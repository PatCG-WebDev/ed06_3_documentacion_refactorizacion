package org.ed06.model;

import java.time.LocalDate;
import java.util.*;
/**
 * Clase que representa un hotel con capacidad para gestionar habitaciones,
 * clientes y reservas. Permite registrar nuevas habitaciones y clientes,
 * realizar reservas y listar datos relevantes.
 *
 * <p>Utiliza estructuras de datos como {@link Map} y {@link List} para organizar
 * internamente los clientes, habitaciones y reservas, proporcionando una interfaz
 * simple para el manejo de estos elementos.</p>
 *
 * @author Patricia Cid González
 */

public class Hotel {
    private final String nombre;
    private final String direccion;
    private final String telefono;

    private final Map<Integer,Cliente> clientes = new HashMap<>();
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Map<Integer,List<Reserva>> reservasPorHabitacion = new HashMap<>();

    private static final int ERROR_NO_HABITACIONES = -4;
    private static final int ERROR_CLIENTE_NO_EXISTE = -3;
    private static final int ERROR_FECHAS_INVALIDAS = -2;
    private static final int ERROR_TIPO_NO_DISPONIBLE = -1;

    /**
     * Crea una nueva instancia del hotel.
     *
     * @param nombre    Nombre del hotel.
     * @param direccion Dirección del hotel.
     * @param telefono  Teléfono de contacto.
     */
    public Hotel(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    /**
     * Registra una nueva habitación en el hotel, añadiéndola a la lista de habitaciones.
     *
     * @param tipo       Tipo de habitación (por ejemplo, SIMPLE, DOBLE, SUITE).
     * @param precioBase Precio base de la habitación.
     */

    public void registrarHabitacion(String tipo, double precioBase) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
    }

    /**
     * Muestra por consola las habitaciones disponibles (no reservadas).
     */
    public void listarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.isDisponible()) {
                System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
            }
        }
    }

    /**
     * Devuelve una habitación a partir de su número.
     *
     * @param numero Número de habitación.
     * @return La habitación correspondiente o {@code null} si no se encuentra.
     */
    public Habitacion getHabitacion(int numero) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }

    /**
     * Realiza una reserva para un cliente, si hay habitaciones del tipo solicitado disponibles
     * y las fechas son válidas. Si todo es correcto, realiza la reserva y marca la habitación
     * como ocupada.
     *
     * @param clienteId    ID del cliente que desea reservar.
     * @param tipo         Tipo de habitación deseado.
     * @param fechaEntrada Fecha de entrada.
     * @param fechaSalida  Fecha de salida.
     * @return Número de habitación reservada o un código de error si falla:
     * <ul>
     *     <li>-1: No hay habitaciones del tipo solicitado.</li>
     *     <li>-2: Las fechas no son válidas.</li>
     *     <li>-3: El cliente no existe.</li>
     *     <li>-4: No hay habitaciones registradas en el hotel.</li>
     * </ul>
     */
    public int reservarHabitacion(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {

        if(!hayHabitaciones())  return ERROR_NO_HABITACIONES;

        Cliente cliente = getCliente(clienteId);
        if (cliente == null) return ERROR_CLIENTE_NO_EXISTE;

        if (!fechaEntrada.isBefore(fechaSalida)) {
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return ERROR_FECHAS_INVALIDAS;
        }

        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getTipo().equals(tipo.toUpperCase()) && habitacion.isDisponible()) {
                esVip(cliente);
                Reserva reserva = new Reserva(reservasPorHabitacion.size() + 1, habitacion, cliente, fechaEntrada, fechaSalida);
                reservasPorHabitacion.get(habitacion.getNumero()).add(reserva);
                habitacion.reservar();
                System.out.println("Reserva realizada con éxito");
                return habitacion.getNumero();
            }
        }
        System.out.println("No hay habitaciones disponibles del tipo " + tipo);
        return ERROR_TIPO_NO_DISPONIBLE;
    }

    /**
     * Comprueba si hay habitaciones registradas en el hotel.
     *
     * @return {@code true} si hay habitaciones, {@code false} en caso contrario.
     */
    private boolean hayHabitaciones() {
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones en el hotel");
            return false;
        }
        return true;
    }

    /**
     * Comprueba si un cliente debe ser ascendido a VIP según el número de reservas
     * en el último año. Si cumple los requisitos, se actualiza su estado.
     *
     * @param cliente Cliente a comprobar.
     */
    private void esVip(Cliente cliente) {
        int numReservas = contarReservasUltimoAnio(cliente);

        if(numReservas > 3 && !cliente.esVip) {
            cliente.esVip = true;
            System.out.println("El cliente " + cliente.nombre + " ha pasado a ser VIP");
        }
    }

    /**
     * Obtiene un cliente a partir de su ID.
     *
     * @param clienteId ID del cliente.
     * @return Cliente correspondiente o {@code null} si no existe.
     */
    private Cliente getCliente(int clienteId) {
        Cliente cliente = clientes.get(clienteId);
        if (cliente == null) {
            System.out.println("No existe el cliente con id " + clienteId);
            return null;
        }
        return cliente;
    }


    /**
     * Cuenta las reservas realizadas por un cliente en el último año.
     *
     * @param cliente Cliente del que se quieren contar las reservas.
     * @return Número de reservas realizadas en el último año.
     */
    private int contarReservasUltimoAnio(Cliente cliente){
        int numReservas = 0;

        for (List<Reserva> reservasHabitacion : reservasPorHabitacion.values()) {
            for (Reserva reservaCliente : reservasHabitacion) {
                if(reservaCliente.getCliente().equals(cliente)) {
                    if(reservaCliente.getFechaInicio().isAfter(LocalDate.now().minusYears(1))) {
                        numReservas++;
                    }
                }
            }
        }
        return numReservas;

    }

    /**
     * Lista todas las reservas agrupadas por habitación, mostrando la información por consola.
     */
    public void listarReservas() {
        reservasPorHabitacion.forEach((key, value) -> {
            System.out.println("Habitación #" + key);
            value.forEach(Reserva::mostrarReserva);
        });
    }

    /**
     * Lista todos los clientes registrados, mostrando su información por consola.
     */
    public void listarClientes() {
        for(Cliente cliente : clientes.values()) {
            System.out.println("Cliente #" + cliente.id + " - Nombre: " + cliente.nombre + " - DNI: " + cliente.dni + " - VIP: " + cliente.esVip);
        }
    }

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * @param nombre Nombre del cliente.
     * @param email  Email del cliente.
     * @param dni    DNI del cliente.
     * @param esVip  Indica si el cliente es VIP al registrarse.
     */
    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.id, cliente);
    }
}
