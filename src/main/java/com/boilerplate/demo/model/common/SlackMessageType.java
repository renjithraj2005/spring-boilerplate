package com.boilerplate.demo.model.common;

public enum SlackMessageType {
    TRACE(SlackMessageColor.PURPLE),
    INFO(SlackMessageColor.BLUE),
    SUCCESS(SlackMessageColor.GREEN),
    WARNING(SlackMessageColor.YELLOW),
    ERROR(SlackMessageColor.RED),
    FATAL(SlackMessageColor.ORANGE);

    private SlackMessageColor color;

    SlackMessageType(SlackMessageColor color) {
        this.color = color;
    }

    public SlackMessageColor getSlackMessageColor() {
        return color;
    }
}
