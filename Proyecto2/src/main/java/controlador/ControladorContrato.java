package controlador;

import dominio.Contrato;
import dominio.ReporteInterventoria;
import enums.EstadoContrato;
import servicio.ContratoServicio;
import java.util.List;

/**
 * Controlador que expone las operaciones de contratos e interventoría hacia la interfaz gráfica.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class ControladorContrato {
    private final ContratoServicio servicio = new ContratoServicio();

    public void crearContrato(Contrato c) { servicio.crearContrato(c); }
    public List<Contrato> listarContratos() { return servicio.obtenerTodos(); }
    public Contrato consultarContratoPorId(String id) { return servicio.buscarPorId(id); }
    public void actualizarContrato(Contrato c) { servicio.actualizarContrato(c); }
    public void eliminarContrato(String id) { servicio.eliminarContrato(id); }

    public void cambiarEstadoContrato(String idContrato, EstadoContrato nuevoEstado, String justificacion) {
        Contrato c = servicio.buscarPorId(idContrato);
        c.setEstado(nuevoEstado);
        ReporteInterventoria nuevoReporte = new ReporteInterventoria(c, justificacion);
        servicio.agregarReporte(nuevoReporte);
    }

    public List<ReporteInterventoria> listarReportes() { return servicio.obtenerTodosLosReportes(); }
}
