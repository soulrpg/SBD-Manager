package sbd.GUI;

import sbd.Main;
import sbd.SQLModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private JButton listyP;
    private JButton kurierzy;
    private JButton magazynierzy;

    protected Table selectTable;
    protected String[] dataTypes;
    protected String[] pK;
    // {"field name in this table, original table name, field name in original table"}
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
        magazynierzy.addActionListener(this);
        kurierzy.addActionListener(this);
        listyP.addActionListener(this);
    }

    public JPanel getPanel(){
        return mainPanel;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "Magazyny"){
            System.out.println("Magazyny!");
            Main.changeScreen(new Magazyny());
        }
        if(e.getActionCommand() == "Klienci"){
            System.out.println("Klienci!");
            Main.changeScreen(new Klienci());
        }
        if(e.getActionCommand() == "Przesyłki"){
            System.out.println("Przesyłki!");
            Main.changeScreen(new Przesylki());
        }
        if(e.getActionCommand() == "Adresy"){
            System.out.println("Adresy!");
            Main.changeScreen(new Adresy());
        }
        if(e.getActionCommand() == "Pracownicy"){
            System.out.println("Pracownicy!!");
            Main.changeScreen(new Pracownicy());
        }
        if(e.getActionCommand() == "Umowy Stałe"){
            System.out.println("Umowy stałe!");
            Main.changeScreen(new UmowyStale());
        }
        if(e.getActionCommand() == "Regiony"){
            System.out.println("Regiony!");
            Main.changeScreen(new Regiony());
        }
        if(e.getActionCommand() == "Samochody"){
            System.out.println("Samochody!");
            Main.changeScreen(new Samochody());
        }
        if(e.getActionCommand() == "Reklamacje"){
            System.out.println("Reklamacje!");
            Main.changeScreen(new Reklamacje());
        }
        if(e.getActionCommand() == "Problemy"){
            System.out.println("Problemy!");
            Main.changeScreen(new Problemy());
        }
        if(e.getActionCommand() == "Listy p"){
            System.out.println("Listy p!");
            Main.changeScreen(new ListyPrzewozowe());
        }
        if(e.getActionCommand() == "Magazynierzy"){
            System.out.println("Magazynierzy!");
            Main.changeScreen(new Magazynierzy());
        }
        if(e.getActionCommand() == "Kurierzy"){
            System.out.println("Kurierzy!");
            Main.changeScreen(new Kurierzy());
        }
        if(e.getActionCommand() == "Wyloguj"){
            System.out.println("Wyloguj!");
            SQLModule.endConnection();
            Main.changeScreen(new LoginScreen());
            //Main.changeScreen(new MainWindow());
        }
    }


    /*public int showInsertWindow(){
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
    }*/

    public int showInsertWindow(){
        // Show message dialog with question and anwsers
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
        JLabel[] label = new JLabel[columnNames.size()];
        JComponent[] inputs = new JComponent[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++){
            label[i] = new JLabel();
            label[i].setText(columnNames.get(i));
            popupPanel.add(label[i]);
            boolean isfK = false;
            for(int j = 0; j < fK.length; j++){
                if(columnNames.get(i).equals(fK[j][0])){
                    inputs[i] = new JComboBox();
                    try{
                        ResultSet rs = SQLModule.selectColumn(fK[j][1], fK[j][2]);
                        while(rs.next()){
                            if(inputs[i] instanceof JComboBox){
                                JComboBox box = (JComboBox) inputs[i];
                                box.addItem(rs.getString(1));
                            }
                        }
                        SQLModule.close();
                    }
                    catch(SQLException e){
                        e.printStackTrace();
                    }

                    isfK = true;
                    break;
                }
            }
            if(!isfK){
                inputs[i] = new JTextField();
                if(dataTypes[i].contains("SEQ")){
                    ((JTextField)inputs[i]).setEnabled(false);
                    ((JTextField)inputs[i]).setText("Wartość generowana automatycznie.");
                }
            }
            popupPanel.add(inputs[i]);
        }
        JOptionPane.showMessageDialog(null, popupPanel, "Insert", JOptionPane.PLAIN_MESSAGE);
        List<String> values = new ArrayList<String>();
        for(JComponent input : inputs){
            if(input instanceof JComboBox){
                values.add((String)((JComboBox)input).getItemAt(((JComboBox)input).getSelectedIndex()));
            }
            else if(input instanceof JTextField){
                values.add(((JTextField)input).getText());
            }
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
        JComponent[] inputs = new JComponent[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++){
            label[i] = new JLabel();
            label[i].setText(columnNames.get(i));
            popupPanel.add(label[i]);
            boolean isfK = false;
            for(int j = 0; j < fK.length; j++){
                if(columnNames.get(i).equals(fK[j][0])){
                    inputs[i] = new JComboBox();
                    try{
                        ResultSet rs = SQLModule.selectColumn(fK[j][1], fK[j][2]);
                        while(rs.next()){
                            if(inputs[i] instanceof JComboBox){
                                JComboBox box = (JComboBox) inputs[i];
                                box.addItem(rs.getString(1));
                                box.setSelectedItem(oldValues[i]);
                            }
                        }
                        SQLModule.close();
                    }
                    catch(SQLException e){
                        e.printStackTrace();
                    }
                    isfK = true;
                    break;
                }
            }
            if(!isfK){
                inputs[i] = new JTextField();
                ((JTextField)inputs[i]).setText(oldValues[i]);
                if(dataTypes[i].contains("SEQ")){
                    ((JTextField)inputs[i]).setEnabled(false);
                }
            }
            popupPanel.add(inputs[i]);
        }
        JOptionPane.showMessageDialog(null, popupPanel, "Update", JOptionPane.PLAIN_MESSAGE);
        List<String> values = new ArrayList<String>();
        for(JComponent input : inputs){
            if(input instanceof JComboBox){
                values.add((String)((JComboBox)input).getItemAt(((JComboBox)input).getSelectedIndex()));
            }
            else if(input instanceof JTextField){
                values.add(((JTextField)input).getText());
            }
        }
        boolean anyChange = false;
        for(int i = 0; i < values.size(); i++){
            if(oldValues[i] == null && values.get(i) == null){
                continue;
            }
            else if(oldValues[i] == null && values.get(i).equals("")){
                continue;
            }
            else if(oldValues[i] == null){
                anyChange = true;
            }
            else if(!values.get(i).equals(oldValues[i])){
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
