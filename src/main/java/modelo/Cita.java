package modelo;

import java.sql.Timestamp;

public class Cita {
    private int id;
    private int idCliente;
    private int idVeterinario;
    private int idMascota;
    private int idServicio;  // Nuevo campo para el tipo de servicio
    private Timestamp fechaHora;
    private String motivo;
    private String estado = "Pendiente";
    private Timestamp fechaCreacion;

    // Constructor vacío
    public Cita() {
    }

    // Constructor con parámetros (incluye fechaCreacion y idServicio)
    public Cita(int idCliente, int idVeterinario, int idMascota, int idServicio, Timestamp fechaHora, String motivo, String estado, Timestamp fechaCreacion) {
        this.idCliente = idCliente;
        this.idVeterinario = idVeterinario;
        this.idMascota = idMascota;
        this.idServicio = idServicio;  // Asignamos el servicio
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
