package Vista;
import Modelo.Orden;
import Controlador.OrdenDao;
import Conexion.ConexionDB;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrdenGUI {
    private JPanel main;
    private JButton agregarButton;
    private JButton atualizarButton;
    private JButton eliminarButton;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;

    OrdenDao ordenDao = new OrdenDao();

    public OrdenGUI() {

        comboBox1.addItem("preparacion");
        comboBox1.addItem("servido");
        comboBox1.addItem("pago");


        obtenerDatos();
        //funcion para agragar los datos a la BD

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int id_cliente = Integer.parseInt(textField2.getText());
                    int id_Empleado = Integer.parseInt(textField3.getText());
                    int id_mesa = Integer.parseInt(textField4.getText());
                    String estado = ((String) comboBox1.getSelectedItem()).trim();

                    System.out.println("Estado seleccionado: '" + estado + "'");

                    Orden orden = new Orden(0,id_cliente, id_Empleado, id_mesa,estado);
                    ordenDao.agregar(orden);

                    obtenerDatos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "verifica que los campos ID sean numeros");
                }
            }
        });


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selecFile = table1.getSelectedRow();
                // Verificar si se seleccionó una fila válida
                if (selecFile >= 0) {
                    textField1.setText((String) table1.getValueAt(selecFile, 0)); // id_orden
                    textField2.setText((String) table1.getValueAt(selecFile, 4)); // id_cliente
                    textField3.setText((String) table1.getValueAt(selecFile, 5)); // id_empleado
                    textField4.setText((String) table1.getValueAt(selecFile, 6)); // id_mesa
                    comboBox1.setSelectedItem((String) table1.getValueAt(selecFile, 3)); // estado






                }
            }
        });
    }

    ConexionDB conexionDB=new ConexionDB();

    public void obtenerDatos(){

        /* diseño de la tabla a mostrar
        */

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID_Orden");
        model.addColumn("Fecha");
        model.addColumn("Total");
        model.addColumn("Estado");
        model.addColumn("ID_Cliente");
        model.addColumn("ID_Empelado");
        model.addColumn("ID_Mesa");
        table1.setModel(model);

        Connection con = conexionDB.getConnection();

        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orden");

            while(rs.next()){

                String[]dato = new String[7];
                        dato[0]=rs.getString(1);//id_orden
                        dato[1]=rs.getString(2);//fecha
                        dato[2]=rs.getString(3);//total
                        dato[3]=rs.getString(4);//estado
                        dato[4]=rs.getString(5);//id_cliente
                        dato[5]=rs.getString(6);//id_empleado
                        dato[6]=rs.getString(7);//id_mesa

                        model.addRow(dato);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("OrdenGUI");
        frame.setContentPane(new OrdenGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
