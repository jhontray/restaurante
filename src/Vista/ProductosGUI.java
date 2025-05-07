package Vista;

import Conexion.ConexionDB;
import Controlador.ProductosDao;
import Modelo.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductosGUI {
    private JPanel main; // PANEL PRINCIPAL
    private JTextField textField1; // ID_PRODUCTO
    private JTextField textField2; // NOMBRE
    private JTextField textField3; // PRECIO
    private JTextField textField4; // CATEGORIA
    private JComboBox comboBoxDisponibilidad; // DISPONIBILIDAD
    private JTable table1; // DB
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;

    ProductosDao productosDao = new ProductosDao();
    ConexionDB conexionDB = new ConexionDB();

    public ProductosGUI() {
        // Configuración del JComboBox para disponibilidad
        comboBoxDisponibilidad.addItem("si");
        comboBoxDisponibilidad.addItem("no");

        obtenerDatos();

        // Agregar
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = textField2.getText();
                    double precio = Double.parseDouble(textField3.getText());
                    String categoria = textField4.getText();
                    boolean disponibilidad = comboBoxDisponibilidad.getSelectedItem().equals("si");

                    Productos productos = new Productos(0, nombre, categoria, precio, disponibilidad);
                    productosDao.agregar(productos);

                    obtenerDatos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El precio debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Editar
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = textField2.getText();
                    double precio = Double.parseDouble(textField3.getText());
                    String categoria = textField4.getText();
                    boolean disponibilidad = comboBoxDisponibilidad.getSelectedItem().equals("si");
                    int id = Integer.parseInt(textField1.getText());

                    Productos productos = new Productos(id, nombre, categoria, precio, disponibilidad);
                    productosDao.atualizar(productos);

                    obtenerDatos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en formato numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Eliminar
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(textField1.getText());
                    productosDao.eliminar(id);
                    obtenerDatos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Selecciona un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Selección con mouse en la tabla
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    textField1.setText((String) table1.getValueAt(filaSeleccionada, 0));
                    textField2.setText((String) table1.getValueAt(filaSeleccionada, 1));
                    textField4.setText((String) table1.getValueAt(filaSeleccionada, 2));
                    textField3.setText((String) table1.getValueAt(filaSeleccionada, 3));

                    String disponibilidad = (String) table1.getValueAt(filaSeleccionada, 4);
                    comboBoxDisponibilidad.setSelectedItem(disponibilidad);
                }
            }
        });
    }

    public JPanel getPanel() {
        return main;
    }

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id Producto");
        model.addColumn("Nombre");
        model.addColumn("Categoria");
        model.addColumn("Precio");
        model.addColumn("Disponibilidad");

        table1.setModel(model);

        Connection con = conexionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM productos");

            while (rs.next()) {
                String[] dato = new String[5];
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getBoolean(5) ? "si" : "no";

                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ProductosGUI");
        frame.setContentPane(new ProductosGUI().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
