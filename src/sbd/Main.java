package sbd;
import sbd.GUI.Constants;
import sbd.GUI.LoginScreen;
import sbd.GUI.Screen;

import javax.swing.*;

public class Main {
    static private Screen currentScreen;
    static private JFrame frame;

    public static void changeScreen(Screen newScreen){
        currentScreen = newScreen;
        frame.setContentPane(currentScreen.getPanel());
        frame.pack();
        frame.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    public static void main(String[] args){
        currentScreen = new LoginScreen();
        frame = new JFrame("System baz danych");
        frame.setContentPane(currentScreen.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        frame.setVisible(true);
    }
}