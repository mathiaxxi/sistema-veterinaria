package dao;

import modelo.Mascota;
import modelo.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MascotaDAO {

    public boolean insertar(Mascota mascota) {
        String sql = "INSERT INTO Mascota(id_cliente, nombre, especie, raza, edad, sexo, peso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mascota.getIdCliente());
            ps.setString(2, mascota.getNombre());
            ps.setString(3, mascota.getEspecie());
            ps.setString(4, mascota.getRaza());
            ps.setInt(5, mascota.getEdad());
            ps.setString(6, mascota.getSexo());
            ps.setBigDecimal(7, mascota.getPeso());

            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Mascota obtenerMascotaPorId(int idMascota) {
        Mascota mascota = null;
        String query = "SELECT * FROM mascota WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idMascota);
                ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mascota = new Mascota();
                mascota.setId(rs.getInt("id"));
                mascota.setIdCliente(rs.getInt("id_cliente"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setRaza(rs.getString("raza"));
                mascota.setEdad(rs.getInt("edad"));
                mascota.setSexo(rs.getString("sexo"));
                mascota.setPeso(rs.getBigDecimal("peso"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener mascota: " + e.getMessage());
            e.printStackTrace();
        }
        return mascota;
    }
    
}
