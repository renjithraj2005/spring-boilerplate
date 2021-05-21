package com.boilerplate.demo.helper;

import com.boilerplate.demo.helper.utils.SlackClient;
import com.boilerplate.demo.model.common.EventPayload;
import com.boilerplate.demo.model.common.EventType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private SlackClient slackClient;

    @Autowired(required = false)
    private HttpServletRequest request;



    // this method is safe, no exception thrown, errors will be logged if any
    public void notify(EventType eventType, EventPayload inputPayload) {
        try {
            EventPayload eventPayload = new EventPayload(inputPayload);
            enrichPayload(eventPayload);
            if (eventType.isSlack()) {
                slackClient.send(eventType, eventPayload);
            }
            // other channels goes here
            // ...
        } catch (Exception e) {
            logger.error("Couldn't send notification: " + eventType.name());
        }
    }

    private void enrichPayload(EventPayload eventPayload) {
        if (request == null) {
            // data not available for slack message
            return;
        }
        String server = StringUtils.EMPTY;
        String user = StringUtils.EMPTY;
        try {
            server = request.getServerName();
        } catch (Exception exp){
            // ignored
        }
        try {
            user = request.getUserPrincipal().getName();
        } catch (Exception exp){
            // ignored
        }


        if (ObjectUtils.isEmpty(eventPayload.get("server"))) {
            eventPayload.put("server", server);
        }
        if (ObjectUtils.isEmpty(eventPayload.get("user"))) {
            eventPayload.put("user", user);
        }
    }

}
