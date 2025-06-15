package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;
import modelo.ConexionDB;

public class UsuarioDAO {

    public Usuario validarUsuario(String correo, String contraseña) {
        Usuario usuario = null;

        String sql = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contraseña);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setRol(rs.getString("rol"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
