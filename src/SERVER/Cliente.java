package SERVER;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente {
    private static final String HOST = "127.0.0.1";
    private static final int PUERTO = 5000;

    private static DataInputStream in;
    private static DataOutputStream out;

    private static JTextArea chatArea;
    private static JTextField mensajeField;
    private static JButton enviarButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Cliente");
        JPanel panel = new JPanel();
        chatArea = new JTextArea(20, 40);
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);

        mensajeField = new JTextField(30);
        enviarButton = new JButton("Enviar");

        panel.add(scroll);
        panel.add(mensajeField);
        panel.add(enviarButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Conectar al servidor
        try {
            Socket socket = new Socket(HOST, PUERTO);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Obtener el nombre del cliente
            String nombre = JOptionPane.showInputDialog(frame, "Ingresa tu nombre:");
            out.writeUTF(nombre);

            // Hilo para recibir los mensajes del servidor
            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = in.readUTF()) != null) {
                        chatArea.append(mensaje + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Enviar mensaje al servidor
            enviarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String mensaje = mensajeField.getText();
                    if (!mensaje.isEmpty()) {
                        try {
                            out.writeUTF(mensaje); // Enviar mensaje al servidor
                            mensajeField.setText(""); // Limpiar el campo de texto
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}