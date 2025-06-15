package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Cita;
import modelo.Mascota;
import modelo.Servicio;
import modelo.Usuario;
import modelo.VeterinarioServicio;
import dao.CitaDAO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ReservarCitaController {

    @FXML private ComboBox<Usuario> cmb_veterinario;
    @FXML private ComboBox<Mascota> cmb_mascota;
    @FXML private ComboBox<Servicio> cmb_servicio;
    @FXML private DatePicker date_fecha;
    @FXML private ComboBox<String> cmb_turno;
    @FXML private ComboBox<String> cmb_hora;
    @FXML private TextField txt_dni_dueño;
    @FXML private TextField txt_motivo;

    private Map<String, Mascota> mapMascotas = new HashMap<>();
    private Map<String, Servicio> mapServicios = new HashMap<>();
    private Map<String, Usuario> mapVeterinarios = new HashMap<>();

    private CitaDAO citaDAO = new CitaDAO();

    @FXML
    public void initialize() {
        cargarServicios();

        cmb_turno.setItems(FXCollections.observableArrayList("mañana", "tarde"));

        // Listener para cuando cambie el servicio, filtrar veterinarios que lo atienden
        cmb_servicio.setOnAction(e -> cargarVeterinariosPorServicio());

        // Cargar horas disponibles cuando cambie turno, veterinario o fecha
        cmb_turno.setOnAction(e -> cargarHorasDisponibles());
        cmb_veterinario.setOnAction(e -> cargarHorasDisponibles());
        date_fecha.setOnAction(e -> cargarHorasDisponibles());
    }

    private void cargarServicios() {
        List<Servicio> servicios = citaDAO.obtenerServicios();
        mapServicios.clear();
        for (Servicio s : servicios) {
            mapServicios.put(s.getNombre(), s);
        }
        cmb_servicio.setItems(FXCollections.observableArrayList(mapServicios.values()));
    }

    private void cargarVeterinariosPorServicio() {
        Servicio servicioSeleccionado = cmb_servicio.getValue();
        if (servicioSeleccionado == null) {
            cmb_veterinario.getItems().clear();
            return;
        }
        // Obtener veterinarios que brindan ese servicio
        mapVeterinarios = citaDAO.obtenerVeterinariosPorServicio(servicioSeleccionado.getId());
        cmb_veterinario.setItems(FXCollections.observableArrayList(mapVeterinarios.values()));

        // Limpiar horas y selección veterinario para evitar inconsistencias
        cmb_hora.getItems().clear();
        cmb_veterinario.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleFiltrarMascotas() {
        String dni = txt_dni_dueño.getText().trim();

        if (dni.isEmpty()) {
            cmb_mascota.getItems().clear();
            return;
        }

        mapMascotas = citaDAO.obtenerMascotasPorDni(dni);
        ObservableList<Mascota> mascotas = FXCollections.observableArrayList(mapMascotas.values());

        if (mascotas.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "No se encontraron mascotas para ese DNI.").showAndWait();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Mascotas encontradas: " + mascotas.size()).showAndWait();
        }

        cmb_mascota.setItems(mascotas);
    }

    private void cargarHorasDisponibles() {
        cmb_hora.getItems().clear();

        String turno = cmb_turno.getValue();
        Usuario vetSeleccionado = cmb_veterinario.getValue();
        LocalDate fecha = date_fecha.getValue();

        if (turno == null || vetSeleccionado == null || fecha == null) return;

        int idVeterinario = vetSeleccionado.getId();

        List<String> horasTurno = turno.equals("mañana") ?
                Arrays.asList("08:00", "09:00", "10:00", "11:00", "12:00") :
                Arrays.asList("14:00", "15:00", "16:00", "17:00");

        Set<String> ocupadas = citaDAO.obtenerHorasOcupadas(idVeterinario, Timestamp.valueOf(fecha.atStartOfDay()));

        ObservableList<String> horasDisponibles = FXCollections.observableArrayList();
        for (String hora : horasTurno) {
            if (!ocupadas.contains(hora)) {
                horasDisponibles.add(hora);
            }
        }

        cmb_hora.setItems(horasDisponibles);
    }

    @FXML
    private void handle_reservar_cita() {
        Usuario vetSeleccionado = cmb_veterinario.getValue();
        Mascota mascotaSeleccionada = cmb_mascota.getValue();
        Servicio servicioSeleccionado = cmb_servicio.getValue();
        LocalDate fecha = date_fecha.getValue();
        String hora = cmb_hora.getValue();
        String motivo = txt_motivo.getText();

        if (vetSeleccionado == null || mascotaSeleccionada == null || servicioSeleccionado == null || fecha == null || hora == null || motivo.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Completa todos los campos.").showAndWait();
            return;
        }

        LocalDateTime fechaHora = LocalDateTime.of(fecha, LocalTime.parse(hora));
        Cita nuevaCita = new Cita(
                mascotaSeleccionada.getIdCliente(),
                vetSeleccionado.getId(),
                mascotaSeleccionada.getId(),
                servicioSeleccionado.getId(),
                Timestamp.valueOf(fechaHora),
                motivo,
                "Pendiente",
                Timestamp.valueOf(LocalDateTime.now())
        );

        boolean registrada = citaDAO.insertar(nuevaCita);

        if (registrada) {
            new Alert(Alert.AlertType.INFORMATION, "Cita registrada correctamente.").showAndWait();
            txt_motivo.clear();
            cmb_hora.getItems().clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error al registrar la cita.").showAndWait();
        }
    }
}
