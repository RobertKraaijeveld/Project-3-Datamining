package DisplayManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jls on 3/21/15.
 */
public class LoginPanel extends JPanel {

    private GridBagConstraints gbc;

    protected JLabel userLabel;
    protected JLabel passLabel;
    protected JTextField userField;
    protected JPasswordField passField;
    protected JButton loginBtn;
    protected JTextField userDisplay;


    protected LoginPanel() {
        super(new GridBagLayout());
        // Init
        initComponent();
        // Create
        createComponent();
        createPanel();
        // Listener
        buttonListener();
    }

    private void initComponent() {
        userLabel = new JLabel("Username:", JLabel.LEFT);
        passLabel = new JLabel("Password:", JLabel.LEFT);
        userField = new JTextField();
        passField = new JPasswordField();
        loginBtn = new JButton("Login");
        userDisplay = new JTextField();
        gbc = new GridBagConstraints();
    }

    private void createComponent() {
        loginBtn.setPreferredSize(new Dimension(100, 25));
        userField.setPreferredSize(new Dimension(200, 25));
        passField.setPreferredSize(new Dimension(200, 25));
        userDisplay.setPreferredSize(new Dimension(200, 25));
        userDisplay.setVisible(false);
        userDisplay.setHorizontalAlignment(JTextField.CENTER);
        userDisplay.setBackground(Color.LIGHT_GRAY);
        userDisplay.setFont(new Font("Verdana", Font.BOLD, 12));
        userDisplay.setDisabledTextColor(Color.white);
    }

    private void createPanel() {
        gbc.insets = new Insets(15, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // Don't grow big
        gbc.weighty = 0; // Don't grow big
        this.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // Don't grow big
        gbc.weighty = 0; // Don't grow big
        this.add(userField, gbc);

        gbc.insets = new Insets(5, 350, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0; // Don't grow big
        gbc.weighty = 0; // Don't grow big
        this.add(userDisplay, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // Don't grow big
        gbc.weighty = 0; // Don't grow big
        this.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // Don't grow big
        gbc.weighty = 0; // Don't grow big
        this.add(passField, gbc);

//        gbc.insets = new Insets(15, 100, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(loginBtn, gbc);

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "Login panel"));
    }

    protected void buttonListener() {
        loginBtn.addActionListener(e -> {
            try {
                Display.getInstance().login();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        });
    }

    protected char[] getPassFieldText() {
        return passField.getPassword();
    }

    protected String getUserFieldText() {
        return userField.getText();
    }

    protected void clearPassField() {
        passField.setText("");
    }

}
