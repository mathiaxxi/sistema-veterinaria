package modelo;

import java.util.Date;

public class HistorialMedico {
    private int id;
    private int idMascota;
    private Date fechaCreacion;

    public HistorialMedico() {}

    public HistorialMedico(int id, int idMascota, Date fechaCreacion) {
        this.id = id;
        this.idMascota = idMascota;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Historial ID: " + id + ", Mascota ID: " + idMascota;
    }
}
