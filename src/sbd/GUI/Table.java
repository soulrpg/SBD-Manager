package sbd.GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Table{

    public JTable table;

    public Table(String[] columnNames){
        table = new JTable(loadData(), columnNames);
    }

    private String[][] loadData(){
        // select
        List<List<String>> data = new ArrayList<>();
        data.add(new ArrayList<String>());
        data.add(new ArrayList<String>());
        data.get(0).add("Kundan Kumar Jha");
        data.get(0).add("4031");
        data.get(0).add("CSE");
        data.get(1).add("Anand Jha");
        data.get(1).add("6014");
        data.get(1).add("IT");
        String[][] rData;
        rData = new String[data.size()][data.get(0).size()];
        for(int i = 0; i < data.size(); i++){
            rData[i] = data.get(i).toArray(new String[0]);
        }
        return rData;
    }

    private String[][] loadData(ResultSet rs){
        List<List<String>> data = new ArrayList<>();
        String[][] rData;
        //
        rData = new String[data.size()][data.get(0).size()];
        return rData;
    }

}
