package dao;

import modelo.DetalleHistorial;
import modelo.ConexionDB;

import java.sql.*;

public class DetalleHistorialDAO {

    // Método para insertar un detalle en el historial
    public boolean insertarDetalle(DetalleHistorial detalle) {
        String sql = "INSERT INTO Detalle_Historial (id_historial, fecha_visita, sintomas, diagnostico, tratamiento, observaciones, id_veterinario) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";  // Cambié usuario_creador por id_veterinario

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdHistorial());
            ps.setDate(2, new java.sql.Date(detalle.getFechaVisita().getTime()));
            ps.setString(3, detalle.getSintomas());
            ps.setString(4, detalle.getDiagnostico());
            ps.setString(5, detalle.getTratamiento());
            ps.setString(6, detalle.getObservaciones());

            // Aquí debes pasar el id_veterinario, no el id de Usuario
            ps.setInt(7, detalle.getIdVeterinario());  // Cambié usuario_creador por id_veterinario

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar el detalle del historial: " + e.getMessage());
        }

        return false;
    }
}
