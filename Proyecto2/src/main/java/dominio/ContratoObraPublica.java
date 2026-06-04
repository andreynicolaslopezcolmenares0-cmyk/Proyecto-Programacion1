package dominio;

import enums.EstadoContrato;
import java.time.LocalDate;

/**
 * Modela infraestructuras civiles, construcciones y mantenimiento de bienes del estado.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class ContratoObraPublica extends Contrato {
    private String ubicacionObra;
    private double areaIntervencion;

    public ContratoObraPublica(String objetoContrato, LocalDate fechaCreacion, Contratante contratante,
                               Contratista contratista, double valorTotal, int plazoEjecucion,
                               EstadoContrato estado, String ubicacionObra, double areaIntervencion) {
        super(objetoContrato, fechaCreacion, contratante, contratista, valorTotal, plazoEjecucion, estado);
        this.ubicacionObra = ubicacionObra;
        this.areaIntervencion = areaIntervencion;
    }

    @Override
    public void validarReglasNegocio() {
        // Cumple la especificación base del modelo sin restricciones financieras iniciales adicionales.
    }

    public String getUbicacionObra() { return ubicacionObra; }
    public void setUbicacionObra(String ubicacionObra) { this.ubicacionObra = ubicacionObra; }
    public double getAreaIntervencion() { return areaIntervencion; }
    public void setAreaIntervencion(double areaIntervencion) { this.areaIntervencion = areaIntervencion; }
}
