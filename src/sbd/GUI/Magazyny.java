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
    //private Table selectTable;
    //private String[] dataTypes;
    //private String[] pK;

    //private List<String> columnNames;
    //private String tableName;
    //private List<String> types = new ArrayList<String>();
    //private List<String> values = new ArrayList<String>();
    //private List<String> columns = new ArrayList<String>();

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
        fK = new String[][]{{"X"},{"Y"}};
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
            if(selectTable.table.getSelectedRowCount() > 0){
                if(SQLModule.deleteRow(tableName, pK, columnNames.toArray(new String[0]), selectTable.getRow(selectTable.table.getSelectedRow()), dataTypes) > 0){
                    refreshTable();
                }
            }
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

    public void refreshTable(){
        ResultSet rs = SQLModule.selectAll(tableName, columns.toArray(new String[0]), types.toArray(new String[0]),
                values.toArray(new String[0]));
        selectTable = new Table(columnNames, rs);
        selectTable.table.setFillsViewportHeight(true);
        selectTable.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePanel.getViewport().add(selectTable.table);
    }
}
