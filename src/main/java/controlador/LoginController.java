package controlador;

import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;

import java.io.File;
import java.net.URL;

public class LoginController {

    @FXML
    private TextField campoCorreo;

    @FXML
    private PasswordField campoContrasena;

    @FXML
    private Label labelError;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

@FXML
private void iniciarSesion(ActionEvent event) {
    String correo = campoCorreo.getText().trim();
    String contraseña = campoContrasena.getText().trim();

    System.out.println("Correo ingresado: " + correo);  // Depuración del correo
    System.out.println("Contraseña ingresada: " + contraseña);  // Depuración de la contraseña

    if (correo.isEmpty() || contraseña.isEmpty()) {
        labelError.setText("Por favor, complete todos los campos.");
        return;
    }

    Usuario usuario = usuarioDAO.validarUsuario(correo, contraseña);

    if (usuario != null) {
        labelError.setText("");
        System.out.println("Usuario encontrado: " + usuario.getCorreo()); // Depuración del usuario

        try {
            String rutaFxml;

            switch (usuario.getRol().toLowerCase()) {
                case "administrador":
                    rutaFxml = "src/main/java/vista/panel_administrador.fxml";
                    break;
                case "veterinario":
                    rutaFxml = "src/main/java/vista/panel_veterinario.fxml";
                    break;
                case "recepcionista":
                    rutaFxml = "src/main/java/vista/panel_recepcionista.fxml";
                    break;
                default:
                    labelError.setText("Rol no reconocido.");
                    return;
            }

            System.out.println("Ruta FXML seleccionada: " + rutaFxml); // Depuración de la ruta FXML

            Stage stage = (Stage) campoCorreo.getScene().getWindow();
            URL fxmlLocation = new File(rutaFxml).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());

            // Obtener el controlador y pasar el usuario
            Object controlador = loader.getController();
            System.out.println("Controlador cargado: " + controlador.getClass().getName());  // Depuración del controlador cargado

            if (controlador instanceof PanelAdministradorController) {
                ((PanelAdministradorController) controlador).setUsuario(usuario);
            } else if (controlador instanceof PanelVeterinarioController) {
                ((PanelVeterinarioController) controlador).setUsuario(usuario);
            } else if (controlador instanceof PanelRecepcionistaController) {
                ((PanelRecepcionistaController) controlador).setUsuario(usuario);
            }

            stage.setScene(scene);
            stage.setTitle("Sistema Veterinaria - " + usuario.getRol());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            labelError.setText("Error al cargar el panel de " + usuario.getRol());
            System.out.println("Error al cargar el FXML: " + e.getMessage());  // Depuración del error
        }

    } else {
        labelError.setText("Correo o contraseña incorrectos.");
        System.out.println("Usuario no encontrado para el correo: " + correo);  // Depuración si el usuario no es encontrado
    }
}
}
