package vista;

import Conexion.ConexionDB;
import Controlador.ClientesDao;
import Modelo.Clientes;
import controlador.EmpleadosDAO;
import modelo.Empleados;

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


    EmpleadosDAO empleadosDAO =new EmpleadosDAO();
    /*
     ESTE CODIGO DE AGREGARBUTTON NOS SIRVE PARA AGREGAR REGSiTRO DESDE EL GUI A LA BASE DE DATOS
     USANDO LOS BOTONES DE AGREGAR Y LLENANDO LOS CAMPOS DE LA APP
      */
    public EmpleadosGUI() {
        obtenerDatos();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre= textField1.getText();
                String cargo = textField2.getText();
                String salario= textField3.getText();

                Empleados empleados=new Empleados(1,nombre,salario, cargo);
                EmpleadosDAO.agregar(empleados);


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
                String nombre= textField1.getText();
                String cargo = textField2.getText();
                String salario= textField3.getText();
                int id = Integer.parseInt(textField1.getText());


                Empleados empleados=new Empleados(id,nombre,cargo,salario);
                EmpleadosDAO.actualizar(empleados);

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
                int id=Integer.parseInt(textField1.getText());
                EmpleadosDAO.eliminar (id);
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

                int selecFile=table1.getSelectedRow();
                if (selecFile>=0)
                {
                    textField1.setText((String) table1.getValueAt(selecFile,0));
                    textField2.setText((String) table1.getValueAt(selecFile,1));
                    textField3.setText((String) table1.getValueAt(selecFile,2));
                    textField4.setText((String) table1.getValueAt(selecFile,3));
                }
            }
        });
    }

    ConexionDB conexionDB = new ConexionDB();

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Empleados");
        model.addColumn("Nombre");
        model.addColumn("Salario");
        model.addColumn("Cargo");

        table1.setModel(model);

        String[] dato = new String[4];

        Connection con = conexionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

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
    public static void main (String[]args)
    {
        JFrame frame = new JFrame("EmpleadoGUI");
        frame.setContentPane(new EmpleadosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
