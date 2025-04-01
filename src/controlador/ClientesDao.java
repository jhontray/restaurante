package controlador;

import Conexion.ConexionDB;

import modelo.Clientes;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientesDao
{
    ConexionDB conexionDB=new ConexionDB();
    //agregar
    /*
    ESTE CODIGO DE AGREGAR NOS SIRVE PARA AGREGAR REGSTRO DESDE LA GUI A LA BASE DE DATOS
     */

    public void agregar(Clientes clientes)
    {
        Connection con =conexionDB.getConnection();

        String query= "INSERT INTO clientes (nombre, telefono, correo ) VALUES(?,?,?)";

        try{
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1,clientes.getNombre());
            pst.setString(2,clientes.getTelefono());
            pst.setString(3,clientes.getCorreo());

            int resultado=pst.executeUpdate();
            if (resultado>0)
            {
                JOptionPane.showMessageDialog(null,"REGISTRO AGREGADO");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"NO SE HA AGREGADO NINGUN REGISTRO");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR DE EN LA EJECUCION");
        }
    }


    //actualizar
     /*
    ESTE CODIGO DE AGREGAR NOS SIRVE PARA ACTUALIZAR REGSTRO DESDE LA GUI A LA BASE DE DATOS
     */
    public void actualizar(Clientes clientes)
    {
        Connection con =conexionDB.getConnection();

        String query= "UPDATE clientes SET nombre = ?,telefono = ?,correo = ? WHERE id_cliente= ?";

        try{
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1,clientes.getNombre());
            pst.setString(2,clientes.getTelefono());
            pst.setString(3,clientes.getCorreo());
            pst.setInt(4,clientes.getId_clientes());

            int resultado=pst.executeUpdate();
            if (resultado>0)
            {
                JOptionPane.showMessageDialog(null,"REGISTRO ACTUALIZADO CON EXITO ");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"NO SE HA ACTUALIZADO NINGUN REGISTRO");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR  EN LA EJECUCION");
        }
    }

    //eliminar dato
     /*
    ESTE CODIGO DE ELIMINAR NOS SIRVE PARA AGREGAR REGSTRO DESDE EL GUI A LA BASE DE DATOS
     */

    public void eliminar (int id)
    {
        Connection con =conexionDB.getConnection();

        String query= "DELETE FROM clientes WHERE id_cliente= ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id);


            int resultado=pst.executeUpdate();
            if (resultado>0)
            {
                JOptionPane.showMessageDialog(null,"REGISTRO ELIMINADO CON EXITO ");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"NO SE HA ELIMINADO NINGUN REGISTRO");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR  EN LA EJECUCION");
        }
    }
}
