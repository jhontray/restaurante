package Vista;

import Conexion.ConexionDB;
import Modelo.Ordenes_Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Interfaz gráfica para gestionar la relación entre órdenes y productos.
 * Permite seleccionar una orden, agregar productos con cantidad y precio,
 * visualizar la tabla de productos agregados y guardar los datos en la base de datos.
 */
public class Ordenes_ProductosGUI {
    // Componentes visuales del formulario
    private JPanel main;
    private JComboBox<Integer> comboOrdenes;            // Lista de órdenes
    private JComboBox<Integer> comboProductos;          // Lista de productos
    private JTextField textFieldCantidad;               // Cantidad de productos
    private JTextField textFieldPrecio;                 // Precio unitario del producto
    private JButton agregarButton;                      // Botón para agregar a la tabla
    private JTable tableOrden;                          // Tabla que muestra productos agregados
    private JButton eliminarSeleccionadoButton;         // Botón para eliminar producto seleccionado
    private JButton guardarOrdenButton;                 // Botón para guardar en BD
    private JTextField textFieldNombreProducto;         // Campo para mostrar el nombre del producto

    private DefaultTableModel tableModel;               // Modelo de tabla para JTable
    private ConexionDB conexionDB = new ConexionDB();   // Conexión a la base de datos
    private ArrayList<Ordenes_Productos> listaOrdenTemporal = new ArrayList<>(); // Lista temporal de productos agregados

    /**
     * Constructor de la interfaz
     */
    public Ordenes_ProductosGUI() {
        // Inicializar el modelo de la tabla con columnas
        tableModel = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Precio", "Subtotal"}, 0);
        tableOrden.setModel(tableModel);

        // Cargar datos en los combos
        cargarComboOrdenes();
        cargarComboProductos();

        // Eventos al interactuar con los componentes
        comboProductos.addActionListener(e -> cargarDatosProductoSeleccionado());
        agregarButton.addActionListener(e -> agregarProductoATabla());
        eliminarSeleccionadoButton.addActionListener(e -> eliminarProductoSeleccionado());
        guardarOrdenButton.addActionListener(e -> guardarOrdenEnBD());

        // El campo del nombre del producto es solo lectura
        textFieldNombreProducto.setEditable(false);
    }

    /**
     * Cargar los ID de las órdenes desde la base de datos al comboBox
     */
    private void cargarComboOrdenes() {
        try (Connection con = conexionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_orden FROM ordenes")) {

            while (rs.next()) {
                comboOrdenes.addItem(rs.getInt("id_orden"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cargar los ID de los productos desde la base de datos al comboBox
     */
    private void cargarComboProductos() {
        try (Connection con = conexionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_producto FROM productos")) {

            while (rs.next()) {
                comboProductos.addItem(rs.getInt("id_producto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Al seleccionar un producto, cargar su nombre y precio desde la base de datos
     */
    private void cargarDatosProductoSeleccionado() {
        Integer idProducto = (Integer) comboProductos.getSelectedItem();
        if (idProducto == null) return;

        try (Connection con = conexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT nombre, precio FROM productos WHERE id_producto = ?")) {

            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");

                textFieldNombreProducto.setText(nombre);
                textFieldPrecio.setText(String.valueOf(precio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Agrega el producto con su cantidad y precio a la tabla y a la lista temporal
     */
    private void agregarProductoATabla() {
        try {
            int idProducto = (Integer) comboProductos.getSelectedItem();
            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            double precio = Double.parseDouble(textFieldPrecio.getText());
            double subtotal = cantidad * precio;

            // Mostrar en la tabla visual
            tableModel.addRow(new Object[]{idProducto, cantidad, precio, subtotal});

            // Guardar en la lista temporal
            int idOrden = (Integer) comboOrdenes.getSelectedItem();
            listaOrdenTemporal.add(new Ordenes_Productos(idOrden, idProducto, cantidad, precio));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor ingresa valores válidos.");
        }
    }

    /**
     * Elimina el producto seleccionado de la tabla y de la lista temporal
     */
    private void eliminarProductoSeleccionado() {
        // Obtenemos la fila seleccionada de la tabla
        int selectedRow = tableOrden.getSelectedRow();

        // Verificamos si hay una fila seleccionada
        if (selectedRow >= 0) {
            // Confirmamos con el usuario antes de eliminar
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de eliminar este producto de la orden?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Eliminamos la fila de la tabla visual
                tableModel.removeRow(selectedRow);

                // También eliminamos el objeto de la lista temporal en memoria
                listaOrdenTemporal.remove(selectedRow);
            }

        } else {
            // Si no hay ninguna fila seleccionada, mostramos un mensaje
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto de la tabla para eliminar.");
        }
    }


    /**
     * Guarda todos los productos agregados temporalmente en la base de datos
     */
    private void guardarOrdenEnBD() {
        try (Connection con = conexionDB.getConnection()) {
            String sql = "INSERT INTO ordenes_productos (id_orden, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            for (Ordenes_Productos op : listaOrdenTemporal) {
                ps.setInt(1, op.getIdOrden());
                ps.setInt(2, op.getIdProducto());
                ps.setInt(3, op.getCantidad());
                ps.setDouble(4, op.getPrecioUnitario());
                ps.addBatch(); // Añadir a lote
            }

            ps.executeBatch(); // Ejecutar lote de inserciones
            JOptionPane.showMessageDialog(null, "Orden guardada correctamente.");
            tableModel.setRowCount(0);          // Limpiar tabla
            listaOrdenTemporal.clear();         // Limpiar lista temporal

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la orden.");
        }
    }

    /**
     * Método principal para mostrar la ventana
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ordenes_ProductosGUI");
        frame.setContentPane(new Ordenes_ProductosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
