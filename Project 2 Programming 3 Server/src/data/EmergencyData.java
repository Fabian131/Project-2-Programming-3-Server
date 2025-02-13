package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import domain.Emergency;

public class EmergencyData {

    // Método para obtener todas las emergencias
    public static LinkedList<Emergency> getAll() {
        LinkedList<Emergency> list = new LinkedList<>();

        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call getAllEmergency}";
            CallableStatement stmt = cn.prepareCall(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Emergency emergency = new Emergency();
                emergency.setId(rs.getInt(1));
                emergency.setUserId(rs.getString(2));
                emergency.setType(rs.getString(3));
                emergency.setLocation(rs.getString(4));
                emergency.setDescription(rs.getString(5));
                emergency.setStatus(rs.getString(6));
                emergency.setOperatorId(rs.getString(7));

                list.add(emergency);
            }

            rs.close();
            stmt.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Método para obtener estadísticas de emergencias por tipo
    public static Map<String, Integer> getTotalEmergenciesByType() {
        Map<String, Integer> stats = new HashMap<>();
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call getTotalEmergenciesByType}";
            CallableStatement stmt = cn.prepareCall(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                int count = rs.getInt("count");
                stats.put(type, count);
            }

            rs.close();
            stmt.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // Método para guardar una nueva emergencia
    public static void saveEmergency(Emergency emergency) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call saveEmergency(?,?,?,?,?,?)}";
            CallableStatement stmt = cn.prepareCall(query);
            stmt.setString(1, emergency.getUserId());
            stmt.setString(2, emergency.getType());
            stmt.setString(3, emergency.getLocation());
            stmt.setString(4, emergency.getDescription());
            stmt.setString(5, emergency.getStatus());
            stmt.setString(6, emergency.getOperatorId());

            stmt.execute();

            System.out.println("Emergencia guardada correctamente");

            stmt.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar el estado de una emergencia
    public static void updateEmergency(Emergency emergency) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call updateEmergency(?,?,?)}";
            CallableStatement stmt = cn.prepareCall(query);
            stmt.setString(1, emergency.getUserId());
            stmt.setString(2, emergency.getStatus());
            stmt.setString(3, emergency.getOperatorId());

            stmt.execute();

            System.out.println("Estado de la emergencia actualizado correctamente");

            stmt.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}