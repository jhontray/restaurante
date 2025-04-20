package SERVER;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel main;
    private JButton INICIARSERVIDORButton;
    private JButton INICIARCHATCLIENTEButton;
    private JButton DESCONECTARSERVIDORButton;

    // Para controlar la instancia del servidor
    private Server server;

    public MainMenu() {
        // Acción del botón para iniciar el servidor
        INICIARSERVIDORButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Iniciar servidor en un hilo separado
                if (server == null) {
                    server = new Server();
                    new Thread(() -> server.startServer()).start();  // Inicia el servidor
                    JOptionPane.showMessageDialog(null, "Servidor iniciado.");
                } else {
                    JOptionPane.showMessageDialog(null, "El servidor ya está en ejecución.");
                }
            }
        });

        // Acción del botón para iniciar el chat cliente
        INICIARCHATCLIENTEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Chat Cliente");
                frame.setContentPane(new ChatGUI().getMainPanel());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 500);
                frame.setVisible(true);
            }
        });

        // Acción del botón para desconectar el servidor
        DESCONECTARSERVIDORButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server != null) {
                    server.stopServer();  // Detener el servidor
                    server = null;  // Limpiar la instancia
                    JOptionPane.showMessageDialog(null, "Servidor detenido.");
                } else {
                    JOptionPane.showMessageDialog(null, "El servidor no está en ejecución.");
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return main;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menú Principal");
        frame.setContentPane(new MainMenu().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
