package Controlador;

import Conexion.ConexionDB;
import Modelo.Reportes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportesDao {

    public List<Reportes> obtenerReporteDiario(String fecha) {
        String sql = "SELECT id_orden, fecha, total FROM Orden WHERE DATE(fecha) = ?";
        List<Reportes> reportes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fecha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reportes.add(new Reportes(
                        rs.getInt("id_orden"),
                        rs.getDate("fecha"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reporte diario: " + e.getMessage());
        }

        return reportes;
    }

    public List<Reportes> obtenerReporteSemanal(String inicio, String fin) {
        String sql = "SELECT id_orden, fecha, total FROM Orden WHERE DATE(fecha) BETWEEN ? AND ?";
        List<Reportes> reportes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inicio);
            stmt.setString(2, fin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reportes.add(new Reportes(
                        rs.getInt("id_orden"),
                        rs.getDate("fecha"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reporte semanal: " + e.getMessage());
        }

        return reportes;
    }

    public List<Reportes> obtenerReporteMensual(String mes) {
        String sql = "SELECT id_orden, fecha, total FROM Orden WHERE DATE_FORMAT(fecha, '%Y-%m') = ?";
        List<Reportes> reportes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mes); // Formato: "2025-04"
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reportes.add(new Reportes(
                        rs.getInt("id_orden"),
                        rs.getDate("fecha"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reporte mensual: " + e.getMessage());
        }

        return reportes;
    }
}
