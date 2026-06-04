package dominio;

import enums.EstadoContrato;
import excepciones.ContractValidationException;
import java.time.LocalDate;

/**
 * Representa contratos basados en actividades intelectuales o de servicios profesionales.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class ContratoPrestacionServicios extends Contrato {
    private String perfilRequerido;
    private String entregables;
    private double valorHonorarioMensual;

    public ContratoPrestacionServicios(String objetoContrato, LocalDate fechaCreacion, Contratante contratante,
                                       Contratista contratista, double valorTotal, int plazoEjecucion,
                                       EstadoContrato estado, String perfilRequerido, String entregables,
                                       double valorHonorarioMensual) {
        super(objetoContrato, fechaCreacion, contratante, contratista, valorTotal, plazoEjecucion, estado);
        this.perfilRequerido = perfilRequerido;
        this.entregables = entregables;
        this.valorHonorarioMensual = valorHonorarioMensual;
        validarReglasNegocio();
    }

    @Override
    public void validarReglasNegocio() {
        if (this.valorHonorarioMensual > getValorTotal()) {
            throw new ContractValidationException("Inconsistencia Financiera: El honorario mensual no puede superar el monto global del contrato.");
        }
    }

    public String getPerfilRequerido() { return perfilRequerido; }
    public void setPerfilRequerido(String perfilRequerido) { this.perfilRequerido = perfilRequerido; }
    public String getEntregables() { return entregables; }
    public void setEntregables(String entregables) { this.entregables = entregables; }
    public double getValorHonorarioMensual() { return valorHonorarioMensual; }
    public void setValorHonorarioMensual(double valorHonorarioMensual) { this.valorHonorarioMensual = valorHonorarioMensual; }
}
