package modelo;

public class Recepcionista {
    private int id;
    private int idUsuario;
    private String turno;

    public Recepcionista() {}

    public Recepcionista(int id, int idUsuario, String turno) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.turno = turno;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Recepcionista{" +
               "id=" + id +
               ", idUsuario=" + idUsuario +
               ", turno='" + turno + '\'' +
               '}';
    }
}
