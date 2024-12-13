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
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == r2) {
        setVisible(false);
    } else if (ae.getSource() == r1) {
        try {
            // Initialize connection and statement
            Conn conn = new Conn();
            String formno = null;

            // Query to get the form number from `signupthree`
            String query1 = "SELECT formno FROM signupthree WHERE cardNumber = ?";
            PreparedStatement ps1 = conn.c.prepareStatement(query1);
            ps1.setString(1, cardNumber);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                formno = rs1.getString("formno");
            }
            rs1.close();
            ps1.close();

            // Ensure formno is not null
            if (formno == null) {
                System.out.println("Form number not found.");
                return;
            }

            // Query to get the email from `signup`
            String query2 = "SELECT email FROM signup WHERE formno = ?";
            PreparedStatement ps2 = conn.c.prepareStatement(query2);
            ps2.setString(1, formno);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                receiverMail = rs2.getString("email");
            }
            rs2.close();
            ps2.close();

            // Ensure email is valid
            if (receiverMail == null || receiverMail.isEmpty()) {
                System.out.println("Email address not found.");
                return;
            }

            // Calculate the available balance
            int balance = 0;
            String query3 = "SELECT * FROM bank WHERE cardNumber = ?";
            PreparedStatement ps3 = conn.c.prepareStatement(query3);
            ps3.setString(1, cardNumber);
            ResultSet rs3 = ps3.executeQuery();

            // Check if the ResultSet is empty
            if (!rs3.isBeforeFirst()) {
                System.out.println("No transactions found for this card number.");
                return;
            }

            // Generate the HTML table with inline CSS
            statement.setLength(0); // Clear any existing data
            statement.append("<html>")
                     .append("<head><title>Bank Statement</title></head>")
                     .append("<body>")
                     .append("<h2 style='color: #333; text-align: center;'>Your Bank Statement</h2>")
                     .append("<table style='border-collapse: collapse; width: 100%; text-align: left;'>")
                     .append("<thead>")
                     .append("<tr style='background-color: #f2f2f2;'>");

            // Extract column headers
            int columnCount = rs3.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs3.getMetaData().getColumnName(i);
                statement.append("<th style='border: 1px solid #ddd; padding: 8px; text-align: left;'>")
                         .append(columnName)
                         .append("</th>");
            }
            // Add an extra column for Available Balance
            statement.append("<th style='border: 1px solid #ddd; padding: 8px; text-align: left;'>Available Balance</th>");
            statement.append("</tr>")
                     .append("</thead>")
                     .append("<tbody>");

            // Extract data rows and calculate available balance dynamically
            while (rs3.next()) {
                statement.append("<tr>");
                for (int i = 1; i <= columnCount; i++) {
                    String cellValue = rs3.getString(i);
                    statement.append("<td style='border: 1px solid #ddd; padding: 8px;'>")
                             .append(cellValue != null ? cellValue : "")
                             .append("</td>");
                }

                // Update balance based on transaction type (Deposit or Withdrawal)
                if (rs3.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(rs3.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs3.getString("amount"));
                }

                // Add available balance for each row
                statement.append("<td style='border: 1px solid #ddd; padding: 8px;'>")
                         .append(balance)
                         .append("</td>");
                statement.append("</tr>");
            }

            statement.append("</tbody>")
                     .append("</table>")
                     .append("<p style='text-align: center;'>Thank you for banking with us!</p>")
                     .append("</body>")
                     .append("</html>");
            rs3.close();
            ps3.close();

            // Send the email
            MaillingClass.doMail(receiverMail, statement.toString());
            JOptionPane.showMessageDialog(this, "eStatement sent successfully to " + receiverMail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}