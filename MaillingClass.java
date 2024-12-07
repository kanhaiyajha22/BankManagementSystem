package bankmanagementsystem;

import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MaillingClass {
    
    public static void doMail(String receiverMail, String statement)
    {
        String to = receiverMail;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        try {
            // Use Session.getInstance instead of Session.getDefaultInstance
            Session session = Session.getInstance(props, new MyAuth()); // Using getInstance instead of getDefaultInstance()
            
            // Compose message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mrvirtual69@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Bank Statement");

            // Initialize the Multipart body to add text and attachments
            Multipart body = new MimeMultipart(); // Initialize the body variable

            // Add text part
            MimeBodyPart part1 = new MimeBodyPart();
            part1.setText(statement);
            body.addBodyPart(part1);

            // Set the content of the message
            message.setContent(body);

            // Send the message
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace(); // Print the error for debugging
        }

    }
}
