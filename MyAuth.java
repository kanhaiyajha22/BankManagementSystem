package bankmanagementsystem;

import javax.mail.*;

public class MyAuth extends javax.mail.Authenticator {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("mrvirtual69@gmail.com", "akow erfa syxz auoz");
    }
}
