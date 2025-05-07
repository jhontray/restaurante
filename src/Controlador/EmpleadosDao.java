package Controlador;

import Conexion.ConexionDB;
import Modelo.Empleados;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase EmpleadosDao
 *
 * Esta clase forma parte del paquete Controlador y se encarga de manejar las operaciones
 * CRUD (Crear, Leer, Actualizar y Eliminar) sobre la tabla "empleados" en la base de datos.
 */
public class EmpleadosDao {

    // Objeto para manejar la conexión con la base de datos
    ConexionDB conexionDB = new ConexionDB();

    /**
     * Método para agregar un nuevo empleado a la base de datos
     *
     * @param empleados Objeto Empleados con los datos a insertar
     */
    public void agregar(Empleados empleados) {
        Connection con = conexionDB.getConnection();
        String query = "INSERT INTO empleado (nombre, cargo, salario) VALUES (?, ?, ?)";

        try {
            // Preparar la sentencia SQL con los datos del objeto empleado
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, empleados.getNombre());
            pst.setString(2, empleados.getCargo());
            pst.setString(3, empleados.getSalario());

            int resultado = pst.executeUpdate();

            // Verificar si la inserción fue exitosa
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO AGREGADO");
            } else {
                JOptionPane.showMessageDialog(null, "NO SE HA AGREGADO NINGÚN REGISTRO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR EN LA EJECUCIÓN");
        }
    }

    /**
     * Método para actualizar un empleado existente en la base de datos
     *
     * @param empleados Objeto Empleados con los datos modificados
     */
    public void editar(Empleados empleados) {
        Connection con = conexionDB.getConnection();
        String query = "UPDATE empleado SET nombre = ?, cargo = ?, salario = ? WHERE id_empleado = ?";

        try {
            // Preparar la sentencia SQL con los nuevos datos
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, empleados.getNombre());
            pst.setString(2, empleados.getCargo());
            pst.setString(3, empleados.getSalario());
            pst.setInt(4, empleados.getId_empleado());

            int resultado = pst.executeUpdate();

            // Verificar si se actualizó al menos un registro
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ACTUALIZADO CON ÉXITO");
            } else {
                JOptionPane.showMessageDialog(null, "NO SE HA ACTUALIZADO NINGÚN REGISTRO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR EN LA EJECUCIÓN");
        }
    }

    /**
     * Método para eliminar un empleado de la base de datos según su ID
     *
     * @param id ID del empleado a eliminar
     */
    public void eliminar(int id) {
        Connection con = conexionDB.getConnection();
        String query = "DELETE FROM empleado WHERE id_empleado = ?";

        try {
            // Preparar y ejecutar la sentencia de eliminación
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int resultado = pst.executeUpdate();

            // Verificar si se eliminó al menos un registro
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO CON ÉXITO");
            } else {
                JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO NINGÚN REGISTRO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR EN LA EJECUCIÓN");
        }
    }
}

