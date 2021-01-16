package sbd.GUI;

import sbd.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements Screen, ActionListener {
    private JButton magazyny;
    private JButton klienci;
    private JButton przesyłki;
    private JButton pracownicy;
    private JButton adresy;
    private JButton umowyStale;
    private JButton regiony;
    private JButton samochody;
    private JButton reklamacje;
    private JButton problemy;
    private JButton wyloguj;
    private JPanel mainPanel;

    public MainWindow(){
        magazyny.addActionListener(this);
        klienci.addActionListener(this);
        przesyłki.addActionListener(this);
        pracownicy.addActionListener(this);
        adresy.addActionListener(this);
        umowyStale.addActionListener(this);
        regiony.addActionListener(this);
        samochody.addActionListener(this);
        reklamacje.addActionListener(this);
        problemy.addActionListener(this);
        wyloguj.addActionListener(this);
    }

    public JPanel getPanel(){
        return mainPanel;
    }

    public void actionPerformed(ActionEvent e){
        System.out.println("test");
        if(e.getActionCommand() == "Magazyny"){
            System.out.println("Magazyny!");
            Main.changeScreen(new Magazyny());
        }
        if(e.getActionCommand() == "Klienci"){
            System.out.println("Klienci!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Przesyłki"){
            System.out.println("Przesyłki!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Adresy"){
            System.out.println("Adresy!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Pracownicy"){
            System.out.println("Pracownicy!!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Umowy Stałe"){
            System.out.println("Umowy stałe!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Regiony"){
            System.out.println("Regiony!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Samochody"){
            System.out.println("Samochody!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Reklamacje"){
            System.out.println("Reklamacje!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Problemy"){
            System.out.println("Problemy!");
            //Main.changeScreen(new MainWindow());
        }
        if(e.getActionCommand() == "Wyloguj"){
            System.out.println("Wyloguj!");
            //Main.changeScreen(new MainWindow());
        }
    }
}
