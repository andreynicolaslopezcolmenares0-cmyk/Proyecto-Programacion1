package controlador;

import dominio.*;
import servicio.UsuarioServicio;
import java.util.List;

/**
 * Controlador que expone las operaciones de gestión de usuarios hacia la interfaz gráfica.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class ControladorUsuario {
    private final UsuarioServicio servicio = new UsuarioServicio();

    public void registrarAdministrador(Administrador a) { servicio.guardar(a); }
    public void registrarContratante(Contratante c) { servicio.guardar(c); }
    public void registrarContratista(Contratista c) { servicio.guardar(c); }
    public List<Usuario> listarTodosLosUsuarios() { return servicio.obtenerTodos(); }
    public Usuario buscarPorDocumento(String doc) { return servicio.buscarPorDocumento(doc); }
    public void actualizarUsuario(Usuario u) { servicio.actualizar(u); }
    public void eliminarUsuario(String doc) { servicio.eliminar(doc); }
}
