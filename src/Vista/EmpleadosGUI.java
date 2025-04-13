package Vista;

import Conexion.ConexionDB;
import Controlador.EmpleadosDao;
import Modelo.Empleados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadosGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;


    EmpleadosDao empleadosDao = new EmpleadosDao();

    /*
     ESTE CODIGO DE AGREGARBUTTON NOS SIRVE PARA AGREGAR REGSTRO DESDE EL GUI A LA BASE DE DATOS
     USANDO LOS BOTONES DE AGREGAR Y LLENANDO LOS CAMPOS DE LA APP
      */
    public EmpleadosGUI() {
        obtenerDatos();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String cargo = textField3.getText();
                String salario = textField4.getText();

                Empleados empleados = new Empleados(1, nombre, cargo, salario);
                empleadosDao.agregar(empleados);


                obtenerDatos();

            }
        });
          /*
     ESTE CODIGO DE EDITARRBUTTON NOS SIRVE PARA EDITAR REGSTROS DESDE EL GUI
     USANDO LOS BOTONES DE EDITAR Y LLENANDO LOS CAMPOS QUE DESEEMOS EDITAR
      */

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String cargo = textField3.getText();
                String salario = textField4.getText();
                int id = Integer.parseInt(textField1.getText());


                Empleados empleados = new Empleados(id, nombre, cargo, salario);
                empleadosDao.editar(empleados);

                obtenerDatos();

            }
        });

          /*
     ESTE CODIGO DE ELIMINARRBUTTON NOS SIRVE PARA EDITAR REGSTROS DESDE EL GUI
     USANDO LOS BOTONES DE ELIMINAR , ESCOGIENDO EL ID QUE DESEEMOS ELIMINAR Y LE DAREMOS AL BOTON ELIMINAR.
      */
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                empleadosDao.eliminar(id);
                obtenerDatos();

            }
        });

          /*
     ESTE CODIGO NOS PERMITE LLENAR LOS CAMPOS DEL TEXTFIELD CON REGISTROS YA EXISTENTES EN LA BASE DE DATOS DESDE LA GUI , SOLO DANDO UN CLICK
     EN UNO DE LOS NOMBRES O DATOS Y TRAEREMOS COMO TAL A LOS ESPACIOS DEL TEXTFIEL Y ASI PODREMOS EDITAR O ELIMINARLOS MAS FACIL
      */
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int selecFile = table1.getSelectedRow();
                if (selecFile >= 0) {
                    textField1.setText((String) table1.getValueAt(selecFile, 0));
                    textField2.setText((String) table1.getValueAt(selecFile, 1));
                    textField3.setText((String) table1.getValueAt(selecFile, 2));
                    textField4.setText((String) table1.getValueAt(selecFile, 3));
                }
            }
        });
    }


    /*permite aceso en  en la interfa menugui
     */
    public JPanel getPanel(){
        return main;
    }

    // Instancia de la clase que maneja la conexión a la base de datos
    ConexionDB conexionDB = new ConexionDB();

    /**
     * Método obtenerDatos
     * <p>
     * Este método se encarga de:
     * - Crear un modelo de tabla con las columnas necesarias
     * - Ejecutar una consulta SQL para obtener los datos de la tabla "empleados"
     * - Llenar el JTable (table1) con los resultados obtenidos desde la base de datos
     */
    public void obtenerDatos() {
        // Crear un nuevo modelo de tabla
        DefaultTableModel model = new DefaultTableModel();

        // Definir las columnas de la tabla
        model.addColumn("ID Empleado");
        model.addColumn("Nombre");
        model.addColumn("Cargo");
        model.addColumn("Salario");

        // Asignar el modelo al JTable
        table1.setModel(model);

        // Arreglo temporal para almacenar los datos de cada fila
        String[] dato = new String[4];

        // Obtener conexión a la base de datos
        Connection con = conexionDB.getConnection();

        try {
            // Crear un statement para ejecutar la consulta SQL
            Statement stmt = con.createStatement();

            // Ejecutar la consulta y obtener los resultados
            ResultSet rs = stmt.executeQuery("SELECT * FROM empleado");

            // Recorrer los resultados y agregarlos al modelo de la tabla
            while (rs.next()) {
                dato[0] = rs.getString(1); // ID Empleado
                dato[1] = rs.getString(2); // Nombre
                dato[2] = rs.getString(3); // Cargo
                dato[3] = rs.getString(4); // Salario

                // Agregar la fila al modelo
                model.addRow(dato);
            }
        } catch (SQLException e) {
            // Imprimir cualquier error que ocurra al ejecutar la consulta
            e.printStackTrace();
        }
    }

    /**
     * Método main
     * <p>
     * Punto de entrada principal de la aplicación.
     * Se encarga de crear la ventana principal (JFrame) y mostrar la interfaz gráfica EmpleadosGUI.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("EmpleadosGUI"); // Crear ventana con título
        frame.setContentPane(new EmpleadosGUI().main); // Establecer panel principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar app al cerrar ventana
        frame.pack(); // Ajustar tamaño automáticamente
        frame.setVisible(true); // Mostrar ventana
    }
}
