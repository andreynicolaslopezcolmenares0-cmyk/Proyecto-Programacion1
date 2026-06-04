package excepciones;

/**
 * Excepción para accesos denegados debido a credenciales inválidas.
 */
public class AutenticacionException extends RuntimeException {
    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}
