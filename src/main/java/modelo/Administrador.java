package modelo;

public class Administrador {
    private int id;
    private int idUsuario;

    public Administrador() {}

    public Administrador(int id, int idUsuario) {
        this.id = id;
        this.idUsuario = idUsuario;
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

    @Override
    public String toString() {
        return "Administrador ID: " + id + ", Usuario ID: " + idUsuario;
    }
}
