package com.boilerplate.demo.model.common;

public enum SlackMessageColor {
    RED("#ff0000"),
    GREEN("#66cc00"),
    BLUE("#0099ff"),
    PURPLE("#b300b3"),
    ORANGE("#ff6600"),
    YELLOW("#ffcc00");

    private String code;

    private SlackMessageColor(String colorCode) {
        this.code = colorCode;
    }

    public String getCode() {
        return code;
    }
}
