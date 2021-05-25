package com.demo.emailapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import com.demo.emailapp.model.MessagePojo;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


	  public List<MessagePojo> listMail(String hostName, String protocol, String port, String uName,
										String uPassword) {

		List<MessagePojo> emails = new ArrayList<MessagePojo>();

		try {

			Store store = getStore(hostName, protocol, port, uName,uPassword);
			if(store !=null) {
				Folder inbox = store.getFolder("INBOX");
				inbox.open(Folder.READ_ONLY);
				Message[] messages = inbox.getMessages();
				for(Message msg:messages)
				{
					MessagePojo mail=getMail(msg);
					if ((mail !=null)&&(mail.getFromAddress() != null)) {
						mail.setFromAddress(makePretty(mail.getFromAddress()));
					}
					emails.add(mail);
				}
				inbox.close(false);
				store.close();
				return emails;
			}
		} catch (NoSuchProviderException nspe) {
			System.err.println("invalid provider name");
		} catch (MessagingException me) {
			System.err.println("messaging exception");
			me.printStackTrace();
		}
		return emails;
	}

	public MessagePojo fetchSingleMail(String hostName, String protocol, String port, String uName,
									   String uPassword, int number) {

		MessagePojo mailPojo = new MessagePojo();
		try {
			Store store = getStore(hostName, protocol, port, uName,uPassword);
			if (null != store) {
				Folder inbox = store.getFolder("INBOX");
				inbox.open(Folder.READ_ONLY);

				Message[] messages = inbox.getMessages();

				for (int i = 0; i < messages.length; i++) {
					MessagePojo mail = getMail(messages[i]);
					if (null != mail && mail.getFromAddress() != null && mail.getMessageNumber() == number) {
						return mail;
					}
				}
				inbox.close(false);
				store.close();
			}

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return mailPojo;
	}

	private MessagePojo getMail(Message javaMailMessage) throws MessagingException {
		MessagePojo mail = new MessagePojo();
		try {
			javaMailMessage.getSubject();
			Address a[] = javaMailMessage.getFrom();
			if (a == null)
				return null;
			for (int i = 0; i < a.length; i++) {
				Address address = a[i];
				mail.setFromAddress(address.toString());
			}
			mail.setSubject(javaMailMessage.getSubject());
			mail.setMessageInTime(javaMailMessage.getSentDate());
			mail.setMessageNumber(javaMailMessage.getMessageNumber());

			mail.setMessageContent(messageToString(javaMailMessage));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mail;
	}

	private String makePretty(String stringToModify) {


		StringBuilder oldStringBuffer = new StringBuilder(stringToModify);
		StringBuilder newStringBuffer = new StringBuilder();

		for (int i = 0, length = oldStringBuffer.length(); i < length; i++) {
			char c = oldStringBuffer.charAt(i);
			if (c == '"' || c == '\'') {
				// do nothing
			} else {
				newStringBuffer.append(c);
			}

		}
		return new String(newStringBuffer);
	}

	private String messageToString(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = mimeMessageToString(mimeMultipart);
		}
		return result;
	}

	private String mimeMessageToString(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + html;
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + mimeMessageToString((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	private Store getStore(String hostName, String protocol, String port, String uName,
						   String uPassword) {
		Properties properties = new Properties();
		Store store = null;
		try {
			String username = uName;
			String password = uPassword;
			String provider = "imap";
			properties.put(String.format("mail.%s.hostName", protocol), hostName);
			properties.put(String.format("mail.%s.port", protocol), port);
			properties.put(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
			properties.put(String.format("mail.%s.socketFactory.fallback", protocol), "false");
			properties.put(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));
			Session session = Session.getDefaultInstance(properties, null);
			store = session.getStore(provider);
			store.connect(hostName, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return store;
	}

}
