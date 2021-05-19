package com.boilerplate.demo.model.common;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class XsdValidationResult {
        private boolean valid = false; // true means valid, else invalid
        private List<String> messages = new LinkedList<>();

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public List<String> getMessages() {
            return messages;
        }

        public void setMessages(List<String> messages) {
            this.messages = messages;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (valid) {
                sb.append("VALID");
            } else {
                sb.append("INVALID");
            }
            if (CollectionUtils.isNotEmpty(messages)) {
                sb.append("\n\t").append(StringUtils.join(messages, "\n\t"));
            }
            return sb.toString();
        }
    }