package com.benison.college.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbIsntance;
    private static Connection con ;
    String DRIVER="com.mysql.jdbc.Driver";
    String URL="jdbc:mysql://localhost:3306/collegeerp";
    String UNAME="root";
    String PASS="root";

    private DBConnection() {
        // private constructor //
    }
    public static DBConnection getInstance(){
        if(dbIsntance==null){
            dbIsntance= new DBConnection();
        }
        return dbIsntance;
    }
    public  Connection getConnection() throws ClassNotFoundException, SQLException
    {
        if (con == null|| con.isClosed()) {
            Class.forName(DRIVER);
            con = (Connection) DriverManager.getConnection(URL, UNAME, PASS);
        }
        return  con;
    }

}


