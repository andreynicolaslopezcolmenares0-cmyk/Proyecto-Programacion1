package dominio;

import enums.Tipopersona;

/**
 * Modela el usuario Administrador del sistema con privilegios sobre la gestión de usuarios.
 * @author Andrés García, Camilo Ayala, Andrey López
 */
public class Administrador extends Usuario {
    public Administrador(Tipopersona tipoPersona, String tipoDocumento, String numeroDocumento, String nombre,
                         String correo, String contrasena, String telefono, String direccion, String ciudad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contrasena, telefono, direccion, ciudad);
    }

}
