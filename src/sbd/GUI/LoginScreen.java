package sbd.GUI;

import sbd.Main;
import sbd.SQLModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen implements Screen, ActionListener {
    private JPanel mainPanel;
    private JButton login;
    private JTextField username;
    private JPasswordField password;

    public LoginScreen(){
        login.addActionListener(this);
    }

    public JPanel getPanel(){
        return mainPanel;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "Loguj"){
            if(SQLModule.startConnection(username.getText(), password.getText())){
                Main.changeScreen(new MainWindow());
            }
            else{
                JOptionPane.showMessageDialog(Main.frame,
                        "Połączenie z bazą danych nie udało się.",
                        "Inane warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
