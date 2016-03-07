package com.messio.mini.bean;

import javax.mail.internet.MimeBodyPart;

/**
 * Created by jpc on 11/4/14.
 */
public class MailEvent {
    private MimeBodyPart mimeBodyPart;

    public MailEvent() {
    }

    public MailEvent(MimeBodyPart mimeBodyPart) {
        this.mimeBodyPart = mimeBodyPart;
    }

    public MimeBodyPart getMimeBodyPart() {
        return mimeBodyPart;
    }

    public void setMimeBodyPart(MimeBodyPart mimeBodyPart) {
        this.mimeBodyPart = mimeBodyPart;
    }
}
