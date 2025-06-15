package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanelVeterinarioDAO {

    // Método para obtener el id_veterinario a partir del id_usuario
    private int obtenerIdVeterinarioPorUsuario(int idUsuario) {
        String query = "SELECT id FROM Veterinario WHERE id_usuario = ?";
        int idVeterinario = -1;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idVeterinario = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el id del veterinario: " + e.getMessage());
            e.printStackTrace();
        }

        return idVeterinario;
    }

    // Método para obtener las citas del día para un veterinario
    public List<Cita> obtenerCitasDelDia(int idUsuario) {
    List<Cita> citas = new ArrayList<>();

    int idVeterinario = obtenerIdVeterinarioPorUsuario(idUsuario);

    if (idVeterinario == -1) {
        System.out.println("No se encontró el veterinario para el usuario con ID: " + idUsuario);
        return citas;
    }

    System.out.println("Buscando citas para el veterinario con ID: " + idVeterinario);

    // Consulta corregida para SQL Server
    String query = "SELECT * FROM cita WHERE estado = 'Pendiente' AND CAST(fecha_hora AS DATE) = CAST(GETDATE() AS DATE) " +
                   "AND id_servicio = 1 AND id_veterinario = ?";

    System.out.println("Ejecutando consulta: " + query);

    try (Connection conn = ConexionDB.conectar();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, idVeterinario);
        ResultSet rs = stmt.executeQuery();

        System.out.println("Conexión a la base de datos establecida correctamente.");

        while (rs.next()) {
            Cita cita = new Cita();
            cita.setId(rs.getInt("id"));
            cita.setIdCliente(rs.getInt("id_cliente"));
            cita.setIdVeterinario(rs.getInt("id_veterinario"));
            cita.setIdMascota(rs.getInt("id_mascota"));
            cita.setIdServicio(rs.getInt("id_servicio"));
            cita.setFechaHora(rs.getTimestamp("fecha_hora"));
            cita.setMotivo(rs.getString("motivo"));
            cita.setEstado(rs.getString("estado"));
            cita.setFechaCreacion(rs.getTimestamp("fechaCreacion"));

            System.out.println("Cita encontrada: ID = " + cita.getId() +
                               ", Veterinario = " + cita.getIdVeterinario() +
                               ", Mascota = " + cita.getIdMascota() +
                               ", Fecha = " + cita.getFechaHora() +
                               ", Motivo = " + cita.getMotivo());

            citas.add(cita);
        }
    } catch (SQLException e) {
        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        e.printStackTrace();
    }

    if (citas.isEmpty()) {
        System.out.println("No se encontraron citas para el veterinario con ID: " + idVeterinario);
    }

    return citas;
}

    // Método para obtener una cita por su ID
    public Cita obtenerCitaPorId(int id) {
        Cita cita = null;
        String query = "SELECT * FROM cita WHERE id = ?";

        System.out.println("Ejecutando consulta para obtener cita con ID = " + id);

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setIdCliente(rs.getInt("id_cliente"));
                cita.setIdVeterinario(rs.getInt("id_veterinario"));
                cita.setIdMascota(rs.getInt("id_mascota"));
                cita.setIdServicio(rs.getInt("id_servicio"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora"));
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado(rs.getString("estado"));
                cita.setFechaCreacion(rs.getTimestamp("fechaCreacion"));

                System.out.println("Cita encontrada: ID = " + cita.getId() +
                                   ", Veterinario = " + cita.getIdVeterinario() +
                                   ", Mascota = " + cita.getIdMascota() +
                                   ", Fecha = " + cita.getFechaHora() +
                                   ", Motivo = " + cita.getMotivo());
            } else {
                System.out.println("No se encontró una cita con ID = " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }
        return cita;
    }
}
