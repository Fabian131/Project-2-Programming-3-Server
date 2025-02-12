package business;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.EmergencyData;
import data.OperatorData;
import domain.Emergency;
import domain.Operator;

public class Server {
    private static final int PORT = 5000; // Puerto del servidor
    private ExecutorService threadPool; // Pool de hilos para manejar múltiples conexiones
    private Map<String, PrintWriter> clientWriters; // Mapa para mantener las conexiones de los clientes

    public Server() {
        this.threadPool = Executors.newCachedThreadPool();
        this.clientWriters = new HashMap<>();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado en el puerto: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Aceptar conexiones entrantes
                System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.execute(clientHandler); // Asignar un hilo al cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                String clientMessage;
                while ((clientMessage = reader.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + clientMessage);
                    processMessage(clientMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processMessage(String message) {
            if (message.startsWith("LOGIN:")) {
                handleLogin(message);
            } else if (message.startsWith("REGISTER:")) {
                handleRegistration(message);
            } else if (message.startsWith("EMERGENCY:")) {
                handleEmergencyReport(message);
            } else if (message.startsWith("UPDATE_STATUS:")) {
                handleStatusUpdate(message);
            } else if (message.startsWith("STATISTICS_REQUEST:")) {
                handleStatisticsRequest(message);
            }
        }

        private void handleLogin(String message) {
            String[] parts = message.split(":");
            String identification = parts[1];
            String password = parts[2];

            boolean isValidUser = OperatorData.validateLoginOperator(identification, password);
            if (isValidUser) {
                writer.println("LOGIN_RESPONSE:SUCCESS");
                clientWriters.put(identification, writer); // Registrar el cliente conectado
            } else {
                writer.println("LOGIN_RESPONSE:WRONG_PASSWORD");
            }
        }

        private void handleRegistration(String message) {
            String[] parts = message.split(":");
            String identification = parts[1];
            String surnames = parts[2];
            String name = parts[3];
            String password = parts[4];
            String imagePath = parts[5];

            if (!OperatorData.checkIdentification(identification)) {
                Operator operator = new Operator(identification, name, surnames, password);
                OperatorData.saveOperator(operator);
                writer.println("REGISTRATION_RESPONSE:SUCCESS");
            } else {
                writer.println("REGISTRATION_RESPONSE:ID_EXISTS");
            }
        }

        private void handleEmergencyReport(String message) {
            String[] parts = message.split(":");
            String userId = parts[1];
            String type = parts[2];
            String location = parts[3];
            String description = parts[4];

            Emergency emergency = new Emergency(userId, type, location, description);
            EmergencyData.saveEmergency(emergency);

            // Buscar un operador disponible
            Operator availableOperator = findAvailableOperator();
            if (availableOperator != null) {
                availableOperator.setAvailable(false); // Marcar como ocupado
                broadcastToOperators("ASSIGNED_EMERGENCY:" + availableOperator.getIdentification() + ":" + type + ":" + location + ":" + description);
            } else {
                broadcastToOperators("UNASSIGNED_EMERGENCY:" + type + ":" + location + ":" + description);
            }
        }

        private Operator findAvailableOperator() {
            for (Operator operator : OperatorData.getAll()) {
                if (operator.isAvailable()) {
                    return operator;
                }
            }
            return null; // No hay operadores disponibles
        }

        private void handleStatusUpdate(String message) {
            String[] parts = message.split(":");
            String emergencyId = parts[1];
            String newStatus = parts[2];
            long duration = System.currentTimeMillis() - getEmergencyStartTime(emergencyId);

            String color = "Verde"; // Por defecto
            if (newStatus.equals("Pendiente")) {
                if (duration > 5 * 60 * 1000) { // Más de 5 minutos
                    color = "Rojo";
                } else if (duration > 3 * 60 * 1000) { // Más de 3 minutos
                    color = "Amarillo";
                }
            } else if (newStatus.equals("En Proceso")) {
                if (duration > 5 * 60 * 1000) { // Más de 5 minutos
                    color = "Rojo";
                } else if (duration > 3 * 60 * 1000) { // Más de 3 minutos
                    color = "Amarillo";
                } else {
                    color = "Celeste";
                }
            }

            // Notificar al cliente sobre el cambio de estado
            broadcastToClients("STATUS_UPDATE:" + emergencyId + ":" + newStatus + ":" + color);
        }

        private long getEmergencyStartTime(String emergencyId) {
            // TODO: Obtener el tiempo de inicio de la emergencia desde la base de datos
            return System.currentTimeMillis() - 180000; // Simulación: hace 3 minutos
        }

        private void handleStatisticsRequest(String message) {
            String[] parts = message.split(":");
            String requestType = parts[1];

            if (requestType.equals("TOTAL_BY_TYPE")) {
                Map<String, Integer> stats = EmergencyData.getTotalEmergenciesByType();
                broadcastToClients("STATISTICS:TOTAL_BY_TYPE:" + stats.toString());
            } else if (requestType.equals("ATTENDED_BY_OPERATOR")) {
                Map<String, Integer> stats = OperatorData.getEmergenciesAttendedByOperator();
                broadcastToClients("STATISTICS:ATTENDED_BY_OPERATOR:" + stats.toString());
            }
        }

        private void broadcastToClients(String message) {
            for (PrintWriter writer : clientWriters.values()) {
                writer.println(message);
            }
        }

        private void broadcastToOperators(String message) {
            // Filtrar solo a los operadores disponibles
            for (Operator operator : OperatorData.getAll()) {
                if (operator.isAvailable()) {
                    PrintWriter operatorWriter = clientWriters.get(operator.getIdentification());
                    if (operatorWriter != null) {
                        operatorWriter.println(message);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}