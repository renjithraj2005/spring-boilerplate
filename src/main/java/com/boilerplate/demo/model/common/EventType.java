package com.boilerplate.demo.model.common;

public enum EventType {
    EXCEPTIONS("Exception caught !", SlackMessageType.ERROR, true),
    EXCEPTIONS_REQUEST_DATA("Exception caught (Request Data) !", SlackMessageType.ERROR, true),
    DAT_MARKED_FOR_SUBMISSION("DAT Marked for submission", SlackMessageType.INFO, true),
    DAT_MARKED_FOR_SUBMISSION_ERROR("DAT Marked for submission ERROR", SlackMessageType.INFO, true),
    DAT_UNMARKED_FOR_SUBMISSION("DAT UnMarked for submission", SlackMessageType.INFO, true),
    DAT_XML_VALIDATION_TRIGGERED("DAT XML validation triggered", SlackMessageType.INFO, true),
    USER_NEW_REGISTRATION("User: New registration", SlackMessageType.INFO, true);

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
