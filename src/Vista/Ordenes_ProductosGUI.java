package Vista;

import Conexion.ConexionDB;
import Modelo.Ordenes_Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class Ordenes_ProductosGUI {
    // Componentes de la interfaz de usuario
    private JPanel main;  // Panel principal de la interfaz
    private JComboBox<Integer> comboOrdenes;  // ComboBox para seleccionar la orden
    private JComboBox<Integer> comboProductos;  // ComboBox para seleccionar el producto
    private JTextField textFieldCantidad;  // Campo de texto para ingresar la cantidad del producto
    private JTextField textFieldPrecio;  // Campo de texto para mostrar el precio del producto
    private JButton agregarButton;  // Botón para agregar un producto a la orden
    private JTable tableOrden;  // Tabla donde se muestran los productos agregados a la orden
    private JButton eliminarSeleccionadoButton;  // Botón para eliminar el producto seleccionado en la tabla
    private JButton guardarOrdenButton;  // Botón para guardar la orden en la base de datos
    private JTextField textFieldNombreProducto;  // Campo de texto para mostrar el nombre del producto seleccionado
    private JTable mostrarBD;  // Tabla para mostrar los registros guardados en la base de datos
    private JButton eliminarBDButton;  // Botón para eliminar un registro de la base de datos

    // Modelos de las tablas
    private DefaultTableModel tableModel;  // Modelo de la tabla para la orden temporal
    private DefaultTableModel tableModelMostrarBD;  // Modelo de la tabla para mostrar los datos de la base de datos

    /*
    / Conexión a la base de datos
     */
    private ConexionDB conexionDB = new ConexionDB();
    private ArrayList<Ordenes_Productos> listaOrdenTemporal = new ArrayList<>();  // Lista temporal de productos en la orden

    public Ordenes_ProductosGUI() {
        /*
        / Inicializa los modelos de las tablas
         */
        tableModel = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Precio", "Subtotal"}, 0);
        tableOrden.setModel(tableModel);

        tableModelMostrarBD = new DefaultTableModel(new Object[]{"ID Orden", "ID Producto", "Cantidad", "Precio Unitario", "Subtotal"}, 0);
        mostrarBD.setModel(tableModelMostrarBD);

        /*
        / Cargar los datos de órdenes y productos desde la base de datos
         */
        cargarComboOrdenes();
        cargarComboProductos();
        cargarDatosEnTablaMostrarBD();

        /*
        / Acción para cargar los datos del producto seleccionado en el combo
         */
        comboProductos.addActionListener(e -> cargarDatosProductoSeleccionado());

        /*
        / Acción para agregar un producto a la tabla
         */
        agregarButton.addActionListener(e -> agregarProductoATabla());

        /*
        / Acción para eliminar un producto de la tabla
         */
        eliminarSeleccionadoButton.addActionListener(e -> eliminarProductoSeleccionado());

        /*
        / Acción para guardar la orden en la base de datos
         */
        guardarOrdenButton.addActionListener(e -> guardarOrdenEnBD());

        /*
        / Acción para eliminar un registro de la base de datos
         */
        eliminarBDButton.addActionListener(e -> eliminarRegistroBD());

        /*
        / Deshabilita la edición del nombre del producto
         */
        textFieldNombreProducto.setEditable(false);
    }

    /*
    / Método para cargar las órdenes en el JComboBox
     */
    private void cargarComboOrdenes() {
        try (Connection con = conexionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_orden FROM orden")) {

            while (rs.next()) {
                comboOrdenes.addItem(rs.getInt("id_orden"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    / Método para cargar los productos en el JComboBox
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

    /*
    / Método para cargar los detalles del producto seleccionado en los campos de texto
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

    /*
    / Método para agregar un producto a la tabla temporal
     */
    private void agregarProductoATabla() {
        try {
            Integer idOrden = (Integer) comboOrdenes.getSelectedItem();
            if (idOrden == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una orden antes de agregar productos.");
                return;
            }

            Integer idProducto = (Integer) comboProductos.getSelectedItem();
            if (idProducto == null) {
                JOptionPane.showMessageDialog(null, "Selecciona un producto válido.");
                return;
            }

            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            double precio = Double.parseDouble(textFieldPrecio.getText());
            double subtotal = cantidad * precio;

            // Agrega el producto a la tabla temporal y la lista
            tableModel.addRow(new Object[]{idProducto, cantidad, precio, subtotal});
            listaOrdenTemporal.add(new Ordenes_Productos(idOrden, idProducto, cantidad, precio));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor ingresa valores válidos.");
        }
    }

    // Método para eliminar un producto seleccionado de la tabla
    private void eliminarProductoSeleccionado() {
        int selectedRow = tableOrden.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de eliminar este producto de la orden?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                listaOrdenTemporal.remove(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto de la tabla para eliminar.");
        }
    }

    /*
    / Método para guardar la orden en la base de datos
     */
    private void guardarOrdenEnBD() {
        try (Connection con = conexionDB.getConnection()) {

            String sql = "INSERT INTO orden_producto (id_orden, id_producto, cantidad, precio_unitario) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "cantidad = cantidad + VALUES(cantidad), " +
                    "precio_unitario = VALUES(precio_unitario)";

             PreparedStatement ps = con.prepareStatement(sql);

            // Inserta los productos de la orden en la base de datos
            for (Ordenes_Productos op : listaOrdenTemporal) {
                ps.setInt(1, op.getIdOrden());
                ps.setInt(2, op.getIdProducto());
                ps.setInt(3, op.getCantidad());
                ps.setDouble(4, op.getPrecioUnitario());
                ps.addBatch();
            }

            ps.executeBatch();  // Ejecuta el batch para insertar todos los registros
            JOptionPane.showMessageDialog(null, "Orden guardada correctamente.");
            tableModel.setRowCount(0);  // Limpia la tabla temporal
            listaOrdenTemporal.clear();  // Limpia la lista temporal
            cargarDatosEnTablaMostrarBD();  // Recarga los datos en la tabla que muestra la base de datos

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la orden.");
        }
    }

    /*
    / Método para cargar los datos de la base de datos en la tabla de la interfaz
     */
    private void cargarDatosEnTablaMostrarBD() {
        tableModelMostrarBD.setRowCount(0);

        String sql = "SELECT * FROM orden_producto";

        try (Connection con = conexionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Muestra los datos de la base de datos en la tabla
            while (rs.next()) {
                int idOrden = rs.getInt("id_orden");
                int idProducto = rs.getInt("id_producto");
                int cantidad = rs.getInt("cantidad");
                double precioUnitario = rs.getDouble("precio_unitario");
                double subtotal = cantidad * precioUnitario;

                tableModelMostrarBD.addRow(new Object[]{idOrden, idProducto, cantidad, precioUnitario, subtotal});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    / Método para eliminar un registro de la base de datos
     */
    private void eliminarRegistroBD() {
        int selectedRow = mostrarBD.getSelectedRow();
        if (selectedRow >= 0) {
            int idOrden = (Integer) mostrarBD.getValueAt(selectedRow, 0);
            int idProducto = (Integer) mostrarBD.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de eliminar este registro de la base de datos?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection con = conexionDB.getConnection()) {
                    String sql = "DELETE FROM orden_producto WHERE id_orden = ? AND id_producto = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setInt(1, idOrden);
                    pst.setInt(2, idProducto);

                    int rowsAffected = pst.executeUpdate();  // Elimina el registro de la base de datos
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");
                        tableModelMostrarBD.removeRow(selectedRow);  // Elimina el registro de la tabla
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el registro.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar el registro.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un registro para eliminar.");
        }
    }

    // Método para obtener el panel principal
    public JPanel getPanel() {
        return main;
    }

    /*
    / Método main para ejecutar la interfaz
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ordenes_ProductosGUI");
        frame.setContentPane(new Ordenes_ProductosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
