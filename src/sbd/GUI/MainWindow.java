package sbd.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow implements ActionListener{
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttons;
    private JPanel contents;
    private JButton[] navigationButtons;

    public MainWindow(){
        frame =  new JFrame("SBD Manager");
        mainPanel = new JPanel();
        frame.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        mainPanel.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        mainPanel.setLayout(null);//new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        buttons = new JPanel();
        buttons.setBounds(0, 0, Constants.WINDOW_WIDTH/5, Constants.WINDOW_HEIGHT);
        buttons.setBackground(Color.yellow);
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        //mainPanel.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        //buttons.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH/5, Constants.WINDOW_HEIGHT));
        //buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        contents = new JPanel();
        navigationButtons = new JButton[10];

        navigationButtons[0] = new JButton("Magazyny");
        navigationButtons[1] = new JButton("Klienci");
        navigationButtons[2] = new JButton("Przesyłki");
        navigationButtons[3] = new JButton("Pracownicy");
        navigationButtons[4] = new JButton("Adresy");
        navigationButtons[5] = new JButton("Umowy stałe");
        navigationButtons[6] = new JButton("Regiony");
        navigationButtons[7] = new JButton("Samochody");
        navigationButtons[8] = new JButton("Reklamacje");
        navigationButtons[9] = new JButton("Problemy");

        for(int i = 0; i < 10; i++){
            navigationButtons[i].addActionListener(this);
            navigationButtons[i].setSize(new Dimension(Constants.WINDOW_WIDTH/5, Constants.WINDOW_HEIGHT/10));
            navigationButtons[i].setPreferredSize(new Dimension(Constants.WINDOW_WIDTH/5, Constants.WINDOW_HEIGHT/11));
            navigationButtons[i].setMinimumSize(new Dimension(Constants.WINDOW_WIDTH/5, Constants.WINDOW_HEIGHT/11));

            buttons.add(navigationButtons[i]);
                    //setPreferredSize(new Dimension(Constants.WINDOW_WIDTH/5, Constants.WINDOW_HEIGHT/9));
        }

        buttons.setSize(Constants.WINDOW_WIDTH/5, Constants.WINDOW_HEIGHT);
        contents.setBounds(Constants.WINDOW_WIDTH/5, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        contents.setBackground(Color.blue);
        mainPanel.add(buttons);
        mainPanel.add(contents);
        frame.add(mainPanel);
        frame.setLayout(null);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "Magazyny"){
            contents = new MagazynyPanel();
            mainPanel.add(contents);
            contents.repaint();
            contents.revalidate();
        }
    }
}
