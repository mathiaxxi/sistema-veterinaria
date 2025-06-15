package controlador;

import modelo.Usuario;

public class PanelAdministradorController {

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Aquí ya no debería marcar error
        System.out.println("Usuario recibido: " + usuario.getNombre());
    }
}
