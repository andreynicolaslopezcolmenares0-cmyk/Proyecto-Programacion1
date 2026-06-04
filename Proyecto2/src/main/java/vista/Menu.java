package vista;

import dominio.*;
import controlador.ControladorContrato;
import controlador.ControladorUsuario;
import enums.EstadoContrato;
import enums.Tipopersona;
import excepciones.*;

import javax.swing.JOptionPane;
import java.time.LocalDate;

/**
 * Clase principal que inicializa el sistema y renderiza los flujos de navegación
 * mediante cuadros de diálogo JOptionPane. Maneja excepciones globales y robustece el software
 * frente a entradas inesperadas, cancelaciones o valores nulos.
 * @author Andrés García, Camilo Ayala, Andrey López
 * @version 1.2
 */
public class Menu {
    /**
     * Punto de entrada principal (main) encargado del arranque de la aplicación.
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        ControladorUsuario ctrlUsuario = new ControladorUsuario();
        ControladorContrato ctrlContrato = new ControladorContrato();
        quemarDatosIniciales(ctrlUsuario);

        boolean salir = false;
        while (!salir) {
            String input = JOptionPane.showInputDialog(null,
                    "--- GESTIÓN DE CONTRATOS PÚBLICOS (SECOP II) ---\n" +
                            "1. Iniciar Sesión\n" +
                            "2. Salir\n\n" +
                            "Seleccione una opción:");

            if (input == null || input.equals("2")) {
                salir = true;
                break;
            }

            if (input.equals("1")) {
                try {
                    Usuario usuarioLogueado = iniciarSesion(ctrlUsuario);

                    if (usuarioLogueado != null) {
                        JOptionPane.showMessageDialog(null, "¡Bienvenido " + usuarioLogueado.getNombre() + "!");

                        if (usuarioLogueado instanceof Administrador) {
                            menuAdministrador(ctrlUsuario, (Administrador) usuarioLogueado);
                        } else if (usuarioLogueado instanceof Contratante) {
                            menuContratante(ctrlContrato, (Contratante) usuarioLogueado);
                        } else if (usuarioLogueado instanceof Contratista) {
                            menuContratista(ctrlContrato, (Contratista) usuarioLogueado);
                        }
                    }
                } catch (InvalidDataException | UserNotFoundException | AutenticacionException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Proceso", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error General", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Opción inválida del menú principal.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Interfaz de captura para el login del sistema con validación inmediata de nulidad y vacío.
     */
    private static Usuario iniciarSesion(ControladorUsuario ctrlUsuario) {
        String documento = JOptionPane.showInputDialog("Ingrese su número de documento:");
        if (documento == null) return null;
        if (documento.trim().isEmpty()) throw new InvalidDataException("El documento no puede estar vacío.");

        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");
        if (contrasena == null) return null;
        if (contrasena.trim().isEmpty()) throw new InvalidDataException("La contraseña no puede estar vacía.");

        Usuario u = ctrlUsuario.buscarPorDocumento(documento.trim());

        if (!u.getContrasena().equals(contrasena)) {
            throw new AutenticacionException("Acceso Denegado: La contraseña ingresada es incorrecta.");
        }

        return u;
    }

