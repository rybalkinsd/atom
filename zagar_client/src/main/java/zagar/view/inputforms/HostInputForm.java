package main.java.zagar.view.inputforms;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * Created by xakep666 on 02.11.16.
 */
public class HostInputForm {
    @NotNull
    private static MaskFormatter portMF;
    private static final int HOST_FORM_CHARS = 20;
    @NotNull
    private JFormattedTextField hostField;
    @NotNull
    private JFormattedTextField portField;
    @NotNull
    private JPanel panel = new JPanel();
    @NotNull
    private String hostdesc;
    @NotNull
    private String host = "";
    private int port;

    static {
        try {
            portMF = new MaskFormatter("*****");
            portMF.setValidCharacters("0123456789");
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public HostInputForm(@NotNull String hostdesc,@NotNull String defaultHost, int defaultPort){
        hostField = new JFormattedTextField();
        portField = new JFormattedTextField(portMF);
        panel.add(new JLabel("Host:"));
        hostField.setText(defaultHost);
        hostField.setColumns(HOST_FORM_CHARS);
        panel.add(hostField);
        panel.add(new JLabel(":"));
        portField.setText(String.valueOf(defaultPort));
        portField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent jComponent) {
                int port = Integer.parseInt(((JTextField) jComponent).getText().trim());
                return port>0 && port<=65535;
            }
        });
        panel.add(portField);
        this.hostdesc=hostdesc;
    }

    public boolean showForm() {
        int result = JOptionPane.showConfirmDialog(null,panel,
                "Please enter "+hostdesc+" host address",JOptionPane.OK_CANCEL_OPTION);
        try {
            host=hostField.getText();
            port=Integer.parseInt(portField.getText().trim());
            return result==JOptionPane.OK_OPTION;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    @NotNull
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
