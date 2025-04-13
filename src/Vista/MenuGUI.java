package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class MenuGUI {

    private JPanel main;
    private JButton mesasButton;
    private JButton ordenButton;
    private JButton clienteButton;
    private JButton pedidosButton;
    private JButton produtosButton;
    private JButton empleadosButton;

    private JPanel contenedorPanel;

    public MenuGUI(){

        /*permitiendo cambiar entre diferentes vistas*/

       contenedorPanel.setLayout(new CardLayout());


       /*se crea para ver la interfas del correspondiente gui*/

       ClientesGUI clientesGUI =new ClientesGUI();
        contenedorPanel.add(clientesGUI.getPanel(),"clientes");

        OrdenGUI ordenGUI =new OrdenGUI();
        contenedorPanel.add(ordenGUI.getPanel(), "orden");

        Ordenes_ProductosGUI ordenesProductosGUI =new Ordenes_ProductosGUI();
        contenedorPanel.add(ordenesProductosGUI.getPanel(), "pedidos");

        MesasGUI mesasGUI =new MesasGUI();
        contenedorPanel.add(mesasGUI.getPanel(), "mesas");

        ProductosGUI productosGUI =new ProductosGUI();
        contenedorPanel.add(productosGUI.getPanel(), "productos");

        EmpleadosGUI empleadosGUI =new EmpleadosGUI();
        contenedorPanel.add(empleadosGUI.getPanel(), "empleados");

/* codigo para asociar los botones al codigo anterior */

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CardLayout cliente = (CardLayout) (contenedorPanel.getLayout());
                cliente.show(contenedorPanel,"clientes");

            }
        });
        ordenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CardLayout orden = (CardLayout) (contenedorPanel.getLayout());
                orden.show(contenedorPanel,"orden");

            }
        });
        mesasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout mesas = (CardLayout) contenedorPanel.getLayout();
                mesas.show(contenedorPanel, "mesas");
            }
        });

        pedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout pedidos = (CardLayout) contenedorPanel.getLayout();
                pedidos.show(contenedorPanel, "pedidos");
            }
        });

        produtosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout productos = (CardLayout) contenedorPanel.getLayout();
                productos.show(contenedorPanel, "productos");
            }
        });

        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout empleados = (CardLayout) contenedorPanel.getLayout();
                empleados.show(contenedorPanel, "empleados");
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MenuGUI"); // Crear ventana con título
        frame.setContentPane(new MenuGUI().main); // Establecer panel principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar app al cerrar ventana
        frame.pack(); // Ajustar tamaño automáticamente
        frame.setVisible(true); // Mostrar ventana
    }
}
