package sbd.GUI;

import sbd.SQLModule;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Table{

    public JTable table;

    public Table(List<String> columnNames, ResultSet rs){
        table = new JTable(loadData(rs, columnNames.size()), convert(columnNames));
    }

    private String[] convert(List<String> columnNames){
        String[] rData;
        rData = new String[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++){
            rData[i] = columnNames.get(i);
        }
        return rData;
    }

    public String[] getRow(int index){
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < table.getColumnCount(); i++){
            result.add((String) table.getValueAt(index, i));
        }
        return result.toArray(new String[0]);
    }

    private String[][] loadData(ResultSet rs, int columnNum){
        List<List<String>> data = new ArrayList<>();
        if(rs == null)
            return new String[0][0];
        try {
            while (rs.next()) {
                data.add(new ArrayList<String>());
                for(int i = 1; i <= columnNum; i++){
                    data.get(data.size() - 1).add(rs.getString(i));
                }
            }
            SQLModule.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        String[][] rData;
        if(data.size() == 0){
            return new String[0][0];
        }
        rData = new String[data.size()][data.get(0).size()];
        for(int i = 0; i < data.size(); i++){
            for(int j = 0; j < data.get(i).size(); j++)
            {
                rData[i][j] = data.get(i).get(j);
            }
        }
        return rData;
    }

}
