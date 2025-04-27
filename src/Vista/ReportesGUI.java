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
    //private JTextField txtMes;
    private JButton btnBuscar;
    //private JButton btnSemanal;
    //private JButton btnMensual;
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
        // Formato de fecha
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaHoy = LocalDate.now().format(formatoFecha);

        // Inicializar fechas por defecto (ejemplo: ambos campos con la fecha de hoy)
        campoFecha1.setText(fechaHoy);
        campoFecha2.setText(fechaHoy);

        // Configurar modelo de tabla
        modeloTabla = new DefaultTableModel();
        tablaReportes.setModel(modeloTabla);
        mostrarReporteDiario();

        add(main);

    }

    private void initListeners() {
        //mostrarReporteGeneral
        btnBuscar.addActionListener((ActionEvent e) -> mostrarReporteGeneral());
        btnProductoMasVendido.addActionListener((ActionEvent e) -> mostrarProductoMasVendido());
        btnClientesQueMasCompran.addActionListener((ActionEvent e) -> mostrarClientesQueMasCompran());
    }

    private void mostrarReporteDiario() {
        String fecha =  obtenerFechaActual();
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

        if(inicio!=null && !inicio.isEmpty() && fin!=null && !fin.isEmpty()){
            List<Reportes> lista = dao.obtenerReporteSemanal(inicio, fin);
            cargarTablaOrdenes(lista);
        }else {
            mostrarReporteDiario();
        }

    }

    /*private void mostrarReporteMensual() {
        String mes = txtMes.getText();
        List<Reportes> lista = dao.obtenerReporteMensual(mes);
        cargarTablaOrdenes(lista);
    }*/

    private void mostrarProductoMasVendido() {
        var lista = dao.obtenerProductoMasVendido();
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Producto", "Cantidad"});
        for (var prod : lista) {
            modeloTabla.addRow(new Object[]{prod.getIdOrden(), prod.getTotal()});
        }
    }

    private void mostrarClientesQueMasCompran() {
        var lista = dao.obtenerClientesQueMasCompran();
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Cliente", "Total Comprado"});
        for (var cliente : lista) {
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
    public JPanel getPanel(){
        return main;
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("ReportesGUI"); // Crear ventana con título
        frame.setContentPane(new ReportesGUI().main); // Establecer panel principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar app al cerrar ventana
        frame.pack(); // Ajustar tamaño automáticamente
        frame.setVisible(true); // Mostrar ventana
    }

}



