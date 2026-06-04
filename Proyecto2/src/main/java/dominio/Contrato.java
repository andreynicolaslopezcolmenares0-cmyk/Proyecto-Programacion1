package dominio;

import enums.EstadoContrato;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Estructura abstracta que implementa el núcleo operacional de cualquier contrato público.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public abstract class Contrato {
    private final String idContrato;
    private String objetoContrato;
    private LocalDate fechaCreacion;
    private Contratante contratante;
    private Contratista contratista;
    private double valorTotal;
    private int plazoEjecucion;
    private EstadoContrato estado;

    public Contrato(String objetoContrato, LocalDate fechaCreacion, Contratante contratante,
                    Contratista contratista, double valorTotal, int plazoEjecucion, EstadoContrato estado) {
        this.idContrato = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.objetoContrato = objetoContrato;
        this.fechaCreacion = fechaCreacion;
        this.contratante = contratante;
        this.contratista = contratista;
        this.valorTotal = valorTotal;
        this.plazoEjecucion = plazoEjecucion;
        this.estado = estado;
    }

    /**
     * Fuerza el cumplimiento de validaciones específicas según el subtipo contractual.
     */
    public abstract void validarReglasNegocio();

    // Getters y Setters
    public String getIdContrato() { return idContrato; }
    public String getObjetoContrato() { return objetoContrato; }
    public void setObjetoContrato(String objetoContrato) { this.objetoContrato = objetoContrato; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Contratante getContratante() { return contratante; }
    public void setContratante(Contratante contratante) { this.contratante = contratante; }
    public Contratista getContratista() { return contratista; }
    public void setContratista(Contratista contratista) { this.contratista = contratista; }
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    public int getPlazoEjecucion() { return plazoEjecucion; }
    public void setPlazoEjecucion(int plazoEjecucion) { this.plazoEjecucion = plazoEjecucion; }
    public EstadoContrato getEstado() { return estado; }
    public void setEstado(EstadoContrato estado) { this.estado = estado; }
}
