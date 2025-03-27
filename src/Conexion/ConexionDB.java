package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB



        // * ESTE CODIGO NOS PERMITE HACER UNA CONEXION CON LA BASE DE DATOS MANEJADA DESDE PHPMYADMIN(XAMPP)

{ public Connection getConnection()
{
    Connection con =null;
    try
    {
        con= DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurante", "root","");
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return con;
}
}
