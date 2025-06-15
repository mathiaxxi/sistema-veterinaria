package modelo;

public class VeterinarioServicio {
    private int idVeterinario;
    private int idServicio;

    public VeterinarioServicio() {}

    public VeterinarioServicio(int idVeterinario, int idServicio) {
        this.idVeterinario = idVeterinario;
        this.idServicio = idServicio;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }
}
