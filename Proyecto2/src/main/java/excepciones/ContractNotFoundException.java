package excepciones;

/**
 * Excepción lanzada cuando el identificador de un contrato no figura en los registros.
 */
public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(String mensaje) {
        super(mensaje);
    }
}
