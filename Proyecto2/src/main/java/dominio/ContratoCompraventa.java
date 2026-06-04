package dominio;

import enums.EstadoContrato;
import excepciones.ContractValidationException;
import java.time.LocalDate;

/**
 * Representa la adquisición de bienes muebles corporales o suministros tangibles.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class ContratoCompraventa extends Contrato {
    private String itemBienes;
    private String marca;
    private String modelo;
    private String serie;
    private double valorUnitario;
    private int cantidadAdquirir;

    public ContratoCompraventa(String objetoContrato, LocalDate fechaCreacion, Contratante contratante,
                               Contratista contratista, double valorTotal, int plazoEjecucion,
                               EstadoContrato estado, String itemBienes, String marca, String modelo,
                               String serie, double valorUnitario, int cantidadAdquirir) {
        super(objetoContrato, fechaCreacion, contratante, contratista, valorTotal, plazoEjecucion, estado);
        this.itemBienes = itemBienes;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.valorUnitario = valorUnitario;
        this.cantidadAdquirir = cantidadAdquirir;
        validarReglasNegocio();
    }

    @Override
    public void validarReglasNegocio() {
        double calculoTotal = this.valorUnitario * this.cantidadAdquirir;
        if (Math.abs(calculoTotal - getValorTotal()) > 0.01) {
            throw new ContractValidationException("Error de Cálculo: El producto de la cantidad por el valor unitario ($" + calculoTotal + ") difiere del valor total estipulado ($" + getValorTotal() + ").");
        }
    }

    // Getters y Setters
    public String getItemBienes() { return itemBienes; }
    public void setItemBienes(String itemBienes) { this.itemBienes = itemBienes; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }
    public int getCantidadAdquirir() { return cantidadAdquirir; }
    public void setCantidadAdquirir(int cantidadAdquirir) { this.cantidadAdquirir = cantidadAdquirir; }
}
