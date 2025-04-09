package Controlador;

import Modelo.Orden;
import Conexion.ConexionDB;
import Modelo.Productos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdenDao {

ConexionDB conexionDB = new ConexionDB();
/* AGREGAR ORDEN

    AGREGAR INFORMACION EN LA TABLA ORDEN DESDE EL GUI
    */

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

    //actualizar
     /*
    ESTE CODIGO DE AGREGAR NOS SIRVE PARA ACTUALIZAR REGSTRO EN LA DB DESDE EL GUI
     */

    public void atualizar(Orden orden) {
        Connection con = conexionDB.getConnection();

        String query = "UPDATE orden SET estado=?,id_cliente=?,id_empleado=?,id_mesa=? WHERE id_orden=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);


            pst.setString(1, orden.getEstado());
            pst.setInt(2, orden.getId_cliente());
            pst.setInt(3, orden.getId_empleado());
            pst.setInt(4, orden.getId_mesa());

            pst.setInt(5, orden.getId_orden());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ACTUALIZADO CON EXITO ");
            } else {
                JOptionPane.showMessageDialog(null, "NO SE HA ACTUALIZADO NINGUN REGISTRO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR  EN LA EJECUCION");
        }
    }

    /*Eliminar
       ELIMINAR INFORMACION EN LA TABLA ORDEN DESDE GUI
       */
    public void eliminar(int id) {
        Connection con = conexionDB.getConnection();

        String query = "DELETE FROM orden WHERE id_orden=?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);


            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO CON EXITO ");
            } else {
                JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO NINGUN REGISTRO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR  EN LA EJECUCION");
        }

    }

}
