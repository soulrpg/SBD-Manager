package sbd.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.*;

public class MagazynyPanel extends JPanel implements ActionListener{
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton filterButton;
    private JScrollPane scrollPane;
    private JTable selectTable;
    public MagazynyPanel(){
        this.setBounds(Constants.WINDOW_WIDTH/5, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        updateButton = new JButton("Update");
        insertButton = new JButton("Insert");
        deleteButton = new JButton("Delete");
        filterButton = new JButton("Filter");
        selectTable = new JTable();
        selectTable.setFillsViewportHeight(true);
        scrollPane = new JScrollPane();
        scrollPane.add(selectTable);
        this.add(updateButton);
        this.add(insertButton);
        this.add(deleteButton);
        this.add(filterButton);
        this.add(scrollPane);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "Update"){

        }
    }
}
