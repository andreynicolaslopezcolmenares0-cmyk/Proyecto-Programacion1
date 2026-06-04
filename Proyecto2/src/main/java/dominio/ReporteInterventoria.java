package dominio;

import java.time.LocalDateTime;

/**
 * Consolida las trazas e informes de auditoría generados ante cambios de fase/estado contractual.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class ReporteInterventoria {
    private Contrato contratoModificado;
    private String informeJustificacion;
    private LocalDateTime fechaHora;

    public ReporteInterventoria(Contrato contratoModificado, String informeJustificacion) {
        this.contratoModificado = contratoModificado;
        this.informeJustificacion = informeJustificacion;
        this.fechaHora = LocalDateTime.now();
    }

    public Contrato getContratoModificado() { return contratoModificado; }
    public String getInformeJustificacion() { return informeJustificacion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}
