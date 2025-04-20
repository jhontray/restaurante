package SERVER;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatGUI {
    private JPanel main;
    private JTextArea chatArea;
    private JTextField nombre;
    private JTextField mensaje;
    private JButton ENVIARButton;
    private JButton CONEXION; // Este botón  actua como Conectar/Desconectar
    private boolean conectado = false;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ChatGUI() {
        // Creación del panel principal
        main = new JPanel();
        main.setLayout(new BorderLayout());

        // Panel superior para el nombre de usuario y botón de conexión/desconexión
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("Tu Nombre:"));
        nombre = new JTextField(15);
        topPanel.add(nombre);

        CONEXION = new JButton("Conectar"); // Texto inicial del botón
        CONEXION.setEnabled(false); // Deshabilitar al inicio
        topPanel.add(CONEXION);

        main.add(topPanel, BorderLayout.NORTH);

        // Panel central para mostrar los mensajes
        chatArea = new JTextArea(20, 40);
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);
        main.add(scroll, BorderLayout.CENTER);

        // Panel inferior para enviar mensajes
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        mensaje = new JTextField(20);
        mensaje.setEnabled(false); // Deshabilitar al inicio

        ENVIARButton = new JButton("Enviar"); // Este botón siempre enviará mensajes
        ENVIARButton.setEnabled(false); // Deshabilitar al inicio
        bottomPanel.add(new JLabel("Mensaje:"));
        bottomPanel.add(mensaje);
        bottomPanel.add(ENVIARButton);

        main.add(bottomPanel, BorderLayout.SOUTH);

        // Listener para habilitar el botón "Conectar" cuando se escribe el nombre
        nombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                CONEXION.setEnabled(!nombre.getText().isEmpty());
            }
        });

        // Acción del botón Conectar/Desconectar
        CONEXION.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = nombre.getText();

                if (!conectado) {
                    // Intentar conectar
                    if (!user.isEmpty()) {
                        try {
                            socket = new Socket("127.0.0.1", 5000);
                            in = new DataInputStream(socket.getInputStream());
                            out = new DataOutputStream(socket.getOutputStream());

                            out.writeUTF(user); // Enviar el nombre al servidor

                            // Hilo para recibir mensajes
                            Thread recibir = new Thread(() -> {
                                try {
                                    String msg;
                                    while ((msg = in.readUTF()) != null) {
                                        chatArea.append(msg + "\n");
                                    }
                                } catch (IOException ex) {
                                    chatArea.append("Conexión cerrada.\n");
                                    habilitarDeshabilitarCampos(false);
                                    CONEXION.setText("Conectar"); // Resetear texto
                                }
                            });
                            recibir.start();

                            conectado = true;
                            CONEXION.setText("Desconectar"); // Cambiar texto
                            mensaje.setEnabled(true); // Habilitar el campo de mensaje
                            nombre.setEnabled(false); // Deshabilitar el campo de nombre
                            ENVIARButton.setEnabled(true); // Habilitar el botón de enviar mensajes
                            chatArea.append("Te has conectado al chat como: " + user + "\n");
                        } catch (IOException ex) {
                            chatArea.append("No se pudo conectar al servidor.\n");
                            habilitarDeshabilitarCampos(false);
                            CONEXION.setEnabled(true); // Re-habilitar para intentar de nuevo
                        }
                    } else {
                        JOptionPane.showMessageDialog(main, "Por favor, ingresa tu nombre para conectar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    // Desconectar
                    closeConnection();
                    chatArea.append("Te has desconectado del servidor.\n");
                    habilitarDeshabilitarCampos(false);
                    CONEXION.setText("Conectar"); // Resetear texto
                    ENVIARButton.setEnabled(false); // Deshabilitar el botón de enviar
                    nombre.setEnabled(true); // Habilitar nombre para reconectar
                }
            }
        });

        // Acción del botón Enviar
        ENVIARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = mensaje.getText();
                String user = nombre.getText();

                if (conectado && !texto.isEmpty()) {
                    String mensajeAEnviar = user + ": " + texto;
                    try {
                        out.writeUTF(mensajeAEnviar);
                        chatArea.append("Yo: " + texto + "\n");
                        mensaje.setText("");
                    } catch (IOException ex) {
                        chatArea.append("Error al enviar mensaje.\n");
                        habilitarDeshabilitarCampos(false);
                    }
                }
            }
        });

        // Crear la ventana con el panel principal
        JFrame frame = new JFrame("Chat Cliente");
        frame.setContentPane(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);

        // Deshabilitar inicialmente el campo de mensaje y el botón de enviar
        mensaje.setEnabled(false);
        ENVIARButton.setEnabled(false);
    }

    // Método para habilitar/deshabilitar el campo de mensaje y el botón enviar
    private void habilitarDeshabilitarCampos(boolean habilitar) {
        mensaje.setEnabled(habilitar && conectado);
        ENVIARButton.setEnabled(habilitar && conectado && !mensaje.getText().isEmpty());
    }

    // Método para cerrar la conexión
    private void closeConnection() {
        try {
            conectado = false;
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método que devuelve el panel principal
    public JPanel getMainPanel() {
        return main;
    }

    // Método principal para ejecutar la GUI (esto se usa si se ejecuta ChatGUI directamente)
    public static void main(String[] args) {
        new ChatGUI();
    }
}
