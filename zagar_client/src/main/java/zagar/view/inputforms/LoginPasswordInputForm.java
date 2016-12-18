package main.java.zagar.view.inputforms;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xakep666 on 02.11.16.
 */
public class LoginPasswordInputForm {
    private static final int FORM_CHARS=20;
    @NotNull
    private JTextField loginField = new JTextField(FORM_CHARS);
    @NotNull
    private JTextField passwordField = new JPasswordField(FORM_CHARS);
    @NotNull
    private JPanel panel = new JPanel(new GridLayout(0,1,2,2));
    private String login,password;

    public LoginPasswordInputForm() {
        panel.add(new JLabel("Login:   "));
        panel.add(loginField);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        loginField.setText("test1");
        passwordField.setText("test1");
    }

    public boolean showForm() {
        int result = JOptionPane.showConfirmDialog(null,panel,
                "Please enter login and password",JOptionPane.OK_CANCEL_OPTION);

        login = loginField.getText();
        password = passwordField.getText();
        return result==JOptionPane.OK_OPTION;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
