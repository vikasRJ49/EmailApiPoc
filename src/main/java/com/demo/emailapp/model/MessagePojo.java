package com.demo.emailapp.model;

import java.util.Date;

public class MessagePojo {

    private String fromAddress;
    private String messageContent;
    private String subject;
    private Date messageInTime;
    private int mNumber;


    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public int getMessageNumber() {
        return mNumber;
    }


    public void setMessageNumber(int messagenumber) {
        this.mNumber = messagenumber;
    }


    public Date getMessageInTime() {
        return messageInTime;
    }


    public void setMessageInTime(Date messageInTime) {
        this.messageInTime = messageInTime;
    }


}
