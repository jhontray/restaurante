package ontrolador;

import Conexion.ConexionDB;
import modelo.Clientes;
import modelo.Empleados;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDAO {

    ConexionDB conexionDB = new ConexionDB();

    public static void eliminar(int id)
    {
        Connection con =ConexionDB.getConnection();

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

    public static void agregar(Empleados empleados) {
        Connection con =ConexionDB.getConnection();

        String query= "INSERT INTO Empleados (nombre, Cargo, Salario ) VALUES(?,?,?)";

        try{
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1,empleados.getNombre());
            pst.setString(2,empleados.getCargo());
            pst.setString(3,empleados.getSalario());

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
    }


    public void actualizar(Empleados empleados)
    {
        Connection con =ConexionDB.getConnection();

        String query= "UPDATE clientes SET nombre = ?,telefono = ?,correo = ? WHERE id_cliente= ?";

        try{
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1,empleados.getNombre());
            pst.setString(2,empleados.getCargo());
            pst.setString(3,empleados.getSalario());
            pst.setInt(4,Empleados.getId_Empleados());

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

    public void agregar(Modelo.Productos productos) {
        Connection con = ConexionDB.getConnection();

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

    public boolean registrarEmpleado(Empleados empleados) {
        String sql = "INSERT INTO Empleado (nombre, cargo, salario) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleados.getNombre());
            stmt.setString(2, empleados.getCargo());
            stmt.setDouble(3, empleados.getSalario());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al registrar empleado: " + e.getMessage());
            return false;
        }
    }

    public List<Empleados> obtenerEmpleados() {
        List<Empleados> empleados = new ArrayList<>();
        String sql = "SELECT * FROM Empleado";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                empleados.add(new Empleados(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener empleados: " + e.getMessage());
        }

        return empleados;
    }

    public boolean actualizarEmpleado(Empleados empleado) {
        String sql = "UPDATE Empleado SET nombre=?, cargo=?, salario=? WHERE id_empleado=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getCargo());
            stmt.setDouble(3, empleado.getSalario());
            stmt.setInt(4, empleado.getId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEmpleado(int id) {
        String sql = "DELETE FROM Empleado WHERE id_empleado=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}

public void main() {
}
