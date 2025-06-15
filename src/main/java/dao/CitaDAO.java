package dao;

import modelo.Mascota;
import modelo.Usuario;
import modelo.Servicio;
import modelo.Cita;
import modelo.ConexionDB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class CitaDAO {

    // Método para insertar una nueva cita
    public boolean insertar(Cita cita) {
        String sql = "INSERT INTO Cita (id_cliente, id_veterinario, id_mascota, id_servicio, fecha_hora, motivo, estado, fechaCreacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdCliente());
            ps.setInt(2, cita.getIdVeterinario());  // Asegúrate de usar el ID del veterinario
            ps.setInt(3, cita.getIdMascota());
            ps.setInt(4, cita.getIdServicio());
            ps.setTimestamp(5, cita.getFechaHora());
            ps.setString(6, cita.getMotivo());
            ps.setString(7, cita.getEstado());
            ps.setTimestamp(8, cita.getFechaCreacion());

            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener los servicios disponibles
    public List<Servicio> obtenerServicios() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM Servicio";

        try (Connection con = ConexionDB.conectar(); 
             Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                BigDecimal precio = rs.getBigDecimal("precio");

                servicios.add(new Servicio(id, nombre, descripcion, precio));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicios;
    }

    // Método para obtener las horas ocupadas por un veterinario en una fecha específica
    public Set<String> obtenerHorasOcupadas(int idVeterinario, Timestamp fecha) {
        Set<String> horasOcupadas = new HashSet<>();
        String sql = "SELECT fecha_hora FROM Cita WHERE id_veterinario = ? AND CONVERT(DATE, fecha_hora) = ?";

        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVeterinario);
            java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
            ps.setDate(2, sqlDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp fechaHora = rs.getTimestamp("fecha_hora");
                    String hora = new java.text.SimpleDateFormat("HH:mm").format(fechaHora);
                    horasOcupadas.add(hora);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horasOcupadas;
    }

    // Método para obtener los veterinarios
    public Map<String, Usuario> obtenerVeterinarios() {
        Map<String, Usuario> veterinarios = new HashMap<>();
        String sql = "SELECT v.id, u.nombre FROM Usuario u " +
                     "JOIN Veterinario v ON u.id = v.id_usuario";

        try (Connection con = ConexionDB.conectar(); 
             Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");  // El id del veterinario
                String nombre = rs.getString("nombre");
                // Crear un nuevo Usuario solo con id y nombre
                veterinarios.put(nombre, new Usuario(id, nombre, "", "", "", ""));  // Campos vacíos o nulos para los campos no utilizados
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veterinarios;
    }

    // Método para obtener las mascotas por DNI del cliente
    public Map<String, Mascota> obtenerMascotasPorDni(String dni) {
        Map<String, Mascota> mascotas = new HashMap<>();
        String sql = "SELECT m.id, m.nombre, m.id_cliente, m.especie, m.raza, m.edad, m.sexo, m.peso " +
                     "FROM Mascota m " +
                     "JOIN Cliente c ON m.id_cliente = c.id " +
                     "WHERE c.dni = ?";

        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int idCliente = rs.getInt("id_cliente");
                    String especie = rs.getString("especie");
                    String raza = rs.getString("raza");
                    int edad = rs.getInt("edad");
                    String sexo = rs.getString("sexo");
                    BigDecimal peso = rs.getBigDecimal("peso");

                    Mascota mascota = new Mascota(id, idCliente, nombre, especie, raza, edad, sexo, peso);
                    mascotas.put(nombre, mascota);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mascotas;
    }

    // Nuevo método para obtener veterinarios según el servicio seleccionado
    public Map<String, Usuario> obtenerVeterinariosPorServicio(int idServicio) {
        Map<String, Usuario> veterinarios = new HashMap<>();
        String sql = "SELECT v.id, u.nombre FROM Usuario u " +
                     "JOIN Veterinario v ON u.id = v.id_usuario " +
                     "JOIN Veterinario_Servicio vs ON v.id = vs.id_veterinario " +
                     "WHERE vs.id_servicio = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idServicio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    veterinarios.put(nombre, new Usuario(id, nombre, "", "", "", ""));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veterinarios;
    }
    public Cita obtenerCitaPorId(int idCita) {
    String sql = "SELECT * FROM Cita WHERE id = ?";
    Cita cita = null;

    try (Connection con = ConexionDB.conectar(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idCita);  // Establecer el ID de la cita en la consulta
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Obtener los datos de la cita de la base de datos
            int idCliente = rs.getInt("id_cliente");
            int idVeterinario = rs.getInt("id_veterinario");
            int idMascota = rs.getInt("id_mascota"); // Recuperamos el id de la mascota
            System.out.println("ID de la mascota recuperado de la base de datos: " + idMascota); // Imprime el ID de la mascota
            int idServicio = rs.getInt("id_servicio");
            Timestamp fechaHora = rs.getTimestamp("fecha_hora");
            String motivo = rs.getString("motivo");
            String estado = rs.getString("estado");
            Timestamp fechaCreacion = rs.getTimestamp("fechaCreacion");

            // Crear la instancia de la cita con los datos obtenidos
            cita = new Cita(idCliente, idVeterinario, idMascota, idServicio, fechaHora, motivo, estado, fechaCreacion);
            cita.setId(rs.getInt("id")); // Establecer el ID de la cita
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return cita;
}


}
