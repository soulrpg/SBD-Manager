package sbd.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MagazynyPanel extends JPanel implements ActionListener, ListSelectionListener {
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton filterButton;
    private JScrollPane scrollPane;
    private Table selectTable;
    public MagazynyPanel(){
        this.setBounds(Constants.WINDOW_WIDTH/5, 0, Constants.WINDOW_WIDTH/5 * 4, Constants.WINDOW_HEIGHT);
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        updateButton = new JButton("Update");
        insertButton = new JButton("Insert");
        deleteButton = new JButton("Delete");
        filterButton = new JButton("Filter");
        String[] columnNames = { "Name", "Roll Number", "Department" };
        selectTable = new Table(columnNames);
        selectTable.table.setFillsViewportHeight(true);
        selectTable.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //selectTable.setBounds(0, 0, Constants.WIN, 300);
        selectTable.table.getSelectionModel().addListSelectionListener(this);
        scrollPane = new JScrollPane(selectTable.table);
        this.add(updateButton);
        updateButton.addActionListener(this);
        this.add(insertButton);
        this.add(deleteButton);
        this.add(filterButton);
        this.add(scrollPane);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "Update"){
            System.out.println("Update");
        }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println(e.getSource());
    }
}
