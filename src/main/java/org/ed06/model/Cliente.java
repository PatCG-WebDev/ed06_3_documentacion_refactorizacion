package org.ed06.model;

/**
 * Representa un cliente del hotel.
 * Cada cliente tiene un identificador único, nombre, DNI, email y estado VIP.
 * La clase incluye validaciones para asegurar que los atributos sean correctos
 * (como el formato del DNI, nombre y email).
 *
 * @author Patricia Cid González
 */
public class Cliente {
    public final int id;
    public final String nombre;
    public final String dni;
    public final String email;
    public boolean esVip;

    /**
     * Crea un nuevo cliente con los datos indicados.
     *
     * @param id      identificador único del cliente
     * @param nombre  nombre del cliente (mínimo 3 caracteres)
     * @param dni     DNI del cliente (8 dígitos seguidos de una letra mayúscula)
     * @param email   correo electrónico del cliente
     * @param esVip   indica si el cliente es VIP
     * @throws IllegalArgumentException si alguno de los datos no es válido
     */
    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        validarNombre(nombre);
        this.nombre = nombre;

        validarDni(dni);
        this.dni = dni;

        validarEmail(email);
        this.email = email;

        this.esVip = esVip;
    }

    /**
     * Valida que el nombre no sea nulo, vacío ni demasiado corto.
     *
     * @param nombre nombre a validar
     * @throws IllegalArgumentException si el nombre no es válido
     */
    public static void validarNombre(String nombre) throws IllegalArgumentException{
        if (nombre == null || nombre.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
    }

    /**
     * Valida que el email tenga un formato correcto. Por ejemplo: ejemplo@dominio.com.
     *
     * @param email email a validar
     * @throws IllegalArgumentException si el email no es válido
     */
    public static void validarEmail(String email) throws IllegalArgumentException{
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("El email no es válido");
        }
    }

    /**
     * Valida que el DNI tenga el formato correcto (8 dígitos y una letra mayúscula).
     *
     * @param dni DNI a validar
     * @throws IllegalArgumentException si el DNI no es válido
     */
    public static void validarDni(String dni) throws IllegalArgumentException{
        if (!dni.matches("[0-9]{8}[A-Z]")) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
    }

}