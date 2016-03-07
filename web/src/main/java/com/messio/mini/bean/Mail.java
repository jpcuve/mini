package com.messio.mini.bean;

import com.messio.mini.resource.TheBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by jpc on 11/4/14.
 */
public class Mail {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mail.class);
    @Resource
    private TheBean theBean;
    @Resource
    private Session mailSession;


    public void mail(@Observes MailEvent mailEvent) throws MessagingException {
        LOGGER.debug("sending mail: {}", mailEvent);
        final MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("messiosprl@gmail.com"));
        mimeMessage.setSubject("Reminder");
        final MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(mailEvent.getMimeBodyPart());
        mimeMessage.setContent(mimeMultipart);
        Transport transport = mailSession.getTransport();
        transport.connect();
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }
}
