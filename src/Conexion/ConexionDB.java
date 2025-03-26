package Conexion;

import jdk.jshell.ImportSnippet;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConexionDB {

    public Connection getConnction()
    {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mycql://localhost:3306/restaurante","root","");
        }

        catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

}
