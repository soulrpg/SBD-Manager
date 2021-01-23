package sbd.GUI;

import sbd.Main;
import sbd.SQLModule;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Magazyny extends MainWindow implements Screen {
    private JButton filtruj;
    private JButton dodaj;
    private JButton aktualizuj;
    private JButton usuń;
    private JScrollPane tablePanel;
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
    private JButton zatwierdz;
    private JButton anuluj;
    private Table selectTable;
    private String[] dataTypes;
    private String[] pK;

    private List<String> columnNames;
    private String tableName;
    private List<String> types = new ArrayList<String>();
    private List<String> values = new ArrayList<String>();
    private List<String> columns = new ArrayList<String>();

    // Trzeba dodac dodawanie pola JTable z klasy Table do tablePanel. Takze okodowac reszte actionPerformed()

    public Magazyny(){
        super();
        filtruj.addActionListener(this);
        dodaj.addActionListener(this);
        aktualizuj.addActionListener(this);
        anuluj.addActionListener(this);
        zatwierdz.addActionListener(this);
        usuń.addActionListener(this);
        dataTypes = new String[]{"NUMBER", "VARCHAR"};
        tableName = "MAGAZYNY";
        pK = new String[]{"NAZWA"};
        createTable();
    }

    public void createTable(){
        columnNames = new ArrayList<String>();
        try{
            ResultSet rs = SQLModule.getColumnNames("\'" + tableName + "\'");
            while(rs.next()){
                columnNames.add(rs.getString(1));
            }
            SQLModule.close();
            refreshTable();
            SQLModule.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public JPanel getPanel(){
        return mainPanel;
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand() == "Filtruj") {
            System.out.println("Filtruj!");
            showFilterWindow();
        }
        if (e.getActionCommand() == "Dodaj") {
            System.out.println("Dodaj!");
            showInsertWindow();
        }
        if (e.getActionCommand() == "Aktualizuj") {
            System.out.println("Aktualizuj!");
            if(selectTable.table.getSelectedRowCount() > 0){
                showUpdateWindow(selectTable.getRow(selectTable.table.getSelectedRow()));
            }
        }
        if (e.getActionCommand() == "Usuń") {
            System.out.println("Usuń!");
        }
        if (e.getActionCommand() == "Anuluj") {
            System.out.println("Anuluj!");
            SQLModule.rollback();
        }
        if (e.getActionCommand() == "Zatwierdź") {
            System.out.println("Zatwierdź!");
            SQLModule.commit();
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
        ResultSet rs = SQLModule.selectAll(tableName, columns.toArray(new String[0]), types.toArray(new String[0]),
                values.toArray(new String[0]));
        selectTable = new Table(columnNames, rs);
        selectTable.table.setFillsViewportHeight(true);
        selectTable.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePanel.getViewport().add(selectTable.table);
    }

}
