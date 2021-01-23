package sbd.GUI;

import sbd.Main;
import sbd.SQLModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    protected Table selectTable;
    protected String[] dataTypes;
    protected String[] pK;
    protected String[][] fK;

    protected List<String> columnNames;
    protected String tableName;
    protected List<String> types = new ArrayList<String>();
    protected List<String> values = new ArrayList<String>();
    protected List<String> columns = new ArrayList<String>();

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


    public int showInsertWindow(){
        // Show message dialog with question and anwsers
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
        JLabel[] label = new JLabel[columnNames.size()];
        JTextField[] inputs = new JTextField[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++){
            label[i] = new JLabel();
            label[i].setText(columnNames.get(i));
            popupPanel.add(label[i]);
            inputs[i] = new JTextField();
            popupPanel.add(inputs[i]);
        }
        JOptionPane.showMessageDialog(null, popupPanel, "Insert", JOptionPane.PLAIN_MESSAGE);
        List<String> values = new ArrayList<String>();
        for(JTextField input : inputs){
            values.add(input.getText());
        }
        if(SQLModule.insertRow(tableName, values.toArray(new String[0]), dataTypes) > 0){
            refreshTable();
            return 0;
        }
        return -1;
    }

    public void showFilterWindow(){
        // Show message dialog with question and anwsers
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(0,3));
        JLabel[] label = new JLabel[columnNames.size()];
        JLabel[] columnLabel = new JLabel[3];
        columnLabel[0] = new JLabel();
        columnLabel[0].setText("Nazwa");
        popupPanel.add(columnLabel[0]);
        columnLabel[1] = new JLabel();
        columnLabel[1].setText("Rodzaj filtrowania");
        popupPanel.add(columnLabel[1]);
        columnLabel[2] = new JLabel();
        columnLabel[2].setText("Wartość");
        popupPanel.add(columnLabel[2]);
        JTextField[] inputsType = new JTextField[columnNames.size()];
        JTextField[] inputsValue = new JTextField[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++){
            label[i] = new JLabel();
            label[i].setText(columnNames.get(i));
            popupPanel.add(label[i]);
            inputsType[i] = new JTextField();
            popupPanel.add(inputsType[i]);
            inputsValue[i] = new JTextField();
            popupPanel.add(inputsValue[i]);
        }
        JOptionPane.showMessageDialog(null, popupPanel, "Filter", JOptionPane.PLAIN_MESSAGE);
        types = new ArrayList<String>();
        values = new ArrayList<String>();
        columns = new ArrayList<String>();
        for(int i = 0; i < columnNames.size(); i++){
            if(inputsType[i].getText().length() > 0){
                types.add(inputsType[i].getText());
                columns.add(columnNames.get(i));
                values.add(inputsValue[i].getText());
            }
        }
        refreshTable();
    }

    public void showUpdateWindow(String[] oldValues){
        // Show message dialog with question and anwsers
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
        JLabel[] label = new JLabel[columnNames.size()];
        JTextField[] inputs = new JTextField[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++){
            label[i] = new JLabel();
            label[i].setText(columnNames.get(i));
            popupPanel.add(label[i]);
            inputs[i] = new JTextField();
            inputs[i].setText(oldValues[i]);
            popupPanel.add(inputs[i]);
        }
        JOptionPane.showMessageDialog(null, popupPanel, "Update", JOptionPane.PLAIN_MESSAGE);
        List<String> values = new ArrayList<String>();
        for(JTextField input : inputs){
            values.add(input.getText());
        }
        boolean anyChange = false;
        for(int i = 0; i < values.size(); i++){
            if(!values.get(i).equals(oldValues[i])){
                anyChange = true;
            }
        }
        if(anyChange){
            if(SQLModule.updateRow(tableName, pK, columnNames.toArray(new String[0]), values.toArray(new String[0]), oldValues, dataTypes) > 0){
                refreshTable();
            }
        }
        else{
            System.out.println("Brak zmienionych pól.");
        }

    }

    public void refreshTable(){

    }
}
