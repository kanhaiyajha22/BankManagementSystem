package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignupTwo extends JFrame implements ActionListener {

    JTextField aadharTextField, panTextField;
    JButton next;
    JRadioButton syes, sno, eyes, eno;
    JComboBox religionn, categoryy, income1, qualification, occupation;
    String formno;

    SignupTwo(String formno) {
        this.formno = formno;
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");
        setLayout(null);

        JLabel additionalDetails = new JLabel("Page 2: Additional Details");
        additionalDetails.setFont(new Font("Raleway", Font.BOLD, 22));
        additionalDetails.setBounds(290, 80, 400, 30);
        add(additionalDetails);

        JLabel religion = new JLabel("Religion:");
        religion.setFont(new Font("Raleway", Font.BOLD, 20));
        religion.setBounds(100, 140, 100, 30);
        add(religion);

        String valReligion[] = {"Hindu", "Muslim", "Sikh", "Christian", "Others"};
        religionn = new JComboBox(valReligion);
        religionn.setBounds(300, 140, 400, 30);
        religionn.setBackground(Color.WHITE);
        add(religionn);

        JLabel category = new JLabel("Category:");
        category.setFont(new Font("Raleway", Font.BOLD, 20));
        category.setBounds(100, 190, 200, 30);
        add(category);

        String valCategory[] = {"General", "OBC", "SC", "ST", "Others"};
        categoryy = new JComboBox(valCategory);
        categoryy.setBounds(300, 190, 400, 30);
        categoryy.setBackground(Color.WHITE);
        add(categoryy);

        JLabel income = new JLabel("Income:");
        income.setFont(new Font("Raleway", Font.BOLD, 20));
        income.setBounds(100, 245, 200, 30);
        add(income);

        String incomeCategory[] = {"Null", "< 1,50,000", "< 2,50,000", "< 5,00,000", "Upto 10,00,000"};
        income1 = new JComboBox(incomeCategory);
        income1.setBounds(300, 245, 400, 30);
        income1.setBackground(Color.WHITE);
        add(income1);

        JLabel Educational = new JLabel("Educational");
        Educational.setFont(new Font("Raleway", Font.BOLD, 20));
        Educational.setBounds(100, 295, 200, 30);
        add(Educational);

        JLabel Qualification = new JLabel("Qualification:");
        Qualification.setFont(new Font("Raleway", Font.BOLD, 20));
        Qualification.setBounds(100, 320, 200, 30);
        add(Qualification);

        String educationValues[] = {"Matriculation", "Intermediate", "Graduate", "Doctrate", "Others"};
        qualification = new JComboBox(educationValues);
        qualification.setBounds(300, 320, 400, 30);
        qualification.setBackground(Color.WHITE);
        add(qualification);

        JLabel Occupation = new JLabel("Occupation:");
        Occupation.setFont(new Font("Raleway", Font.BOLD, 20));
        Occupation.setBounds(100, 385, 200, 30);
        add(Occupation);

        String occupationValues[] = {"Salaried", "Self-Employed", "Student", "Retired", "Others"};
        occupation = new JComboBox(occupationValues);
        occupation.setBounds(300, 385, 400, 30);
        occupation.setBackground(Color.WHITE);
        add(occupation);

        JLabel pan = new JLabel("PAN Number:");
        pan.setFont(new Font("Raleway", Font.BOLD, 20));
        pan.setBounds(100, 440, 200, 30);
        add(pan);

        panTextField = new JTextField();
        panTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        panTextField.setBounds(300, 440, 400, 30);
        add(panTextField);

        JLabel aadhar = new JLabel("Aadhar Number:");
        aadhar.setFont(new Font("Raleway", Font.BOLD, 20));
        aadhar.setBounds(100, 490, 200, 30);
        add(aadhar);

        aadharTextField = new JTextField();
        aadharTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        aadharTextField.setBounds(300, 490, 400, 30);
        add(aadharTextField);

        JLabel Senior = new JLabel("Senior Citizen:");
        Senior.setFont(new Font("Raleway", Font.BOLD, 20));
        Senior.setBounds(100, 540, 200, 30);
        add(Senior);

        syes = new JRadioButton("Yes");
        syes.setBounds(300, 540, 100, 30);
        syes.setBackground(Color.WHITE);
        add(syes);

        sno = new JRadioButton("No");
        sno.setBounds(400, 540, 100, 30);
        sno.setBackground(Color.WHITE);
        add(sno);

        ButtonGroup seniorGroup = new ButtonGroup();
        seniorGroup.add(syes);
        seniorGroup.add(sno);

        JLabel Existing = new JLabel("Existing Account:");
        Existing.setFont(new Font("Raleway", Font.BOLD, 20));
        Existing.setBounds(100, 590, 200, 30);
        add(Existing);

        eyes = new JRadioButton("Yes");
        eyes.setBounds(300, 590, 100, 30);
        eyes.setBackground(Color.WHITE);
        add(eyes);

        eno = new JRadioButton("No");
        eno.setBounds(400, 590, 100, 30);
        eno.setBackground(Color.WHITE);
        add(eno);

        ButtonGroup exisitingGroup = new ButtonGroup();
        exisitingGroup.add(eyes);
        exisitingGroup.add(eno);

        next = new JButton("Next");
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Raleway", Font.BOLD, 14));
        next.setBounds(620, 660, 80, 30);
        next.addActionListener(this);
        add(next);

        getContentPane().setBackground(Color.WHITE);
        setSize(850, 800);
        setLocation(350, 10);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String sreligion = (String) religionn.getSelectedItem();
        String scatagory = (String) categoryy.getSelectedItem();
        String sincome = (String) income1.getSelectedItem();
        String squalification = (String) qualification.getSelectedItem();
        String soccupation = (String) occupation.getSelectedItem();

        String seniorCitizen = null;
        if (syes.isSelected()) {
            seniorCitizen = "Yes";
        } else if (sno.isSelected()) {
            seniorCitizen = "No";
        }

        String existingAccount = null;
        if (eyes.isSelected()) {
            setVisible(false);
            new Login().setVisible(true);
            return;
//            existingAccount = "Yes";
        } else if (sno.isSelected()) {
            existingAccount = "NO";
        }

        String span = panTextField.getText();
        String saadhar = aadharTextField.getText();

        try {
            if (span.isEmpty() || !span.matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid PAN Number");
            } else if (saadhar.isEmpty() || saadhar.length() != 12 || !saadhar.matches("\\d{12}")) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Aadhar Number");
            } else if (seniorCitizen == null) {
                JOptionPane.showMessageDialog(null, "Please Select Senior Citizen");
            }else {
                Conn c = new Conn();
                String query = "insert into signuptwo values('" + formno + "', '" + sreligion + "', '" + scatagory + "', '" + sincome + "', '" + squalification + "', '" + soccupation + "', '" + seniorCitizen + "', '" + existingAccount + "', '" + span + "', '" + saadhar + "')";
                c.s.executeUpdate(query);

                // SignupThree Object 
                setVisible(false);
                new SignupThree(formno).setVisible(true);
            }
            }catch (Exception e) {
            System.out.println(e);

        }
        }

    

    public static void main(String args[]) {
        new SignupTwo("");
    }
}
