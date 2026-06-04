package dominio;

import enums.Tipopersona;

/**
 * Modela las entidades estatales o personas que actúan en calidad de Contratantes.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class Contratante extends Usuario {
    private String sector;
    private String nivelEntidad;
    private String codigoUnicoEntidad;

    public Contratante() {}

    public Contratante(Tipopersona tipoPersona, String tipoDocumento, String numeroDocumento, String nombre,
                       String correo, String contrasena, String telefono, String direccion, String ciudad,
                       String nivelEntidad, String sector, String codigoUnicoEntidad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contrasena, telefono, direccion, ciudad);
        this.nivelEntidad = nivelEntidad;
        this.sector = sector;
        this.codigoUnicoEntidad = codigoUnicoEntidad;
    }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public String getNivelEntidad() { return nivelEntidad; }
    public void setNivelEntidad(String nivelEntidad) { this.nivelEntidad = nivelEntidad; }
    public String getCodigoUnicoEntidad() { return codigoUnicoEntidad; }
    public void setCodigoUnicoEntidad(String codigoUnicoEntidad) { this.codigoUnicoEntidad = codigoUnicoEntidad; }
}
