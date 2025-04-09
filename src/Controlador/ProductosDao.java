package Controlador;

import Conexion.ConexionDB;

import Modelo.Productos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductosDao {

    ConexionDB conexionDB = new ConexionDB();

    //AGREGAR
    /*
    AGREGAR INFORMACION EN LA TABLA PRODUTOS DESDE EL GUI
    */
    public void agregar(Productos productos) {
        Connection con = conexionDB.getConnection();

        String query = "INSERT INTO productos(nombre, categoria, precio, disponibilidad) VALUES(?,?,?,?)";

        try {

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, productos.getNombre());
            pst.setString(2, productos.getCategoria());
            pst.setDouble(3, productos.getPrecio());
            pst.setBoolean(4, productos.getDisponibilidad());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO AGREGADO");
            } else {
                JOptionPane.showMessageDialog(null, "MO SE A AAGREGADO NINGUN REGISTRO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR EN LA EJECUCION");
        }
    }


    //actualizar
     /*
    ESTE CODIGO DE AGREGAR NOS SIRVE PARA ACTUALIZAR REGSTRO EN LA DB DESDE EL GUI
     */

    public void atualizar(Productos productos) {
        Connection con = conexionDB.getConnection();

        String query = "UPDATE productos SET nombre=?,categoria=?,precio=?,disponibilidad=?  WHERE id_producto=?";

        try {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, productos.getNombre());
            pst.setString(2, productos.getCategoria());
            pst.setDouble(3, productos.getPrecio());
            pst.setBoolean(4, productos.getDisponibilidad());
            pst.setInt(5, productos.getId_Productos());


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
    ELIMINAR INFORMACION EN LA TABLA PRODUTOS DESDE GUI
    */
    public void eliminar(int id) {
        Connection con = conexionDB.getConnection();

        String query = "DELETE FROM productos WHERE id_producto=?";

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
