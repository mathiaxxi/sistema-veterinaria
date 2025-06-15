package controlador;

import dao.MascotaDAO;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Cita;
import modelo.Mascota;
import modelo.PanelVeterinarioDAO;

public class DetalleCitaController {

    // Labels del FXML
    @FXML private Label labelNombre;
    @FXML private Label labelEspecie;
    @FXML private Label labelRaza;
    @FXML private Label labelEdad;
    @FXML private Label labelSexo;
    @FXML private Label labelPeso;

    // DAOs
    private PanelVeterinarioDAO citaDAO = new PanelVeterinarioDAO();
    private MascotaDAO mascotaDAO = new MascotaDAO();

    // Variables para guardar IDs
    private int citaId;
    private int idMascota; // NUEVO: para usar en historial médico

    // Método principal llamado desde PanelVeterinarioController
    public void cargarDetalleCita(int citaId) {
        this.citaId = citaId;
        Cita cita = citaDAO.obtenerCitaPorId(citaId);

        if (cita != null) {
            Mascota mascota = mascotaDAO.obtenerMascotaPorId(cita.getIdMascota());

            if (mascota != null) {
                this.idMascota = mascota.getId(); // ← Necesario para historial médico

                labelNombre.setText("Nombre: " + mascota.getNombre());
                labelEspecie.setText("Especie: " + mascota.getEspecie());
                labelRaza.setText("Raza: " + mascota.getRaza());
                labelEdad.setText("Edad: " + mascota.getEdad());
                labelSexo.setText("Sexo: " + mascota.getSexo());
                labelPeso.setText("Peso: " + mascota.getPeso() + " kg");

                System.out.println("Mascota cargada: ID = " + idMascota);
            } else {
                labelNombre.setText("Mascota no encontrada");
            }
        } else {
            labelNombre.setText("Cita no encontrada");
        }
    }

    @FXML
    private void handle_HistorialMedico() {
        try {
            File fxmlFile = new File("src/main/java/vista/historial_medico.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();

            // Obtener el controlador y pasarle el id de la mascota
            HistorialMedicoController historialController = loader.getController();
            historialController.cargarHistorial(idMascota);  // ← CORRECTO

            Stage stage = new Stage();
            stage.setTitle("Historial Médico");
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Historial médico abierto para ID mascota = " + idMascota);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handle_GenerarExpediente() {
        try {
            File fxmlFile = new File("src/main/java/vista/generar_expediente.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();

            // Obtener el controlador
            GenerarExpedienteController generarExpedienteController = loader.getController();
            generarExpedienteController.cargarCitaId(this.citaId); // Pasar citaId

            Stage stage = new Stage();
            stage.setTitle("Generar Expediente");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