    /**
     * Flujo operacional para el Rol Administrador. Permite la gestión de usuarios.
     */
    private static void menuAdministrador(ControladorUsuario ctrlUsuario, Administrador adminLogueado) {
        int opcion = 0;
        do {
            String input = JOptionPane.showInputDialog(null,
                    "--- MENU ADMINISTRADOR ---\n" +
                            "1. Crear Contratante/Contratista\n" +
                            "2. Ver Todos los Usuarios\n" +
                            "3. Actualizar Usuario\n" +
                            "4. Eliminar Usuario\n" +
                            "5. Cerrar Sesión");

            if (input == null || input.equals("5")) break;
            try { opcion = Integer.parseInt(input); } catch (Exception e) { continue; }

            switch (opcion) {
                case 1:
                    try {
                        String tipoU = JOptionPane.showInputDialog("Tipo de usuario a crear:\n1. Contratante\n2. Contratista");
                        if (tipoU == null) break;

                        // CORRECCIÓN: Validación inmediata si pone '3' o cualquier valor inválido
                        if (!tipoU.equals("1") && !tipoU.equals("2")) {
                            throw new InvalidDataException("Opción inválida (" + tipoU + "). Debe seleccionar 1 (Contratante) o 2 (Contratista).");
                        }

                        // Ya no se pedirán estos campos si la opción inicial fue incorrecta
                        String doc = solicitarCampoObligatorio("Número de documento:");
                        String nom = solicitarCampoObligatorio("Nombre Completo:");
                        String correo = solicitarCampoObligatorio("Correo Electrónico:");
                        String pass = solicitarCampoObligatorio("Contraseña:");
                        String tel = solicitarCampoObligatorio("Teléfono:");
                        String dir = solicitarCampoObligatorio("Dirección:");
                        String ciu = solicitarCampoObligatorio("Ciudad:");

                        if (tipoU.equals("1")) {
                            String sector = solicitarCampoObligatorio("Sector institucional:");

                            // MEJORA: Lista desplegable obligatoria para evitar textos inválidos y cumplir con la rúbrica de validaciones
                            String[] opcionesNivel = {"Nacional", "Territorial"};
                            String nivel = (String) JOptionPane.showInputDialog(null,
                                    "Seleccione el nivel de la entidad:",
                                    "Nivel Entidad",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    opcionesNivel,
                                    opcionesNivel[0]);

                            if (nivel == null) {
                                throw new InvalidDataException("Operación cancelada: El nivel de la entidad es obligatorio.");
                            }

                            String codigo = solicitarCampoObligatorio("Código único de entidad:");

                            Contratante nuevoCt = new Contratante(Tipopersona.JURIDICO, "NIT", doc, nom, correo, pass, tel, dir, ciu, nivel, sector, codigo);
                            ctrlUsuario.registrarContratante(nuevoCt);
                            JOptionPane.showMessageDialog(null, "Contratante registrado exitosamente.");
                        } else if (tipoU.equals("2")) {
                            String area = solicitarCampoObligatorio("Área de Desempeño:");
                            int esPubInt = JOptionPane.showConfirmDialog(null, "¿Es una entidad pública?", "Tipo Contratista", JOptionPane.YES_NO_OPTION);
                            boolean esPub = (esPubInt == JOptionPane.YES_OPTION);

                            Contratista nuevoCs = new Contratista(Tipopersona.NATURAL, "CC", doc, nom, correo, pass, tel, dir, ciu, esPub, area);
                            ctrlUsuario.registrarContratista(nuevoCs);
                            JOptionPane.showMessageDialog(null, "Contratista registrado exitosamente.");
                        }
                    } catch (InvalidDataException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2:
                    StringBuilder listaU = new StringBuilder("--- LISTA DE USUARIOS REGISTRADOS ---\n");
                    for (Usuario u : ctrlUsuario.listarTodosLosUsuarios()) {
                        listaU.append("Doc: ").append(u.getNumeroDocumento()).append(" | ").append(u.getNombre())
                                .append(" (").append(u.getClass().getSimpleName()).append(")\n");
                    }
                    JOptionPane.showMessageDialog(null, listaU.toString());
                    break;

                case 3:
                    try {
                        String docAct = solicitarCampoObligatorio("Ingrese el documento del usuario a actualizar:");
                        Usuario uExistente = ctrlUsuario.buscarPorDocumento(docAct);

                        String nuevoNom = solicitarCampoObligatorio("Nuevo Nombre (Actual: " + uExistente.getNombre() + "):");
                        String nuevoCorreo = solicitarCampoObligatorio("Nuevo Correo:");
                        String nuevoPass = solicitarCampoObligatorio("Nueva Contraseña:");

                        uExistente.setNombre(nuevoNom);
                        uExistente.setCorreo(nuevoCorreo);
                        uExistente.setContrasena(nuevoPass);

                        if (uExistente instanceof Contratante) {
                            String nuevoSec = solicitarCampoObligatorio("Nuevo Sector:");
                            ((Contratante) uExistente).setSector(nuevoSec);
                        } else if (uExistente instanceof Contratista) {
                            String nuevaArea = solicitarCampoObligatorio("Nueva Área Desempeño:");
                            ((Contratista) uExistente).setAreaDesempeno(nuevaArea);
                        }

                        ctrlUsuario.actualizarUsuario(uExistente);
                        JOptionPane.showMessageDialog(null, "Usuario actualizado de manera correcta.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error en Actualización", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 4:
                    String docEliminar = JOptionPane.showInputDialog("Ingrese el documento del usuario a eliminar:");
                    if (docEliminar != null && !docEliminar.trim().isEmpty()) {
                        if (docEliminar.trim().equals(adminLogueado.getNumeroDocumento())) {
                            JOptionPane.showMessageDialog(null, "Acción Bloqueada: No puedes eliminarte a ti mismo mientras tienes la sesión activa.", "Error de Seguridad", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        try {
                            ctrlUsuario.eliminarUsuario(docEliminar.trim());
                            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente del sistema.");
                        } catch (UserNotFoundException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
            }
        } while (opcion != 5);
    }

    /**
     * Flujo operacional para el Rol Contratante. Permite configurar y procesar contratos.
     */
    private static void menuContratante(ControladorContrato ctrlContrato, Contratante contratanteLogueado) {
        int opcion = 0;
        do {
            String input = JOptionPane.showInputDialog(null,
                    "--- MENU CONTRATANTE ---\n" +
                            "1. Crear Contrato\n" +
                            "2. Listar Todos los Contratos\n" +
                            "3. Consultar Contrato por ID\n" +
                            "4. Actualizar Contrato\n" +
                            "5. Eliminar Contrato por ID\n" +
                            "6. Ver Reportes de Interventoría\n" +
                            "7. Cerrar Sesión");

            if (input == null || input.equals("7")) break;
            try { opcion = Integer.parseInt(input); } catch (Exception e) { continue; }

            switch (opcion) {
                case 1:
                    try {
                        String tipoC = JOptionPane.showInputDialog("Seleccione Tipo de Contrato:\n1. Prestación de Servicios\n2. Compraventa\n3. Obra Pública");
                        if (tipoC == null) break;

                        // CORRECCIÓN: Control estricto de opciones inválidas en la creación de contratos también
                        if (!tipoC.equals("1") && !tipoC.equals("2") && !tipoC.equals("3")) {
                            throw new InvalidDataException("Opción inválida (" + tipoC + "). Debe seleccionar 1, 2 o 3.");
                        }

                        String objeto = solicitarCampoObligatorio("Objeto del contrato:");
                        double valorTotal = solicitarDoubleObligatorio("Valor Total ($):");
                        int plazo = solicitarIntObligatorio("Plazo de ejecución (Días):");

                        Contratista contratistaAsignado = new Contratista();
                        contratistaAsignado.setNombre("Contratista Asignado Pendiente");

                        Contrato nuevoContrato = null;

                        if (tipoC.equals("1")) {
                            String perfil = solicitarCampoObligatorio("Perfil Requerido:");
                            String entregables = solicitarCampoObligatorio("Entregables:");
                            double honorario = solicitarDoubleObligatorio("Valor Honorario Mensual ($):");

                            nuevoContrato = new ContratoPrestacionServicios(objeto, LocalDate.now(), contratanteLogueado, contratistaAsignado, valorTotal, plazo, EstadoContrato.PUBLICACION, perfil, entregables, honorario);
                        } else if (tipoC.equals("2")) {
                            String item = solicitarCampoObligatorio("Ítem a adquirir:");
                            String marca = solicitarCampoObligatorio("Marca:");
                            String modelo = solicitarCampoObligatorio("Modelo:");
                            String serie = solicitarCampoObligatorio("Serie:");
                            double valUni = solicitarDoubleObligatorio("Valor Unitario ($):");
                            int cant = solicitarIntObligatorio("Cantidad a adquirir:");

                            nuevoContrato = new ContratoCompraventa(objeto, LocalDate.now(), contratanteLogueado, contratistaAsignado, valorTotal, plazo, EstadoContrato.PUBLICACION, item, marca, modelo, serie, valUni, cant);
                        } else if (tipoC.equals("3")) {
                            String ubi = solicitarCampoObligatorio("Ubicación de la obra:");
                            double area = solicitarDoubleObligatorio("Área de intervención (m2):");

                            nuevoContrato = new ContratoObraPublica(objeto, LocalDate.now(), contratanteLogueado, contratistaAsignado, valorTotal, plazo, EstadoContrato.PUBLICACION, ubi, area);
                        }

                        if (nuevoContrato != null) {
                            ctrlContrato.crearContrato(nuevoContrato);
                            JOptionPane.showMessageDialog(null, "Contrato estructurado y validado con ID: " + nuevoContrato.getIdContrato());
                        }
                    } catch (ContractValidationException | InvalidDataException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Validación Fallida", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2:
                    StringBuilder lista = new StringBuilder("--- CONTRATOS REGISTRADOS ---\n");
                    for (Contrato c : ctrlContrato.listarContratos()) {
                        lista.append("ID: ").append(c.getIdContrato()).append(" | OBJETO: ").append(c.getObjetoContrato()).append(" (").append(c.getEstado()).append(")\n");
                    }
                    JOptionPane.showMessageDialog(null, lista.toString());
                    break;

                case 3:
                    String idBuscar = JOptionPane.showInputDialog("Ingrese el ID del contrato:");
                    if (idBuscar != null && !idBuscar.trim().isEmpty()) {
                        try {
                            Contrato c = ctrlContrato.consultarContratoPorId(idBuscar);
                            JOptionPane.showMessageDialog(null, "CONTRATO ENCONTRADO:\n" +
                                    "ID: " + c.getIdContrato() + "\n" +
                                    "Objeto: " + c.getObjetoContrato() + "\n" +
                                    "Estado Actual: " + c.getEstado() + "\n" +
                                    "Plazo: " + c.getPlazoEjecucion() + " días\n" +
                                    "Valor Total: $" + c.getValorTotal());
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case 4:
                    try {
                        String idAct = solicitarCampoObligatorio("ID del contrato a actualizar:");
                        Contrato cExistente = ctrlContrato.consultarContratoPorId(idAct);

                        String nuevoObj = solicitarCampoObligatorio("Nuevo Objeto (Actual: " + cExistente.getObjetoContrato() + "):");
                        double nuevoVal = solicitarDoubleObligatorio("Nuevo Valor Total:");
                        int nuevoPlazo = solicitarIntObligatorio("Nuevo Plazo (Días):");

                        cExistente.setObjetoContrato(nuevoObj);
                        cExistente.setValorTotal(nuevoVal);
                        cExistente.setPlazoEjecucion(nuevoPlazo);

                        if (cExistente instanceof ContratoCompraventa) {
                            double nValUni = solicitarDoubleObligatorio("Nuevo Valor Unitario:");
                            int nCant = solicitarIntObligatorio("Nueva Cantidad:");
                            ((ContratoCompraventa) cExistente).setValorUnitario(nValUni);
                            ((ContratoCompraventa) cExistente).setCantidadAdquirir(nCant);
                        } else if (cExistente instanceof ContratoPrestacionServicios) {
                            double nHon = solicitarDoubleObligatorio("Nuevo Honorario Mensual:");
                            ((ContratoPrestacionServicios) cExistente).setValorHonorarioMensual(nHon);
                        }

                        ctrlContrato.actualizarContrato(cExistente);
                        JOptionPane.showMessageDialog(null, "Contrato modificado y validado con éxito.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error en Edición", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 5:
                    String idEliminar = JOptionPane.showInputDialog("Ingrese el ID del contrato a eliminar:");
                    if (idEliminar != null && !idEliminar.trim().isEmpty()) {
                        try {
                            ctrlContrato.eliminarContrato(idEliminar);
                            JOptionPane.showMessageDialog(null, "Contrato eliminado satisfactoriamente.");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case 6:
                    StringBuilder reportesStr = new StringBuilder("--- HISTORIAL DE REPORTES DE INTERVENTORÍA ---\n");
                    for (ReporteInterventoria r : ctrlContrato.listarReportes()) {
                        reportesStr.append("Contrato ID: ").append(r.getContratoModificado().getIdContrato())
                                .append(" | Fecha/Hora: ").append(r.getFechaHora())
                                .append("\nJustificación: ").append(r.getInformeJustificacion()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null, reportesStr.toString());
                    break;
            }
        } while (opcion != 7);
    }

    /**
     * Flujo operacional para el Rol Contratista. Permite cambiar fases contractuales y emitir reportes.
     */
    private static void menuContratista(ControladorContrato ctrlContrato, Contratista contratista) {
        int opcion = 0;
        do {
            String input = JOptionPane.showInputDialog(null,
                    "--- MENU CONTRATISTA ---\n" +
                            "1. Ver Contratos Disponibles\n" +
                            "2. Cambiar Estado de Contrato (Generar Reporte de Interventoría)\n" +
                            "3. Cerrar Sesión");

            if (input == null || input.equals("3")) break;
            try { opcion = Integer.parseInt(input); } catch (Exception e) { continue; }

            switch (opcion) {
                case 1:
                    StringBuilder lista = new StringBuilder("--- CONTRATOS ---\n");
                    for (Contrato c : ctrlContrato.listarContratos()) {
                        lista.append("ID: ").append(c.getIdContrato()).append(" - ").append(c.getObjetoContrato()).append(" (").append(c.getEstado()).append(")\n");
                    }
                    JOptionPane.showMessageDialog(null, lista.toString());
                    break;

                case 2:
                    try {
                        String idStr = solicitarCampoObligatorio("Ingrese el ID del contrato a gestionar:");
                        Contrato c = ctrlContrato.consultarContratoPorId(idStr);

                        if(c.getContratista().getNombre().contains("Pendiente")) {
                            c.setContratista(contratista);
                        }

                        String estadoStr = JOptionPane.showInputDialog("Estado actual del contrato: " + c.getEstado() +
                                "\nSeleccione el nuevo estado:\n1. LICITACION\n2. ADJUDICACION\n3. EJECUCION\n4. FINALIZADO");
                        if (estadoStr == null) break;

                        EstadoContrato nuevoEstado = null;
                        switch (estadoStr.trim()) {
                            case "1": nuevoEstado = EstadoContrato.LICITACION; break;
                            case "2": nuevoEstado = EstadoContrato.ADJUDICACION; break;
                            case "3": nuevoEstado = EstadoContrato.EJECUCION; break;
                            case "4": nuevoEstado = EstadoContrato.FINALIZADO; break;
                            default: throw new InvalidDataException("Selección de estado no válida.");
                        }

                        String justificacion = solicitarCampoObligatorio("Ingrese el informe de justificación (Reporte de Interventoría):");

                        ctrlContrato.cambiarEstadoContrato(idStr, nuevoEstado, justificacion);
                        JOptionPane.showMessageDialog(null, "Transición completada y Reporte de Interventoría archivado.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error en Proceso", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (opcion != 3);
    }

    /**
     * Captura de texto forzada. Si el usuario presiona "Cancelar" o deja el campo en blanco,
     * interrumpe el flujo arrojando una excepción controlada.
     */
    private static String solicitarCampoObligatorio(String mensaje) {
        String resultado = JOptionPane.showInputDialog(mensaje);
        if (resultado == null || resultado.trim().isEmpty()) {
            throw new InvalidDataException("Error crítico: El campo '" + mensaje.replace(":", "") + "' es obligatorio.");
        }
        return resultado.trim();
    }

    /**
     * Solicita y valida el ingreso seguro de un número decimal mayor o igual a cero.
     */
    private static double solicitarDoubleObligatorio(String mensaje) {
        String res = solicitarCampoObligatorio(mensaje);
        try {
            double num = Double.parseDouble(res);
            if (num < 0) throw new InvalidDataException("El valor numérico no puede ser negativo.");
            return num;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("El formato ingresado para '" + mensaje.replace(":", "") + "' debe ser un número válido.");
        }
    }

    /**
     * Solicita y valida el ingreso seguro de un entero mayor a cero.
     */
    private static int solicitarIntObligatorio(String mensaje) {
        String res = solicitarCampoObligatorio(mensaje);
        try {
            int num = Integer.parseInt(res);
            if (num <= 0) throw new InvalidDataException("El valor entero debe ser estrictamente mayor a cero.");
            return num;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("El formato ingresado para '" + mensaje.replace(":", "") + "' debe ser un número entero válido.");
        }
    }

    /**
     * Inicializa registros de prueba por defecto para acelerar el testing funcional.
     */
    private static void quemarDatosIniciales(ControladorUsuario ctrlUsuario) {
        Administrador admin = new Administrador(Tipopersona.NATURAL, "CC", "123", "Andres Admin", "admin@uptc.edu.co", "admin123", "300123", "Calle 1", "Duitama");
        Contratante contratante = new Contratante(Tipopersona.JURIDICO, "NIT", "456", "Gobernación", "gob@gov.co", "contra123", "301456", "Cra 1", "Tunja", "Nacional", "Público", "001");
        Contratista contratista = new Contratista(Tipopersona.NATURAL, "CC", "789", "Ingeniero Civil", "ing@mail.com", "obra123", "302789", "Calle 2", "Sogamoso", false, "Obras");

        ctrlUsuario.registrarAdministrador(admin);
        ctrlUsuario.registrarContratante(contratante);
        ctrlUsuario.registrarContratista(contratista);
    }
}
