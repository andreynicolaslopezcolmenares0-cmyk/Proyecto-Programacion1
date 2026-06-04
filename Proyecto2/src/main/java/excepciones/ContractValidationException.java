package excepciones;

/**
 * Excepción de negocio encargada de validar los montos económicos y lógicas de contratos.
 */
public class ContractValidationException extends RuntimeException {
    public ContractValidationException(String mensaje) {
        super(mensaje);
    }
}
