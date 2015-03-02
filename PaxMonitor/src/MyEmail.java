import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyEmail{
	public void email(String inputLine, String inputLine2) throws AddressException, MessagingException{
		Properties mailServerProperties;
		Session getMailSession;
		MimeMessage generateMailMessage;

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("lam.ck.alex@gmail.com"));
        generateMailMessage.setSubject("PAX TICKETS");
        String emailBody = "Please check Pax tickets "+"<br />Current status: "+inputLine+"<br /> Details: " + inputLine2
        		+ "<br />http://prime.paxsite.com/registration";
        generateMailMessage.setContent(emailBody, "text/html");

        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", "tobycatapps", "bubblebutt3");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

}
