package com.example.lessons;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLserver {
    Connection con;
    String username, pass, ip, port, database;
    public Connection connectionClass(){
        ip = "172.1.1.0";
        database = "ProManager";
        username = "sa";
        pass = "knife513755";
        port = "1433";
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+username+";password="+pass+";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } return connection;
    }

    public ArrayList<String> executeQuery(String query){
        Connection connect;
        ArrayList<String> result = new ArrayList<String>();
        try{
            SQLserver sql = this;
            connect = this.connectionClass();
            if (connect==null) return result;
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(query);
            int columnIter = 1;
            while (rs.next()) {
                result.add(rs.getString(columnIter));
                columnIter+=1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } return result;
    }
}
