package controlador;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import modelo.Usuario;

import java.io.File;
import java.net.URL;

public class PanelRecepcionistaController {

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        System.out.println("Usuario recepcionista: " + usuario.getNombre());
        // Cargar info o configurar la vista según usuario si quieres
    }

    @FXML
    private void handle_registrar_mascota() {
        try {
            URL fxmlLocation = new File("src/main/java/vista/registrar_mascota.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Registrar Mascota");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handle_reservar_cita() {
        try {
            URL fxmlLocation = new File("src/main/java/vista/registrar_cita.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Registrar Cita");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
