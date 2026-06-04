package excepciones;

/**
 * Excepción lanzada cuando los datos de entrada no cumplen con los formatos o restricciones básicas.
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String mensaje) {
        super(mensaje);
    }
}
