package controlador;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import modelo.DetalleHistorial;
import dao.HistorialMedicoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

public class HistorialMedicoController {

    @FXML private VBox vboxHistorial;
    private int idMascota; // Almacenar el ID de la mascota seleccionada
    private HistorialMedicoDAO historialMedicoDAO;

    public void initialize() {
        // Inicializamos el DAO para poder usarlo
        historialMedicoDAO = new HistorialMedicoDAO();
    }

public void cargarHistorial(int idMascota) {
    this.idMascota = idMascota;
    System.out.println("Cargando historial para la mascota con ID: " + idMascota);

    // Obtener los detalles del historial médico para la mascota seleccionada
    ObservableList<DetalleHistorial> detalles = FXCollections.observableArrayList();
    detalles.addAll(historialMedicoDAO.obtenerDetallesHistorial(idMascota));

    System.out.println("Se obtuvieron " + detalles.size() + " registros del historial.");

    // Limpiar cualquier contenido anterior
    vboxHistorial.getChildren().clear();

    // Recorrer los detalles y crear los elementos visuales dinámicamente
    for (DetalleHistorial detalle : detalles) {
        System.out.println("Detalle: " + detalle.getFechaVisita() + " - " + detalle.getSintomas());

        HBox hboxDetalle = new HBox(10);
        hboxDetalle.setPadding(new Insets(5, 5, 5, 5));

        Label labelFechaVisita = new Label("Fecha: " + detalle.getFechaVisita());
        Label labelSintomas = new Label("Síntomas: " + detalle.getSintomas());
        Label labelDiagnostico = new Label("Diagnóstico: " + detalle.getDiagnostico());
        Label labelTratamiento = new Label("Tratamiento: " + detalle.getTratamiento());
        Label labelObservaciones = new Label("Observaciones: " + detalle.getObservaciones());
        Label labelVeterinario = new Label("Veterinario: " + detalle.getIdVeterinario());

        labelFechaVisita.setStyle("-fx-font-weight: bold;");
        labelSintomas.setStyle("-fx-text-fill: #555;");
        labelDiagnostico.setStyle("-fx-text-fill: #555;");
        labelTratamiento.setStyle("-fx-text-fill: #555;");
        labelObservaciones.setStyle("-fx-text-fill: #555;");
        labelVeterinario.setStyle("-fx-text-fill: #555;");

        hboxDetalle.getChildren().addAll(labelFechaVisita, labelSintomas, labelDiagnostico, labelTratamiento, labelObservaciones, labelVeterinario);
        vboxHistorial.getChildren().add(hboxDetalle);
    }
}

}
