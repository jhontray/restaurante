package Controlador;

import Conexion.ConexionDB;
import Modelo.Ordenes_Productos;

import java.sql.*;
import java.util.ArrayList;

public class OrdenPDAO{

    private ConexionDB conexionDB = new ConexionDB();

    /*
    /ESTE CODIGO NOS PERMITE VER LOS DATOS GUARDADOS EN LA BASE DE DATOS  DE LA TABLA ORDEN_PRODUCTO .
    NOS PERMITE CARGAR LOS DATOS A UN JTABLE Y MOSTRARLOS EN GUI
     */

    public ArrayList<Ordenes_Productos> obtenerTodos() {
        ArrayList<Ordenes_Productos> lista = new ArrayList<>();

        String sql = "SELECT * FROM orden_producto";

        try (Connection con = conexionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ordenes_Productos op = new Ordenes_Productos(
                        rs.getInt("id_orden"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario")
                );
                lista.add(op);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
