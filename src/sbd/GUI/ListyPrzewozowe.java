package sbd.GUI;

import sbd.SQLModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListyPrzewozowe extends MainWindow implements Screen {
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
    private JButton magazynierzy;
    private JButton kurierzy;
    private JButton listyP;
    //private Table selectTable;
    //private String[] dataTypes;
    //private String[] pK;

    //private List<String> columnNames;
    //private String tableName;
    //private List<String> types = new ArrayList<String>();
    //private List<String> values = new ArrayList<String>();
    //private List<String> columns = new ArrayList<String>();

    // Trzeba dodac dodawanie pola JTable z klasy Table do tablePanel. Takze okodowac reszte actionPerformed()

    public ListyPrzewozowe(){
        //super(); super() does not work there
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
        filtruj.addActionListener(this);
        dodaj.addActionListener(this);
        aktualizuj.addActionListener(this);
        anuluj.addActionListener(this);
        zatwierdz.addActionListener(this);
        usuń.addActionListener(this);
        magazynierzy.addActionListener(this);
        kurierzy.addActionListener(this);
        listyP.addActionListener(this);

        dataTypes = new String[]{"LIST_SEQ", "NUMBER", "NUMBER", "VARCHAR", "VARCHAR", "NUMBER", "NUMBER", "DATE", "DATE", "DATE"};
        tableName = "LISTY_PRZEWOZOWE";
        pK = new String[]{"ID_LISTU"};
        fK = new String[][]{{"ID_ADRESU","ADRESY","ID_ADRESU"},{"ID_PRZESYLKI","PRZESYLKI","ID_PRZESYLKI"},{"NADAWCA","KLIENCI","ID_KLIENTA"}};
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
            refreshTable();
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
