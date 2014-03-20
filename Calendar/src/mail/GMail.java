package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/*
 * eksempel på sending av mail: (paste i en annen klasse)
 * GMail mail = new GMail();
 *		if (mail.sendMail("samoth1601@gmail.com", "Testing fra klient", "HALLA!!! Denne mailen ble sendt fra mainFrame! Totally awsm yo!")){
 *			System.out.println("mail sent!");
		}
 */

public class GMail {
	private String username = "caltwenty@gmail.com" ;
	private String password = "gruppe20";
	private Properties props;

	
	public GMail(){
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}

	
	
	public boolean sendMail(String recipient, String subject, String text){
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(recipient));
					message.setSubject(subject);
					message.setText(text);

					Transport.send(message);

					System.out.println("Done");

					return true;

				} catch (MessagingException e) {
					return false;
				}
	}
}