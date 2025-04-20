package Controlador;

import Modelo.Ordenes_Productos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) encargada de manejar las operaciones
 * de base de datos para la entidad Ordenes_Productos.
 * Proporciona métodos para agregar, editar, eliminar y listar registros.
 */
public class OrdenProductoDao {
    private Connection conexion;

    /**
     * Constructor que recibe una conexión activa a la base de datos.
     * @param conexion conexión a la base de datos
     */
    public OrdenProductoDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Método para agregar un nuevo registro a la tabla ordenes_productos.
     * @param op Objeto Ordenes_Productos a insertar
     * @return true si se insertó correctamente, false si ocurrió un error
     */
    public boolean agregarOrdenProducto(Ordenes_Productos op) {
        String sql = "INSERT INTO orden_producto (id_orden, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, op.getIdOrden());
            stmt.setInt(2, op.getIdProducto());
            stmt.setInt(3, op.getCantidad());
            stmt.setDouble(4, op.getPrecioUnitario());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para editar un registro existente en la tabla ordenes_productos.
     * Se actualiza cantidad y precio_unitario según id_orden e id_producto.
     * @param op Objeto Ordenes_Productos con los datos actualizados
     * @return true si se actualizó correctamente, false si ocurrió un error
     */
    public boolean editarOrdenProducto(Ordenes_Productos op) {
        String sql = "UPDATE orden_producto SET cantidad = ?, precio_unitario = ? WHERE id_orden = ? AND id_producto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, op.getCantidad());
            stmt.setDouble(2, op.getPrecioUnitario());
            stmt.setInt(3, op.getIdOrden());
            stmt.setInt(4, op.getIdProducto());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al editar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un registro de la tabla ordenes_productos
     * usando como identificadores el id de la orden y el id del producto.
     * @param idOrden ID de la orden
     * @param idProducto ID del producto
     * @return true si se eliminó correctamente, false si ocurrió un error
     */
    public boolean eliminarOrdenProducto(int idOrden, int idProducto) {
        String sql = "DELETE FROM orden_producto WHERE id_orden = ? AND id_producto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idOrden);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método que retorna una lista con todos los registros de la tabla ordenes_productos.
     * @return Lista de objetos Ordenes_Productos
     */
    public List<Ordenes_Productos> obtenerTodos() {
        List<Ordenes_Productos> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_producto";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}

