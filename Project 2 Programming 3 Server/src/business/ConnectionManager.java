package business;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConnectionManager {
    private static ConnectionManager instance;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private boolean isConnected;
    private Thread listenerThread;
    private List<Consumer<String>> messageHandlers;

    private ConnectionManager() {
        this.messageHandlers = new ArrayList<>();
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            isConnected = true;
            startListening();
        } catch (IOException e) {
            isConnected = false;
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        if (isConnected && writer != null) {
            writer.println(message);
        } else {
            System.out.println("No se puede enviar el mensaje. No hay conexión con el servidor.");
        }
    }

    private void startListening() {
        listenerThread = new Thread(() -> {
            try {
                String serverMessage;
                while (isConnected && (serverMessage = reader.readLine()) != null) {
                    System.out.println("Mensaje recibido del servidor: " + serverMessage);
                    notifyMessageHandlers(serverMessage);
                }
            } catch (IOException e) {
                System.out.println("Error al recibir mensajes del servidor: " + e.getMessage());
            } finally {
                disconnect();
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void addMessageHandler(Consumer<String> handler) {
        messageHandlers.add(handler);
    }

    private void notifyMessageHandlers(String message) {
        for (Consumer<String> handler : messageHandlers) {
            handler.accept(message);
        }
    }

    public void disconnect() {
        isConnected = false;
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}