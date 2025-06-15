package veterinaria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.net.URL;
import java.io.File;
import modelo.ConexionDB;

public class Veterinaria extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Intentar conectar a la base de datos
            Connection conexion = ConexionDB.conectar();

            if (conexion != null) {
                System.out.println("¡Conexión exitosa a la base de datos!");

                // Cargar el archivo FXML desde la ruta en src/main/java/vista/
                URL fxmlLocation = new File("src/main/java/vista/login.fxml").toURI().toURL();
                System.out.println("Ruta FXML: " + fxmlLocation);

                // Si la ruta es nula, lanza una excepción
                if (fxmlLocation == null) {
                    throw new RuntimeException("No se pudo encontrar el archivo login.fxml en /vista/");
                }

                // Cargar el FXML y establecer la escena
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Scene scene = new Scene(loader.load());
                primaryStage.setTitle("Sistema de Citas - Veterinaria");
                primaryStage.setScene(scene);
                primaryStage.show();

                // Cerrar la conexión después de cargar la interfaz
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error al cargar la interfaz: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
