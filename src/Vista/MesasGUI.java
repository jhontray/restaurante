package Vista;

import Controlador.MesasDao;
import Modelo.Mesas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MesasGUI extends JFrame {
    private JPanel main; // 1. Añadir el JPanel main
    private JTable tablaMesas;
    private DefaultTableModel modelo;
    private MesasDao dao;
    private int idSeleccionado = -1;
    private JPanel Principal;
    private JTable mesas;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JTextField tfNumMesa;
    private JTextField tfCapacidad;
    private JComboBox<String> cbEstado;

    public MesasGUI() {
        dao = new MesasDao();
        setTitle("Gestión de Mesas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        cargarMesas();
        // No necesitamos setVisible(true) aquí si se usa dentro de un CardLayout
    }

    private void initComponents() {
        // 2. Inicializar el JPanel main
        main = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2));

        panelFormulario.add(new JLabel("Número de Mesa:"));
        tfNumMesa = new JTextField();
        panelFormulario.add(tfNumMesa);

        panelFormulario.add(new JLabel("Capacidad:"));
        tfCapacidad = new JTextField();
        panelFormulario.add(tfCapacidad);

        panelFormulario.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"Disponible", "Ocupada", "Reservada"});
        panelFormulario.add(cbEstado);

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelFormulario.add(new JLabel("")); // Espacio en la grilla
        panelFormulario.add(panelBotones);

        modelo = new DefaultTableModel(new String[]{"ID", "Número", "Capacidad", "Estado"}, 0);
        tablaMesas = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaMesas);

        // 3. Añadir componentes al JPanel main
        main.add(panelFormulario, BorderLayout.NORTH);
        main.add(scroll, BorderLayout.CENTER);

        // Ya no añadimos directamente al JFrame
        // add(main);

        btnAgregar.addActionListener(e -> agregarMesa());
        btnActualizar.addActionListener(e -> actualizarMesa());
        btnEliminar.addActionListener(e -> eliminarMesa());

        tablaMesas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaMesas.getSelectedRow();
                if (fila != -1) {
                    idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                    tfNumMesa.setText(modelo.getValueAt(fila, 1).toString());
                    tfCapacidad.setText(modelo.getValueAt(fila, 2).toString());
                    cbEstado.setSelectedItem(modelo.getValueAt(fila, 3).toString());
                }
            }
        });
    }

    private void cargarMesas() {
        modelo.setRowCount(0);
        List<Mesas> lista = dao.obtenerMesas();
        for (Mesas m : lista) {
            modelo.addRow(new Object[]{m.getId(), m.getNumero(), m.getCapacidad(), m.getEstado()});
        }
    }

    private void agregarMesa() {
        try {
            int numero = Integer.parseInt(tfNumMesa.getText());
            int capacidad = Integer.parseInt(tfCapacidad.getText());
            String estado = cbEstado.getSelectedItem().toString();

            Mesas mesa = new Mesas(0, numero, capacidad, estado);
            if (dao.registrarMesa(mesa)) {
                cargarMesas();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Mesa registrada correctamente");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número o capacidad inválidos");
        }
    }

    private void actualizarMesa() {
        if (idSeleccionado != -1) {
            try {
                int numero = Integer.parseInt(tfNumMesa.getText());
                int capacidad = Integer.parseInt(tfCapacidad.getText());
                String estado = cbEstado.getSelectedItem().toString();

                Mesas mesa = new Mesas(idSeleccionado, numero, capacidad, estado);
                if (dao.actualizarMesa(mesa)) {
                    cargarMesas();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Mesa actualizada correctamente");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Número o capacidad inválidos");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa para actualizar");
        }
    }

    private void eliminarMesa() {
        if (idSeleccionado != -1) {
            if (dao.eliminarMesa(idSeleccionado)) {
                cargarMesas();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Mesa eliminada correctamente");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa para eliminar");
        }
    }

    private void limpiarCampos() {
        tfNumMesa.setText("");
        tfCapacidad.setText("");
        cbEstado.setSelectedIndex(0);
        idSeleccionado = -1;
    }

    // 4. Crear el método getMainPanel()
    public JPanel getMainPanel() {
        return main;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MesasGUI().setVisible(true));
    }
}