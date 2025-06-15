package modelo;

public class Veterinario {
    private int id;
    private int idUsuario;
    private String especialidad;
    private String cmp;

    public Veterinario() {}

    public Veterinario(int id, int idUsuario, String especialidad, String cmp) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.especialidad = especialidad;
        this.cmp = cmp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    @Override
    public String toString() {
        return "Veterinario ID: " + id + ", Especialidad: " + especialidad;
    }
}
