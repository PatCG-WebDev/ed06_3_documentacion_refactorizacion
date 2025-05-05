package org.ed06.model;

import java.time.LocalDate;

/**
 * Clase que representa una reserva en el hotel.
 * La clase gestiona los detalles de la habitación reservada,
 * el cliente que hace la reserva y las fechas de la estancia.
 * Además, calcula el precio final de la reserva aplicando descuentos
 * según el tipo de cliente (VIP) y la duración de la estancia.
 *
 * @author Patricia Cid González
 */
public class Reserva {

    public static final double DESCUENTO_VIP = 0.9;       // Descuento aplicado a los clientes VIP
    public static final double DESCUENTO_ESTANCIA_LARGA = 0.95;  // Descuento aplicado a las estancias largas (por ejemplo, más de 7 días)
    private final int id;
    private final Habitacion habitacion;
    private final Cliente cliente;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final double precioTotal;


    /**
     * Crea una nueva instancia de Reserva.
     *
     * @param id           ID único de la reserva.
     * @param habitacion   Habitación reservada.
     * @param cliente      Cliente que hace la reserva.
     * @param fechaInicio  Fecha de inicio de la reserva.
     * @param fechaFin     Fecha de fin de la reserva.
     */
    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }


    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }


    /**
     * Calcula el precio total de la reserva. El precio base se multiplica por el número de días
     * de la estancia, y luego se aplican los descuentos si el cliente es VIP o si la estancia
     * es más larga que 7 días.
     *
     * @return El precio total de la reserva.
     */
    public double calcularPrecioFinal() {

        int diasReserva = fechaFin.getDayOfYear() - fechaInicio.getDayOfYear();

        double precioFinal = habitacion.getPrecioBase() * diasReserva;

        if (cliente.esVip) {
            precioFinal *= DESCUENTO_VIP;
        }

        if (diasReserva > 7) {
            precioFinal *= DESCUENTO_ESTANCIA_LARGA;
        }

        return precioFinal;
    }

    /**
     * Muestra la información completa de la reserva en consola.
     * Incluye detalles de la habitación, el cliente, las fechas y el precio total.
     */
    public void mostrarReserva() {
        System.out.println("Reserva #" + id);
        System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
        System.out.println("Cliente: " + cliente.nombre);
        System.out.println("Fecha de inicio: " + fechaInicio.toString());
        System.out.println("Fecha de fin: " + fechaFin.toString());
        System.out.printf("Precio total: %.2f €\n", precioTotal);
    }

}
