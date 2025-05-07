package Vista;

import Controlador.ReportesDao;
import Modelo.Reportes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportesGUI extends JFrame {
    private JPanel main;
    private JTable tablaReportes;
    private JTextField campoFecha1;
    private JTextField campoFecha2;
    private JButton btnBuscar;
    private JButton btnProductoMasVendido;
    private JButton btnClientesQueMasCompran;
    private DefaultTableModel modeloTabla;
    private final ReportesDao dao;

    public ReportesGUI() {
        dao = new ReportesDao();
        setTitle("Reportes del Restaurante");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        // Crear panel principal y layout
        main = new JPanel(new BorderLayout());

        // Panel superior con filtros
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());

        campoFecha1 = new JTextField(10);
        campoFecha2 = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnProductoMasVendido = new JButton("Producto más vendido");
        btnClientesQueMasCompran = new JButton("Clientes que más compran");

        // Formato de fecha actual
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaHoy = LocalDate.now().format(formatoFecha);
        campoFecha1.setText(fechaHoy);
        campoFecha2.setText(fechaHoy);

        panelSuperior.add(new JLabel("Fecha inicio:"));
        panelSuperior.add(campoFecha1);
        panelSuperior.add(new JLabel("Fecha fin:"));
        panelSuperior.add(campoFecha2);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnProductoMasVendido);
        panelSuperior.add(btnClientesQueMasCompran);

        // Tabla para reportes
        modeloTabla = new DefaultTableModel();
        tablaReportes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaReportes);

        // Agregar al panel principal
        main.add(panelSuperior, BorderLayout.NORTH);
        main.add(scrollPane, BorderLayout.CENTER);

        // Mostrar reporte diario por defecto
        mostrarReporteDiario();

        // Agregar main panel a la ventana
        add(main);
    }

    private void initListeners() {
        btnBuscar.addActionListener((ActionEvent e) -> mostrarReporteGeneral());
        btnProductoMasVendido.addActionListener((ActionEvent e) -> mostrarProductoMasVendido());
        btnClientesQueMasCompran.addActionListener((ActionEvent e) -> mostrarClientesQueMasCompran());
    }

    private void mostrarReporteDiario() {
        String fecha = obtenerFechaActual();
        List<Reportes> lista = dao.obtenerReporteDiario(fecha);
        cargarTablaOrdenes(lista);
    }

    private String obtenerFechaActual() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return fechaActual.format(formato);
    }

    private void mostrarReporteGeneral() {
        String inicio = campoFecha1.getText();
        String fin = campoFecha2.getText();

        if (inicio != null && !inicio.isEmpty() && fin != null && !fin.isEmpty()) {
            List<Reportes> lista = dao.obtenerReporteSemanal(inicio, fin);
            cargarTablaOrdenes(lista);
        } else {
            mostrarReporteDiario();
        }
    }

    private void mostrarProductoMasVendido() {
        List<Reportes> lista = dao.obtenerProductoMasVendido();
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Producto", "Cantidad"});
        for (Reportes prod : lista) {
            modeloTabla.addRow(new Object[]{prod.getNombre(), prod.getTotal()});
        }
    }

    private void mostrarClientesQueMasCompran() {
        List<Reportes> lista = dao.obtenerClientesQueMasCompran();
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Cliente", "Total Comprado"});
        for (Reportes cliente : lista) {
            modeloTabla.addRow(new Object[]{cliente.getNombre(), cliente.getTotal()});
        }
    }

    private void cargarTablaOrdenes(List<Reportes> lista) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"ID Orden", "Fecha", "Total"});
        for (Reportes rep : lista) {
            modeloTabla.addRow(new Object[]{rep.getIdOrden(), rep.getFecha(), rep.getTotal()});
        }
    }

    public JPanel getPanel() {
        return main;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReportesGUI ventana = new ReportesGUI();
            ventana.setVisible(true);
        });
    }
}


