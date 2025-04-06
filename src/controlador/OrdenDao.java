package Controlador;

import Modelo.Orden;
import Conexion.ConexionDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdenDao {

ConexionDB conexionDB = new ConexionDB();
// AGREGAR ORDEN

    public void agregar (Orden orden){

        Connection con = conexionDB.getConnection();

        String query = "INSERT INTO orden(estado, id_cliente, id_empleado, id_mesa) VALUES (?,?,?,?)";

        try{
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, orden.getEstado());
            pst.setInt(2, orden.getId_cliente());
            pst.setInt(3, orden.getId_empleado());
            pst.setInt(4, orden.getId_mesa());


            int resultado = pst.executeUpdate();
            if (resultado > 0){
                JOptionPane.showMessageDialog(null, "REGISTRO AGREGADO");
            } else {
                JOptionPane.showMessageDialog(null, "NO SE AGREGO NINGUN REGISTRO");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR EN LA EJECUCION");
        }
    }

}
