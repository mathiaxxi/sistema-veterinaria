package controlador;

import dao.ClienteDAO;
import dao.MascotaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Cliente;
import modelo.Mascota;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegistrarMascotaController {

    @FXML private TextField campoNombreMascota;
    @FXML private ComboBox<String> comboEspecie;
    @FXML private ComboBox<String> comboRaza;
    @FXML private TextField campoEdad;
    @FXML private TextField campoPeso;
    @FXML private ComboBox<String> comboSexo;

    @FXML private TextField campoDniCliente;
    @FXML private TextField campoNombreCliente;
    @FXML private TextField campoApellidoCliente;
    @FXML private TextField campoTelefonoCliente;
    @FXML private TextField campoCorreoCliente;
    @FXML private TextField campoDireccionCliente;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final MascotaDAO mascotaDAO = new MascotaDAO();

    private ObservableList<String> listaEspecies;

    // Mapa especie -> lista de razas válidas
    private Map<String, ObservableList<String>> mapaEspecieRazas;

    @FXML
    public void initialize() {
        comboSexo.getItems().addAll("Macho", "Hembra");

        listaEspecies = FXCollections.observableArrayList("Perro", "Gato", "Pez", "Ave", "Conejo");

        // Definir razas reales para cada especie
        mapaEspecieRazas = new HashMap<>();

        mapaEspecieRazas.put("Perro", FXCollections.observableArrayList(
                "Labrador Retriever",
                "Bulldog",
                "Beagle",
                "Pastor Alemán",
                "Golden Retriever",
                "Poodle",
                "Chihuahua",
                "Dachshund"
        ));

        mapaEspecieRazas.put("Gato", FXCollections.observableArrayList(
                "Siamés",
                "Persa",
                "Maine Coon",
                "Ragdoll",
                "Bengalí",
                "Sphynx",
                "British Shorthair",
                "Abisinio"
        ));

        mapaEspecieRazas.put("Pez", FXCollections.observableArrayList(
                "Goldfish",
                "Betta",
                "Guppy",
                "Tetra Neón",
                "Discus",
                "Oscar"
        ));

        mapaEspecieRazas.put("Ave", FXCollections.observableArrayList(
                "Canario",
                "Periquito",
                "Agapornis",
                "Cacatúa",
                "Loro Amazona"
        ));

        mapaEspecieRazas.put("Conejo", FXCollections.observableArrayList(
                "Angora",
                "Cabeza de León",
                "Mini Rex",
                "Californiano"
        ));

        configurarFiltroComboBox(comboEspecie, listaEspecies);

        // Inicialmente no hay razas seleccionadas ni habilitadas
        comboRaza.setDisable(true);

        // Listener para actualizar las razas según la especie seleccionada
        comboEspecie.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && mapaEspecieRazas.containsKey(newVal)) {
                ObservableList<String> razas = mapaEspecieRazas.get(newVal);
                comboRaza.setDisable(razas.isEmpty());
                configurarFiltroComboBox(comboRaza, razas);
                comboRaza.getSelectionModel().clearSelection();
                comboRaza.getEditor().clear();
            } else {
                comboRaza.setItems(FXCollections.emptyObservableList());
                comboRaza.setDisable(true);
                comboRaza.getSelectionModel().clearSelection();
                comboRaza.getEditor().clear();
            }
        });
    }

    private void configurarFiltroComboBox(ComboBox<String> comboBox, ObservableList<String> listaOriginal) {
        FilteredList<String> filteredList = new FilteredList<>(listaOriginal, s -> true);
        comboBox.setItems(filteredList);
        comboBox.setEditable(true);

        comboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            final TextField editor = comboBox.getEditor();
            final String selected = comboBox.getSelectionModel().getSelectedItem();

            if (newVal == null || newVal.isEmpty()) {
                filteredList.setPredicate(s -> true);
            } else {
                String filter = newVal.toLowerCase();
                filteredList.setPredicate(s -> s.toLowerCase().contains(filter));
            }

            if (selected == null || !selected.equals(editor.getText())) {
                comboBox.show();
            }
        });
    }

    @FXML
    private void handleBuscarCliente() {
        String dni = campoDniCliente.getText().trim();
        if (dni.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Buscar Cliente", "Ingrese un DNI para buscar.");
            return;
        }

        Optional<Cliente> clienteOpt = clienteDAO.buscarPorDni(dni);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            campoNombreCliente.setText(cliente.getNombre());
            campoApellidoCliente.setText(cliente.getApellido());
            campoTelefonoCliente.setText(cliente.getTelefono());
            campoCorreoCliente.setText(cliente.getCorreo());
            campoDireccionCliente.setText(cliente.getDireccion());

            deshabilitarCamposCliente(true);
        } else {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente no encontrado", "No se encontró un cliente con ese DNI. Ingrese los datos.");
            deshabilitarCamposCliente(false);
            limpiarCamposCliente();
        }
    }

    @FXML
    private void handleRegistrarMascota() {
        try {
            String dni = campoDniCliente.getText().trim();
            Optional<Cliente> clienteOpt = clienteDAO.buscarPorDni(dni);
            Cliente cliente;

            if (clienteOpt.isPresent()) {
                cliente = clienteOpt.get();
            } else {
                cliente = new Cliente();
                cliente.setNombre(campoNombreCliente.getText().trim());
                cliente.setApellido(campoApellidoCliente.getText().trim());
                cliente.setDni(dni);
                cliente.setTelefono(campoTelefonoCliente.getText().trim());
                cliente.setCorreo(campoCorreoCliente.getText().trim());
                cliente.setDireccion(campoDireccionCliente.getText().trim());

                int nuevoId = clienteDAO.insertar(cliente);
                cliente.setId(nuevoId);
            }

            Mascota mascota = new Mascota();
            mascota.setNombre(campoNombreMascota.getText().trim());
            mascota.setEspecie(comboEspecie.getEditor().getText().trim());
            mascota.setRaza(comboRaza.getEditor().getText().trim());
            mascota.setEdad(Integer.parseInt(campoEdad.getText().trim()));
            mascota.setSexo(comboSexo.getValue());
            mascota.setPeso(new BigDecimal(campoPeso.getText().trim()));
            mascota.setIdCliente(cliente.getId());

            mascotaDAO.insertar(mascota);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", "Mascota registrada correctamente.");
            limpiarFormulario();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al registrar la mascota.");
        }
    }

    private void deshabilitarCamposCliente(boolean deshabilitar) {
        campoNombreCliente.setDisable(deshabilitar);
        campoApellidoCliente.setDisable(deshabilitar);
        campoTelefonoCliente.setDisable(deshabilitar);
        campoCorreoCliente.setDisable(deshabilitar);
        campoDireccionCliente.setDisable(deshabilitar);
    }

    private void limpiarCamposCliente() {
        campoNombreCliente.clear();
        campoApellidoCliente.clear();
        campoTelefonoCliente.clear();
        campoCorreoCliente.clear();
        campoDireccionCliente.clear();
    }

    private void limpiarFormulario() {
        campoDniCliente.clear();
        limpiarCamposCliente();
        deshabilitarCamposCliente(false);
        campoNombreMascota.clear();
        comboEspecie.getEditor().clear();
        comboRaza.getEditor().clear();
        comboRaza.setDisable(true);
        campoEdad.clear();
        campoPeso.clear();
        comboSexo.setValue(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
