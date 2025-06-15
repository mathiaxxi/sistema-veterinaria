package dao;

import modelo.HistorialMedico;
import modelo.DetalleHistorial;
import modelo.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAO {

    // Método para obtener un historial médico por ID de mascota
    public HistorialMedico obtenerHistorialPorMascota(int idMascota) {
        String sql = "SELECT * FROM Historial_Medico WHERE id_mascota = ?";
        HistorialMedico historial = null;

        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMascota);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                Date fechaCreacion = rs.getDate("fecha_creacion");

                historial = new HistorialMedico(id, idMascota, fechaCreacion);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el historial médico: " + e.getMessage());
        }

        return historial;
    }

    // Método para obtener los detalles del historial médico de una mascota
    public List<DetalleHistorial> obtenerDetallesHistorial(int idMascota) {
        List<DetalleHistorial> detalles = new ArrayList<>();
        String sql = "SELECT d.id, d.id_historial, d.fecha_visita, d.sintomas, d.diagnostico, d.tratamiento, " +
                     "d.observaciones, d.id_veterinario, d.fecha_creacion " +
                     "FROM Detalle_Historial d " +
                     "INNER JOIN Historial_Medico h ON d.id_historial = h.id " +
                     "WHERE h.id_mascota = ?";

        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idMascota);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetalleHistorial detalle = new DetalleHistorial(
                    rs.getInt("id"),
                    rs.getInt("id_historial"),
                    rs.getDate("fecha_visita"),
                    rs.getString("sintomas"),
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getString("observaciones"),
                    rs.getInt("id_veterinario"),
                    rs.getDate("fecha_creacion")
                );
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los detalles del historial: " + e.getMessage());
        }
        
        return detalles;
    }

    // Método para insertar un nuevo historial médico
    public boolean insertarHistorialMedico(HistorialMedico historial) {
        String sql = "INSERT INTO Historial_Medico (id_mascota, fecha_creacion) VALUES (?, ?)";

        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, historial.getIdMascota());
            ps.setDate(2, new java.sql.Date(historial.getFechaCreacion().getTime()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La inserción del historial médico falló, no se generó ningún ID.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    historial.setId(generatedKeys.getInt(1));  // Asignar el ID generado al objeto
                } else {
                    throw new SQLException("No se obtuvo el ID generado para el historial médico.");
                }
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar el historial médico: " + e.getMessage());
        }

        return false;
    }
}
