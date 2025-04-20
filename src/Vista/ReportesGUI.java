package Vista;

import Controlador.ReportesDao;
import Modelo.Reportes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportesGUI extends JFrame {

    private JPanel main; // Agrega el JPanel main
    private JComboBox<String> tipoReporte;
    private JTextField campoFecha1, campoFecha2;
    private JButton btnBuscar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ReportesDao dao;
    private JTable table1;

    public ReportesGUI() {
        setTitle("Reportes de Ventas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dao = new ReportesDao();
        initComponents();
    }

    private void initComponents() {
        // Inicializa el JPanel main con un BorderLayout
        main = new JPanel(new BorderLayout());

        tipoReporte = new JComboBox<>(new String[]{"Diario", "Semanal", "Mensual"});
        campoFecha1 = new JTextField(10);  // Fecha o Mes o Inicio
        campoFecha2 = new JTextField(10);  // Fin (solo para semanal)
        btnBuscar = new JButton("Buscar");

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Tipo:"));
        panelSuperior.add(tipoReporte);
        panelSuperior.add(new JLabel("Fecha 1:"));
        panelSuperior.add(campoFecha1);
        panelSuperior.add(new JLabel("Fecha 2:"));
        panelSuperior.add(campoFecha2);
        panelSuperior.add(btnBuscar);

        modelo = new DefaultTableModel(new String[]{"ID Orden", "Fecha", "Total"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPaneTabla = new JScrollPane(tabla);

        // Agrega los paneles al JPanel main
        main.add(panelSuperior, BorderLayout.NORTH);
        main.add(scrollPaneTabla, BorderLayout.CENTER);

        // Ya no agregas directamente al JFrame
        // add(main);

        btnBuscar.addActionListener(e -> cargarReporte());
    }

    private void cargarReporte() {
        modelo.setRowCount(0);
        String tipo = tipoReporte.getSelectedItem().toString();
        String fecha1 = campoFecha1.getText();
        String fecha2 = campoFecha2.getText();
        List<Reportes> lista;

        if (tipo.equals("Diario")) {
            lista = dao.obtenerReporteDiario(fecha1);
        } else if (tipo.equals("Semanal")) {
            lista = dao.obtenerReporteSemanal(fecha1, fecha2);
        } else {
            lista = dao.obtenerReporteMensual(fecha1);
        }

        for (Reportes r : lista) {
            modelo.addRow(new Object[]{r.getIdOrden(), r.getFecha(), r.getTotal()});
        }
    }

    // Método para obtener el JPanel main
    public JPanel getMainPanel() {
        return main;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportesGUI().setVisible(true));
    }
}
