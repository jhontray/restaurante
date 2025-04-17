package Controlador;

import Conexion.ConexionDB;
import Modelo.Mesas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MesasDao {

    ConexionDB conexionDB = new ConexionDB();

    public boolean registrarMesa(Mesas mesa) {
        String sql = "INSERT INTO Mesa (numero, capacidad, estado) VALUES (?, ?, ?)";
        try {
            Connection con = conexionDB.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, mesa.getNumero());
            stmt.setInt(2, mesa.getCapacidad());
            stmt.setString(3, mesa.getEstado());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar mesa: " + e.getMessage());
            return false;
        }
    }

    public List<Mesas> obtenerMesas() {
        List<Mesas> mesas = new ArrayList<>();
        String sql = "SELECT * FROM Mesa";
        try (Connection conn = conexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                mesas.add(new Mesas(
                        rs.getInt("id_mesa"),
                        rs.getInt("numero"),
                        rs.getInt("capacidad"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener mesas: " + e.getMessage());
        }

        return mesas;
    }

    public boolean actualizarMesa(Mesas mesa) {
        String sql = "UPDATE Mesa SET numero=?, capacidad=?, estado=? WHERE id_mesa=?";
        try (Connection conn = conexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mesa.getNumero());
            stmt.setInt(2, mesa.getCapacidad());
            stmt.setString(3, mesa.getEstado());
            stmt.setInt(4, mesa.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar mesa: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarMesa(int id) {
        String sql = "DELETE FROM Mesa WHERE id_mesa=?";
        try (Connection conn = conexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar mesa: " + e.getMessage());
            return false;
        }
    }
}




