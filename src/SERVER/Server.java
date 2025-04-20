package SERVER;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private boolean running = true;

    public void startServer() {
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Servidor iniciado en el puerto 5000...");

            while (running) {  // Solo acepta conexiones si running es true
                Socket socket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado.");

                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            if (!serverSocket.isClosed()) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        try {
            running = false;  // Cambiar running a false para detener el bucle de aceptación
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();  // Cerrar el ServerSocket
                System.out.println("Servidor detenido.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {  // No enviar el mensaje al cliente que lo envió
                client.sendMessage(message);
            }
        }
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    // Clase interna ClientHandler
    private class ClientHandler implements Runnable {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String msg;
                while ((msg = in.readUTF()) != null) {
                    System.out.println("Mensaje recibido: " + msg);
                    broadcast(msg, this);  // Llamamos a broadcast y pasamos el emisor para evitar que el mensaje se reenvíe al cliente que lo envió
                }
            } catch (IOException e) {
                System.out.println("Cliente desconectado.");
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (socket != null) socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                removeClient(this);
            }
        }

        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
