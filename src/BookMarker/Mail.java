package BookMarker;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 
// Send a simple, single part, text/plain e-mail 
public class Mail { 
 
    public Mail (String emailadd) { 
 
        
        final String username = "sherlypaul@gmail.com";
        final String password = "28thdec2011";

 
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
 
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sherlypaul@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailadd));
            message.setSubject("Book Available");
            message.setText("Dear Member,,"
                + "The Item you reserved is available now.");
 
            Transport.send(message);
 
            System.out.println("Done");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
     
}//End of class  

