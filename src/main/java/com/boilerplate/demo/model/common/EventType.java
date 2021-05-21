package com.boilerplate.demo.model.common;

public enum EventType {
    EXCEPTIONS("Exception caught !", SlackMessageType.ERROR, true),
    EXCEPTIONS_REQUEST_DATA("Exception caught (Request Data) !", SlackMessageType.ERROR, true),
    DEMO_EVENT_INFO("New demo event", SlackMessageType.INFO, true);

    private String header;
    private SlackMessageType messageType;
    private boolean slack;

    EventType(String header, SlackMessageType messageType, boolean slack) {
        this.header = header;
        this.messageType = messageType;
        this.slack = slack;
    }

    public String getHeader() {
        return header;
    }

    public SlackMessageType getMessageType() {
        return messageType;
    }

    public boolean isSlack() {
        return slack;
    }

}
