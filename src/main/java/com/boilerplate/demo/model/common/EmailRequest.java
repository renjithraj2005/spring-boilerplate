package com.boilerplate.demo.model.common;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class EmailRequest {
	
	private String sender;
	private List<String> toMailIds = new ArrayList<>(0);
	private List<String> ccMailIds = new ArrayList<>(0);
	private List<String> bccMailIds = new ArrayList<>(0);
	private MultiValueMap<String, String> additionalParameters = new LinkedMultiValueMap<>(0);
	private String subject;
	private String mailBody;
	private List<EmailAttachment> attachments = new ArrayList<>();

	private EmailRequest() {

	}
	public static EmailRequest requestBuilder() {
		return new EmailRequest();
	}
	public String getSender() {
		return sender;
	}
	public EmailRequest setSender(String sender) {
		this.sender = sender;
		return this;
	}
	public List<String> getToMailIds() {
		return toMailIds;
	}
	public EmailRequest setToMailIds(List<String> toMailIds) {
		this.toMailIds = toMailIds;
		return this;
	}
	public List<String> getCcMailIds() {
		return ccMailIds;
	}
	public EmailRequest setCcMailIds(List<String> ccMailIds) {
		this.ccMailIds = ccMailIds;
		return this;
	}
	public List<String> getBccMailIds() {
		return bccMailIds;
	}
	public EmailRequest setBccMailIds(List<String> bccMailIds) {
		this.bccMailIds = bccMailIds;
		return this;
	}
	public String getMailBody() {
		return mailBody;
	}
	public EmailRequest setMailBody(String mailBody) {
		this.mailBody = mailBody;
		return this;
	}
	public String getSubject() {
		return subject;
	}
	public EmailRequest setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public EmailRequest setAdditionalParameters(MultiValueMap<String, String> additionalParameters){
		if(additionalParameters instanceof MultiValueMap){
			this.additionalParameters = additionalParameters;
		}
		return this;
	}

	public MultiValueMap getAdditionalParameters(){
		return this.additionalParameters;
	}

	public List<EmailAttachment> getAttachments() {
		return attachments;
	}

	public EmailRequest setAttachments(List<EmailAttachment> attachments) {
		this.attachments = attachments;
		return this;
	}
}


