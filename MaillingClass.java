package bankmanagementsystem;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MaillingClass {
    
    public static void doMail(String receiverMail, String statement) {
        String from = "mrvirtual69@gmail.com"; // Replace with your email

        // Set up mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // Get the Session object
        Session session = Session.getInstance(props, new MyAuth());

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From, To, and Subject fields
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverMail));
            message.setSubject("Bank Statement");

            // Add the HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(statement, "text/html; charset=utf-8"); // Set content type as HTML

            // Add the HTML part to the message
            Multipart body = new MimeMultipart();
            body.addBodyPart(htmlPart);

            // Set the complete message parts
            message.setContent(body);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully to: " + receiverMail);

        } catch (MessagingException e) {
            // Log the error (replace with a logging framework in production)
            e.printStackTrace();
        }
    }
}
