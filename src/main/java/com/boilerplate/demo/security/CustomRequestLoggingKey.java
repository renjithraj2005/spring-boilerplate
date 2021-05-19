package com.boilerplate.demo.security;

public enum CustomRequestLoggingKey {
    log_uuid,
    request_data;

    public String keyText() {
        return this.name();
    }
}
