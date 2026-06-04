package servicio;

import dominio.Usuario;
import excepciones.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lógica interna persistente en memoria para el almacenamiento y ciclo de vida de usuarios.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class UsuarioServicio {
    private final List<Usuario> baseUsuarios = new ArrayList<>();

    public void guardar(Usuario u) {
        baseUsuarios.add(u);
    }

    public List<Usuario> obtenerTodos() {
        return baseUsuarios;
    }

    public Usuario buscarPorDocumento(String documento) {
        return baseUsuarios.stream()
                .filter(u -> u.getNumeroDocumento().equalsIgnoreCase(documento))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Usuario con documento " + documento + " no se encuentra registrado."));
    }

    public void actualizar(Usuario uActualizado) {
        Usuario u = buscarPorDocumento(uActualizado.getNumeroDocumento());
        int index = baseUsuarios.indexOf(u);
        baseUsuarios.set(index, uActualizado);
    }

    public void eliminar(String documento) {
        Usuario u = buscarPorDocumento(documento);
        baseUsuarios.remove(u);
    }
}
