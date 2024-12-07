package bankmanagementsystem;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class MiniStatement extends JFrame implements ActionListener {

    String receiverMail;
    StringBuilder statement = new StringBuilder();
    JButton r1, r2;
    String cardNumber;
    MiniStatement(String cardNumber) {
        this.cardNumber = cardNumber;
        setTitle("Mini Statement");

        setLayout(null);

        JLabel mini = new JLabel();
        mini.setBounds(20, 140, 400, 200);
        add(mini);

        JLabel bank = new JLabel("Indian Bank");
        bank.setBounds(150, 20, 100, 20);
        add(bank);

        JLabel card = new JLabel();
        card.setBounds(20, 80, 300, 20);
        add(card);

        JLabel balance = new JLabel();
        balance.setBounds(20, 400, 300, 20);
        add(balance);
        
        JLabel email = new JLabel("Do You Want to get eStatement:");
        email.setBounds(20, 450, 300, 20);
        add(email);
        
        r1 = new JButton("Yes");
        r1.setFont(new Font("Raleway", Font.BOLD, 10));
        r1.setBounds(20, 485, 100, 20);
        r1.addActionListener(this);
        add(r1);

        r2 = new JButton("No");
        r2.setFont(new Font("Raleway", Font.BOLD, 10));
        r2.setBounds(130, 485, 100, 20);
        r2.addActionListener(this);
        add(r2);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from login where cardNumber = '" + cardNumber + "'");
            while (rs.next()) {
                card.setText("Card Number: " + rs.getString("cardNumber").substring(0, 4) + "XXXXXXXX" + rs.getString("cardNumber").substring(12));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Conn conn = new Conn();
            int bal = 0;
            ResultSet rs = conn.s.executeQuery("select * from bank where cardNumber = '" + cardNumber + "'");
            
            while (rs.next()) {
                mini.setText(mini.getText() + "<html>" + rs.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount") + "<br><br><html>");

                if (rs.getString("type").equals("Deposit")) {
                    bal += Integer.parseInt(rs.getString("amount"));
                } else {
                    bal -= Integer.parseInt(rs.getString("amount"));
                }
            }
            balance.setText("Available Balance Rs " + bal);
        } catch (Exception e) {
            System.out.println(e);
        }

        setSize(400, 600);
        setLocation(20, 20);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == r2){
            setVisible(false);
        } else if(ae.getSource() == r1){
            
        try{
            
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from signupthree where cardNumber = '" + cardNumber + "'");
            String formno="";
            if(rs.next())
            {
                formno = rs.getString("formno");
            }
            Conn conn1 = new Conn();
            ResultSet rs1 = conn1.s.executeQuery("select * from signup where formno = '" + formno + "'");
            if(rs.next())
            {
                receiverMail = rs.getString("email");
            }
            
            Conn conn2 = new Conn();
             ResultSet rs2 = conn2.s.executeQuery("select * from bank where cardNumber = '" + cardNumber + "'");
            // Generate HTML table
            statement.append("<html>")
                     .append("<head><title>Database Records</title></head>")
                     .append("<body>")
                     .append("<table border='1'>");

            // Extract column names
            int columnCount = rs2.getMetaData().getColumnCount();
            statement.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs2.getMetaData().getColumnName(i);
                statement.append("<th>").append(columnName).append("</th>");
            }
            statement.append("</tr>");

            // Extract rows
            while (rs2.next()) {
                statement.append("<tr>");
                for (int i = 1; i <= columnCount; i++) {
                    String cellValue = rs2.getString(i);
                    statement.append("<td>").append(cellValue != null ? cellValue : "").append("</td>");
                }
                statement.append("</tr>");
            }

            statement.append("</table>")
                     .append("</body>")
                     .append("</html>");

            // Display the generated HTML (in console for this example)
            System.out.println(statement.toString());
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
            MaillingClass.doMail(receiverMail, statement.toString());

        }
    }

    public static void main(String args[]) {
        new MiniStatement("");
    }
}
