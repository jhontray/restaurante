package Vista;

import Conexion.ConexionDB;
import Controlador.EmpleadosDao;
import Modelo.Empleados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadosGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBoxCargo; // Actualizado
    private JTextField textField4;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton agregarButtonButton;
    private JButton editarButtonButton;
    private JCheckBox checkBox1;

    EmpleadosDao empleadosDao = new EmpleadosDao();
    ConexionDB conexionDB = new ConexionDB();

    public EmpleadosGUI() {
        // Opciones del ComboBox
        comboBoxCargo.addItem("Mesero");
        comboBoxCargo.addItem("Chef");
        comboBoxCargo.addItem("Cajero");
        comboBoxCargo.addItem("Administrador");

        obtenerDatos();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String cargo = (String) comboBoxCargo.getSelectedItem();
                String salario = textField4.getText();

                Empleados empleados = new Empleados(1, nombre, cargo, salario);
                empleadosDao.agregar(empleados);
                obtenerDatos();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                String nombre = textField2.getText();
                String cargo = (String) comboBoxCargo.getSelectedItem();
                String salario = textField4.getText();

                Empleados empleados = new Empleados(id, nombre, cargo, salario);
                empleadosDao.editar(empleados);
                obtenerDatos();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                empleadosDao.eliminar(id);
                obtenerDatos();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    textField1.setText((String) table1.getValueAt(filaSeleccionada, 0));
                    textField2.setText((String) table1.getValueAt(filaSeleccionada, 1));
                    comboBoxCargo.setSelectedItem(table1.getValueAt(filaSeleccionada, 2)); // Actualizado
                    textField4.setText((String) table1.getValueAt(filaSeleccionada, 3));
                }
            }
        });
    }

    public JPanel getPanel() {
        return main;
    }

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Empleado");
        model.addColumn("Nombre");
        model.addColumn("Cargo");
        model.addColumn("Salario");
        table1.setModel(model);

        String[] dato = new String[4];
        Connection con = conexionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM empleado");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmpleadosGUI");
        frame.setContentPane(new EmpleadosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

