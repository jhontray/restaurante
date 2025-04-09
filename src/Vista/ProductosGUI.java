package Vista;
import Conexion.ConexionDB;
import Controlador.ProductosDao;
import Modelo.Productos;

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

public class ProductosGUI {
    private JPanel main;//PANEL PRINCIPAL
    private JTextField textField1;//ID_PRODCUTO
    private JTextField textField2;//NNOMBRE
    private JTextField textField3;//PRECIO
    private JTextField textField4;//CATEGORIA
    private JList list1;//BALIDACION
    private JTable table1;//DB
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;



    ProductosDao productosDao=new ProductosDao();

    /*encabezado para coemnsar a configurar lo que ara los botones y los espacios para agregar carateres

     */

    public ProductosGUI() {
// configuracion de Jlist para disponibilidad
        DefaultListModel<String> disponibilidadmodel = new DefaultListModel<>();
        disponibilidadmodel.addElement("si");
        disponibilidadmodel.addElement("no");
        list1.setModel(disponibilidadmodel);

        obtenerDatos();

        /* funcionde enviar informacion digitada en los recuadros dandole al boton agregar

         */
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*SE UTILIZO EL TRY PARA REALIZAR UNA VALIDADCION EN PRECIO Y QUE AL INGRESAR INFORAMCION SEA NUMEROS Y NO LETRAS

                 */
                try {
                    String nombre = textField2.getText();
                    double precio = Double.parseDouble(textField3.getText());
                    String categoria = textField4.getText();
                    boolean disponibilidad = list1.getSelectedValue().equals("si");

                    Productos productos = new Productos(0, nombre, categoria, precio, disponibilidad);
                    productosDao.agregar(productos);

                    obtenerDatos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El precio debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*editar o atualizar funciona para modificar informacion existente en la BD

         */
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                double precio = Double.parseDouble(textField3.getText());
                String categoria = textField4.getText();
                boolean disponibilidad = list1.getSelectedValue().equals("si");
                int id = Integer.parseInt(textField1.getText());


                Productos productos = new Productos(id, nombre, categoria, precio, disponibilidad);
                productosDao.atualizar(productos);

                obtenerDatos();

            }
        });

        /*eliminar funciona para borrar datos almacenados en la DB

         */
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                productosDao.eliminar(id);
                obtenerDatos();

            }
        });

        /*CODIGO REALIZADO PARA PODER CELECIONAR DIRETAMENTEN DE LA BASE DE DATOS CON EL MOUSE Y FACILITAR LA EDICION O ELIMINACION

         */
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selecFile=table1.getSelectedRow();
                // Verificar si se seleccionó una fila válida
                if (selecFile >= 0) {
                    textField1.setText((String) table1.getValueAt(selecFile, 0));//ID-PRODUTO
                    textField2.setText((String) table1.getValueAt(selecFile, 1));//NOMBRE
                    textField3.setText((String) table1.getValueAt(selecFile, 3));//PRECIO
                    textField4.setText(String.valueOf(table1.getValueAt(selecFile, 2)));//CATEGORIA

                    // Actualizar la selección de disponibilidad
                    String disponibilidad = (String) table1.getValueAt(selecFile, 4);
                    list1.setSelectedValue(disponibilidad, true);
                }

            }
        });
    }

    ConexionDB conexionDB = new ConexionDB();

    /*codigo para almacenar los datos y obtener todos lo datos de la DB
    */

    public void obtenerDatos() {

        /*diseño de la tabla de datos gui

         */

        DefaultTableModel model =new DefaultTableModel();

        model.addColumn("Id Producto");
        model.addColumn("Nombre");
        model.addColumn("categoria");
        model.addColumn("precio");
        model.addColumn("Disponibilidad");

        table1.setModel(model);

        Connection con = conexionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM productos");

            while (rs.next()) {
                String[] dato = new String[5];
                dato[0] = rs.getString(1);//ID_PRODUTO
                dato[1] = rs.getString(2);//NOMBRE
                dato[2] = rs.getString(3);//CATEGORIA
                dato[3] = rs.getString(4);//PRECIO
                dato[4] = rs.getBoolean(5)?"si":"no";//DISPONIBILIDAD

                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ProductosGUI");
        frame.setContentPane(new ProductosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}