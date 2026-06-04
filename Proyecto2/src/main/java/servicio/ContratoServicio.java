package servicio;

import dominio.Contrato;
import dominio.ReporteInterventoria;
import excepciones.ContractNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lógica interna persistente en memoria para el control de contratos públicos y bitácoras.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class ContratoServicio {
    private final List<Contrato> baseContratos = new ArrayList<>();
    private final List<ReporteInterventoria> historicoReportes = new ArrayList<>();

    public void crearContrato(Contrato c) {
        baseContratos.add(c);
    }

    public List<Contrato> obtenerTodos() {
        return baseContratos;
    }

    public Contrato buscarPorId(String id) {
        return baseContratos.stream()
                .filter(c -> c.getIdContrato().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new ContractNotFoundException("Contrato con ID " + id + " no existe en SECOP II."));
    }

    public void actualizarContrato(Contrato cActualizado) {
        cActualizado.validarReglasNegocio();
        Contrato c = buscarPorId(cActualizado.getIdContrato());
        int index = baseContratos.indexOf(c);
        baseContratos.set(index, cActualizado);
    }

    public void eliminarContrato(String id) {
        Contrato c = buscarPorId(id);
        baseContratos.remove(c);
    }

    public void agregarReporte(ReporteInterventoria r) {
        historicoReportes.add(r);
    }

    public List<ReporteInterventoria> obtenerTodosLosReportes() {
        return historicoReportes;
    }
}
