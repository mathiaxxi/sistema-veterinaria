package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modelo.Cita;
import modelo.PanelVeterinarioDAO;
import modelo.Mascota;
import dao.MascotaDAO;
import java.io.File;
import modelo.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PanelVeterinarioController {

    @FXML private VBox vboxManana; // Para mostrar las citas de la mañana
    @FXML private VBox vboxTarde;  // Para mostrar las citas de la tarde
    @FXML private Label labelNombreVeterinario;  // Para mostrar el nombre del veterinario

    private PanelVeterinarioDAO citaDAO = new PanelVeterinarioDAO();
    private MascotaDAO mascotaDAO = new MascotaDAO();  // Nuevo DAO para mascotas
    private Usuario usuario;

    // Formato para mostrar hora (HH:mm)
    private static final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Método para setear el usuario veterinario
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelNombreVeterinario.setText("Veterinario: " + usuario.getNombre());
        System.out.println("Usuario veterinario: " + usuario.getNombre());
        cargarCitasDelDia();
    }

    // Método que carga las citas del día (solo del día actual)
    public void cargarCitasDelDia() {
        if (usuario == null) {
            System.out.println("Usuario veterinario no definido.");
            return;
        }

        List<Cita> citas = citaDAO.obtenerCitasDelDia(usuario.getId());

        vboxManana.getChildren().clear();
        vboxTarde.getChildren().clear();

for (Cita cita : citas) {
    LocalDateTime fechaHora = cita.getFechaHora().toLocalDateTime();
    int hora = fechaHora.getHour();

    // Obtener nombre de la mascota (usando MascotaDAO que debes crear)
    Mascota mascota = mascotaDAO.obtenerMascotaPorId(cita.getIdMascota());
    String nombreMascota = (mascota != null) ? mascota.getNombre() : "Desconocida";

    // Formatear horas para rango (ejemplo: 10:00-11:00)
    String horaInicio = fechaHora.format(HORA_FORMATTER);
    String horaFin = fechaHora.plusHours(1).format(HORA_FORMATTER);
    String rangoHorario = horaInicio + " - " + horaFin;

    // Crear Labels para cada columna
    Label lblMascota = new Label(nombreMascota);
    Label lblMotivo = new Label(cita.getMotivo());
    Label lblHora = new Label(rangoHorario);
    Label lblEstado = new Label(cita.getEstado());

    // Estilo simple (opcional)
    lblMascota.setStyle("-fx-font-size: 14px;");
    lblMotivo.setStyle("-fx-font-size: 14px;");
    lblHora.setStyle("-fx-font-size: 14px;");
    lblEstado.setStyle("-fx-font-size: 14px;");

    // Que el motivo ocupe todo el espacio posible para centrarlo
    HBox.setHgrow(lblMotivo, Priority.ALWAYS);
    lblMotivo.setMaxWidth(Double.MAX_VALUE);
    lblMotivo.setAlignment(Pos.CENTER);

    // Alinear hora y estado a la derecha
    lblHora.setAlignment(Pos.CENTER_RIGHT);
    lblEstado.setAlignment(Pos.CENTER_RIGHT);

    // Crear contenedor horizontal para la cita
    HBox hboxCita = new HBox(20);  // espacio entre columnas = 20px
    hboxCita.setAlignment(Pos.CENTER_LEFT);

    // Agregar labels al HBox
    hboxCita.getChildren().addAll(lblMascota, lblMotivo, lblHora, lblEstado);

    // Asociar el ID de la cita al HBox usando setUserData
    hboxCita.setUserData(cita.getId());  // Aquí se guarda el ID de la cita

    // Verificación: Imprimir el ID de la cita al asociarlo
    System.out.println("Asociando ID de la cita al HBox: " + cita.getId());

    // Evento clic para abrir detalle
    hboxCita.setOnMouseClicked(event -> {
        // Recuperamos el ID de la cita desde el HBox
        int citaId = (int) hboxCita.getUserData();
        
        // Verificación: Imprimir el ID de la cita cuando se hace clic
        System.out.println("ID de la cita recuperado al hacer clic: " + citaId);

        abrirDetalleCita(citaId);  // Llamamos al método para abrir el detalle de la cita
    });

    // Agregar al VBox correspondiente (mañana o tarde)
    if (hora >= 8 && hora < 13) {
        vboxManana.getChildren().add(hboxCita);
    } else if (hora >= 14 && hora < 18) {
        vboxTarde.getChildren().add(hboxCita);
    }
}
    }

private void abrirDetalleCita(int citaId) {
    try {
        // Aquí se carga el archivo FXML para el detalle de la cita
        File fxmlFile = new File("src/main/java/vista/detalle_cita.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        Parent root = loader.load();

        // Obtener el controlador de la ventana de detalle de cita
        DetalleCitaController detalleCitaController = loader.getController();

        // Pasar el ID de la cita para que el controlador pueda cargar los datos
        detalleCitaController.cargarDetalleCita(citaId);

        // Aquí se abre una nueva ventana
        Stage stage = new Stage();
        stage.setTitle("Detalle Cita");
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
