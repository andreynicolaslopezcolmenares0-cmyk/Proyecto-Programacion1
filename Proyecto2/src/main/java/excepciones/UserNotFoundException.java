package excepciones;

/**
 * Excepción lanzada cuando se intenta buscar, actualizar o eliminar un usuario inexistente.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String mensaje) {
        super(mensaje);
    }
}
