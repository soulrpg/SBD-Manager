package sbd.GUI;

import sbd.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Table selectTable;

    // Trzeba dodac dodawanie pola JTable z klasy Table do tablePanel. Takze okodowac reszte actionPerformed()

    public Magazyny(){
        super();
        filtruj.addActionListener(this);
        dodaj.addActionListener(this);
        aktualizuj.addActionListener(this);
        usuń.addActionListener(this);
        String[] columnNames = { "Name", "Roll Number", "Department" };
        selectTable = new Table(columnNames);
        selectTable.table.setFillsViewportHeight(true);
        selectTable.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePanel.getViewport().add(selectTable.table);
    }

    @Override
    public JPanel getPanel(){
        return mainPanel;
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand() == "Filtruj") {
            System.out.println("Filtruj!");
        }
        if (e.getActionCommand() == "Dodaj") {
            System.out.println("Dodaj!");
        }
        if (e.getActionCommand() == "Aktualizuj") {
            System.out.println("Aktualizuj!");
        }
        if (e.getActionCommand() == "Usuń") {
            System.out.println("Usuń!");
        }
    }


}
