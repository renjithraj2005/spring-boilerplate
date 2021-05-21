package com.boilerplate.demo.helper.utils;

import com.boilerplate.demo.model.common.EventPayload;
import com.boilerplate.demo.model.common.EventType;
import com.boilerplate.demo.model.common.SlackMessageType;
import com.boilerplate.demo.model.common.SlackMessengerException;
import com.boilerplate.demo.security.Constants;
import com.boilerplate.demo.security.CustomRequestLoggingKey;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.boilerplate.demo.model.common.EventType.*;

@Component
public class SlackClient {
    private final Logger logger = LoggerFactory.getLogger(SlackClient.class);

    private static final Slack client = Slack.getInstance();

    @Value("${slack.enabled}")
    private Boolean slackEnabled;
    @Value("${slack.demo.hook.url}")
    private String slackDemoHookUrl;


    public void send(EventType eventType, EventPayload payloadInput) throws SlackMessengerException {
        if(!checkSlackEnabled()) {
            logger.debug("slack.enabled is OFF");
            return;
        }

        if (eventType == null) {
            throw new SlackMessengerException("Missing eventType");
        }

        // clone the payload, so modification in this method will not affect the payload input.
        EventPayload payload = new EventPayload(payloadInput);

        if (MapUtils.isEmpty(payload)) {
            throw new SlackMessengerException("Empty event payload");
        }

        EventPayload bigPayload = extractBigFields(payload);

        sendToSlack(eventType, payload, 4000);
        if (bigPayload != null) {
            sendToSlack(EXCEPTIONS_REQUEST_DATA, bigPayload, Constants.MAX_REQUEST_DATA_LENGTH_TO_LOG);
        }

    }

    private void sendToSlack(EventType eventType, EventPayload payload, int maxFieldLength) {
        StringBuilder message = new StringBuilder();
        for (String key : payload.keySet()) {
            message.append(key).append(" :").append("\n");
            String v = String.valueOf(payload.get(key));

            // 4000 is a result of manually test vs slack, the message will be cut by Slack if it's too long
            // and we can lose important info
            message.append("```\n" + StringUtils.left(v, maxFieldLength) + "\n```").append("\n");
        }

        try {
            String webhookUrl = getSlackWebhookUrl(eventType);
            if (logger.isDebugEnabled()) {
                logger.debug("Sending " + eventType.name() + " to " + webhookUrl);
            }

            WebhookResponse response = client.send(webhookUrl, createPayload(eventType.getHeader(), message.toString(),
                    eventType.getMessageType()));
            if(logger.isDebugEnabled()) {
                logger.debug(ToStringBuilder.reflectionToString(response, ToStringStyle.SIMPLE_STYLE));
            }
        } catch (Exception e) {
            throw new SlackMessengerException(e);
        }
    }

    private EventPayload extractBigFields(EventPayload payload) {
        Object requestBodyObj = payload.remove(CustomRequestLoggingKey.request_data.keyText());
        if (requestBodyObj != null) {
            Object uuid = payload.get(CustomRequestLoggingKey.log_uuid.keyText());
            if (uuid == null) {
                uuid = UUID.randomUUID().toString();
            }

            // this is to make sure uuid is at top
            EventPayload tempPayload = EventPayload.map(CustomRequestLoggingKey.log_uuid.keyText(), uuid);
            tempPayload.putAll(payload);
            payload.clear();
            payload.putAll(tempPayload);

            EventPayload requestBodyEventPayload = EventPayload
                    .map(CustomRequestLoggingKey.log_uuid.keyText(), uuid)
                    .with(CustomRequestLoggingKey.request_data.keyText(), requestBodyObj);
            return requestBodyEventPayload;
        }
        return null;
    }

    private Payload createPayload(String title, String message, SlackMessageType type) {
        return Payload.builder().attachments(createAttachment(title, message, type)).build();
    }

    private List<Attachment> createAttachment(String title, String message, SlackMessageType type) {
        Attachment textContent = Attachment.builder().title(title)
                .text(message)/*.mrkdwnIn(Arrays.asList("plain_text"))*/.color(type.getSlackMessageColor().getCode()).build();

        List<Attachment> attachements = new ArrayList<>();
        attachements.add(textContent);
        return attachements;
    }

    private String getSlackWebhookUrl(EventType eventType) {
        switch (eventType) {
            case EXCEPTIONS:
            case EXCEPTIONS_REQUEST_DATA:
            case DEMO_EVENT_INFO:
                return slackDemoHookUrl;
            default:
                // temporarily use this as default
                return slackDemoHookUrl;
        }
    }

    private boolean checkSlackEnabled() {
        return BooleanUtils.isTrue(slackEnabled);
    }
}