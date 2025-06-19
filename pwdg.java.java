import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PasswordGenerator extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox uppercaseCheckBox, numbersCheckBox, specialCharsCheckBox;
    private JSpinner lengthSpinner;
    private JButton generateButton, manualButton;
    private JTextField resultField;

    public PasswordGenerator() {
        super("Password Generator");

        // Authentication Panel
        JPanel loginPanel = new JPanel(new GridLayout(2, 2));
        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField);

        int authResult = JOptionPane.showConfirmDialog(this, loginPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (authResult != JOptionPane.OK_OPTION || !authenticate(usernameField.getText(), new String(passwordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Authentication Failed!");
            System.exit(0);
        }

        // Password Generator UI
        setLayout(new FlowLayout());

        uppercaseCheckBox = new JCheckBox("Uppercase");
        numbersCheckBox = new JCheckBox("Numbers");
        specialCharsCheckBox = new JCheckBox("Special Characters");

        add(uppercaseCheckBox);
        add(numbersCheckBox);
        add(specialCharsCheckBox);

        add(new JLabel("Length:"));
        lengthSpinner = new JSpinner(new SpinnerNumberModel(8, 4, 20, 1));
        add(lengthSpinner);

        generateButton = new JButton("Generate");
        resultField = new JTextField(20);
        resultField.setEditable(false);

        add(generateButton);
        add(resultField);

        generateButton.addActionListener(e -> {
            boolean useUpper = uppercaseCheckBox.isSelected();
            boolean useNum = numbersCheckBox.isSelected();
            boolean useSpecial = specialCharsCheckBox.isSelected();
            int length = (int) lengthSpinner.getValue();

            String password = generatePassword(useUpper, useNum, useSpecial, length);
            resultField.setText(password);
        });

        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    private String generatePassword(boolean upper, boolean num, boolean special, int length) {
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*";

        StringBuilder chars = new StringBuilder(lower);
        if (upper) chars.append(upperCase);
        if (num) chars.append(numbers);
        if (special) chars.append(symbols);

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        new PasswordGenerator();
    }
}
