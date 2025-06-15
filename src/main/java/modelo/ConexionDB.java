package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Cambia "localhost" y el puerto si tu SQL Server está en otro lado o puerto
    private static final String URL = "jdbc:sqlserver://DESKTOP-A7G5PI8:1433;databaseName=VeterinariaDB;encrypt=true;TrustServerCertificate=true";
    private static final String USUARIO = "alexx";       // Cambia por tu usuario SQL Server
    private static final String CONTRASENA = "123";  // Cambia por tu contraseña

    public static Connection conectar() {
        try {
            System.out.println("Intentando conectar a la base de datos...");
            System.out.println("URL: " + URL);
            System.out.println("Usuario: " + USUARIO);

            Connection conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

            System.out.println("Conexión exitosa.");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error en la conexión:");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Código de error: " + e.getErrorCode());
            e.printStackTrace(); // Para ver más detalles
            return null;
        }
    }
}
