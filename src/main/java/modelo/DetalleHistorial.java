package modelo;

import java.util.Date;

public class DetalleHistorial {
    private int id;
    private int idHistorial;
    private Date fechaVisita;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private int idVeterinario;          // Renombrado de usuarioCreador
    private Date fechaCreacion;         // Nueva propiedad

    public DetalleHistorial() {}

    public DetalleHistorial(int id, int idHistorial, Date fechaVisita, String sintomas, String diagnostico,
                            String tratamiento, String observaciones, int idVeterinario, Date fechaCreacion) {
        this.id = id;
        this.idHistorial = idHistorial;
        this.fechaVisita = fechaVisita;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.idVeterinario = idVeterinario;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public Date getFechaVisita() { return fechaVisita; }
    public void setFechaVisita(Date fechaVisita) { this.fechaVisita = fechaVisita; }

    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public int getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(int idVeterinario) { this.idVeterinario = idVeterinario; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public String toString() {
        return "DetalleHistorial ID: " + id + ", Fecha Visita: " + fechaVisita;
    }
}
