package Vista;

import SERVER.MainMenu; // Importa la clase MainMenu del chat
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI {

    private JPanel main;
    private JButton mesasButton;
    private JButton ordenButton;
    private JButton clienteButton;
    private JButton pedidosButton;
    private JButton produtosButton;
    private JButton empleadosButton;
    private JButton chatButton; // El botón del chat
    private JButton reportesButton; // El botón de Reportes

    private JPanel contenedorPanel;
    private MainMenu chatMainMenu; // Instancia de MainMenu

    public MenuGUI(){
        // Inicializar el JPanel main
        main = new JPanel(new BorderLayout()); // Puedes usar otro LayoutManager si lo prefieres

        /*permitiendo cambiar entre diferentes vistas*/
        contenedorPanel = new JPanel(new CardLayout()); // Inicializa contenedorPanel aquí
        main.add(contenedorPanel, BorderLayout.CENTER); // Añade contenedorPanel al main

        /*se crea para ver la interfas del correspondiente gui*/
        ClientesGUI clientesGUI =new ClientesGUI();
        contenedorPanel.add(clientesGUI.getPanel(),"clientes");

        OrdenGUI ordenGUI =new OrdenGUI();
        contenedorPanel.add(ordenGUI.getPanel(), "orden");

        Ordenes_ProductosGUI ordenesProductosGUI =new Ordenes_ProductosGUI();
        contenedorPanel.add(ordenesProductosGUI.getPanel(), "pedidos");

        MesasGUI mesasGUI =new MesasGUI();
        contenedorPanel.add(mesasGUI.getMainPanel(), "mesas"); // Usar getMainPanel()

        ProductosGUI productosGUI =new ProductosGUI();
        contenedorPanel.add(productosGUI.getPanel(), "productos");

        EmpleadosGUI empleadosGUI =new EmpleadosGUI();
        contenedorPanel.add(empleadosGUI.getPanel(), "empleados");

        // Crear instancia de MainMenu
        chatMainMenu = new MainMenu();
        // Añadir el panel del MainMenu al contenedor
        contenedorPanel.add(chatMainMenu.getMainPanel(), "chat");

        // Crear instancia de ReportesGUI
        ReportesGUI reportesGUI = new ReportesGUI();
        // Añadir el panel de ReportesGUI al contenedor
        contenedorPanel.add(reportesGUI.getMainPanel(), "reportes");

        // Crear panel para los botones del menú principal
        JPanel menuBotonesPanel = new JPanel(new FlowLayout());
        menuBotonesPanel.add(mesasButton);
        menuBotonesPanel.add(ordenButton);
        menuBotonesPanel.add(clienteButton);
        menuBotonesPanel.add(pedidosButton);
        menuBotonesPanel.add(produtosButton);
        menuBotonesPanel.add(empleadosButton);
        menuBotonesPanel.add(chatButton);
        menuBotonesPanel.add(reportesButton); // Añadir el botón de Reportes
        main.add(menuBotonesPanel, BorderLayout.NORTH); // Añadir panel de botones al main


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

        // ActionListener para el botón de chat
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout chatLayout = (CardLayout) contenedorPanel.getLayout();
                chatLayout.show(contenedorPanel, "chat");
            }
        });

        // ActionListener para el botón de Reportes
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout reportesLayout = (CardLayout) contenedorPanel.getLayout();
                reportesLayout.show(contenedorPanel, "reportes");
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

    public JPanel getMainPanel() {
        return main;
    }
}