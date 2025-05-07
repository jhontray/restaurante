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

    // Producto más vendido
    public List<Reportes> obtenerProductoMasVendido() {
        String sql = "SELECT p.nombre, SUM(op.cantidad) AS total_vendido " +
                "FROM Productos p " +
                "JOIN Orden_Producto op ON p.id_producto = op.id_producto " +
                "GROUP BY p.nombre " +
                "ORDER BY total_vendido DESC " +
                "LIMIT 1";

        List<Reportes> reportes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                double totalVendido = rs.getDouble("total_vendido");

                reportes.add(new Reportes(nombre, totalVendido));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener producto más vendido: " + e.getMessage());
        }

        return reportes;
    }

    // Clientes que más compran
    public List<Reportes> obtenerClientesQueMasCompran() {
        String sql = "SELECT c.nombre, SUM(o.total) AS total_comprado " +
                "FROM Clientes c " +
                "JOIN Orden o ON c.id_cliente = o.id_cliente " +
                "GROUP BY c.nombre " +
                "ORDER BY total_comprado DESC";

        List<Reportes> reportes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double totalComprado = rs.getDouble("total_comprado");

                reportes.add(new Reportes(nombre, totalComprado));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener clientes que más compran: " + e.getMessage());
        }

        return reportes;
    }
}


