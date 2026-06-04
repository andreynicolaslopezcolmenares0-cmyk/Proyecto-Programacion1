package dominio;

import enums.Tipopersona;

/**
 * Modela los perfiles de Contratistas registrados (Entidades Públicas o Privadas/Naturales).
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class Contratista extends Usuario {
    private boolean esEntidadPublica;
    private String areaDesempeno;

    public Contratista() {}

    public Contratista(Tipopersona tipoPersona, String tipoDocumento, String numeroDocumento, String nombre,
                       String correo, String contrasena, String telefono, String direccion, String ciudad,
                       boolean esEntidadPublica, String areaDesempeno) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contrasena, telefono, direccion, ciudad);
        this.esEntidadPublica = esEntidadPublica;
        this.areaDesempeno = areaDesempeno;
    }

    public boolean isEsEntidadPublica() { return esEntidadPublica; }
    public void setEsEntidadPublica(boolean esEntidadPublica) { this.esEntidadPublica = esEntidadPublica; }
    public String getAreaDesempeno() { return areaDesempeno; }
    public void setAreaDesempeno(String areaDesempeno) { this.areaDesempeno = areaDesempeno; }
}
