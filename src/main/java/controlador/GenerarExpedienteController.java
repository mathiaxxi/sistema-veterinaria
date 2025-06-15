package controlador;

import dao.CitaDAO;
import dao.HistorialMedicoDAO;
import dao.DetalleHistorialDAO;
import modelo.Cita;
import modelo.HistorialMedico;
import modelo.DetalleHistorial;
import modelo.Usuario;

import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GenerarExpedienteController {

    private CitaDAO citaDAO;
    private HistorialMedicoDAO historialMedicoDAO;
    private DetalleHistorialDAO detalleHistorialDAO;

    // Variables FXML
    @FXML private TextField txtFechaVisita;
    @FXML private TextArea txtSintomas;
    @FXML private TextArea txtDiagnostico;
    @FXML private TextArea txtTratamiento;
    @FXML private TextArea txtObservaciones;

    // Variables dinámicas que se recibirán desde otra ventana
    private int idCita;
    private int idVeterinario;

    public GenerarExpedienteController() {
        citaDAO = new CitaDAO();
        historialMedicoDAO = new HistorialMedicoDAO();
        detalleHistorialDAO = new DetalleHistorialDAO();
    }

    // Método para setear IDs desde otra ventana
    public void setDatos(int idCita, int idVeterinario) {
        this.idCita = idCita;
        this.idVeterinario = idVeterinario;
        System.out.println("ID de la cita recibido: " + idCita);
        System.out.println("ID del veterinario recibido: " + idVeterinario);
    }

    // Método para generar expediente
   public boolean generarExpediente(String sintomas, String diagnostico, String tratamiento, String observaciones) {
    Cita cita = citaDAO.obtenerCitaPorId(idCita);

    if (cita == null) {
        System.out.println("No se encontró la cita con ID: " + idCita);
        return false;
    }

    System.out.println("ID de la mascota recuperado de la cita: " + cita.getIdMascota());

    // Aquí obtienes el historial de la mascota, si ya existe, o lo creas si no existe.
    HistorialMedico historial = historialMedicoDAO.obtenerHistorialPorMascota(cita.getIdMascota());

    if (historial == null) {
        System.out.println("No se encontró historial médico. Creando uno nuevo.");
        historial = new HistorialMedico();
        historial.setIdMascota(cita.getIdMascota());
        historial.setFechaCreacion(new Date());

        boolean historialInsertado = historialMedicoDAO.insertarHistorialMedico(historial);
        if (!historialInsertado) {
            System.out.println("Error al crear el historial médico.");
            return false;
        }
    }

    // Ahora vamos a crear un DetalleHistorial.
    DetalleHistorial detalle = new DetalleHistorial();
    detalle.setIdHistorial(historial.getId());
    detalle.setFechaVisita(new Date());
    detalle.setSintomas(sintomas);
    detalle.setDiagnostico(diagnostico);
    detalle.setTratamiento(tratamiento);
    detalle.setObservaciones(observaciones);

    // Asegúrate de pasar el id del Veterinario, no el id del Usuario
    detalle.setIdVeterinario(cita.getIdVeterinario());  // Aquí usas el id del veterinario de la cita

    boolean detalleInsertado = detalleHistorialDAO.insertarDetalle(detalle);
    if (!detalleInsertado) {
        System.out.println("Error al insertar el detalle del historial.");
        return false;
    }

    System.out.println("Expediente generado exitosamente.");
    return true;
}

    
public void cargarCitaId(int idCita) {
    this.idCita = idCita;
    System.out.println("ID de cita cargado desde cargarCitaId: " + idCita);
}
    // Método para manejar el botón de guardar
    @FXML
    private void handleGuardarExpediente() {
        String sintomas = txtSintomas.getText();
        String diagnostico = txtDiagnostico.getText();
        String tratamiento = txtTratamiento.getText();
        String observaciones = txtObservaciones.getText();

        boolean resultado = generarExpediente(sintomas, diagnostico, tratamiento, observaciones);

        if (resultado) {
            System.out.println("Expediente guardado correctamente.");
        } else {
            System.out.println("Error al guardar el expediente.");
        }
    }
    
}
