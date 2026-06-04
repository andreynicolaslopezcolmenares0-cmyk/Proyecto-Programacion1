package dominio;

import enums.Tipopersona;

/**
 * Clase abstracta base que encapsula los atributos comunes a todos los usuarios del sistema.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public abstract class Usuario {
    private Tipopersona tipoPersona;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private String direccion;
    private String ciudad;

    public Usuario() {}

    public Usuario(Tipopersona tipoPersona, String tipoDocumento, String numeroDocumento, String nombre,
                   String correo, String contrasena, String telefono, String direccion, String ciudad) {
        this.tipoPersona = tipoPersona;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    // Getters y Setters
    public Tipopersona getTipoPersona() { return tipoPersona; }
    public void setTipoPersona(Tipopersona tipoPersona) { this.tipoPersona = tipoPersona; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
}
