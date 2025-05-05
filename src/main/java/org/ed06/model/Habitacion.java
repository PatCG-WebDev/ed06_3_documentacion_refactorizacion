package org.ed06.model;

/**
 * Clase que representa una habitación en el hotel.
 * Cada habitación tiene un número, un tipo (por ejemplo, SIMPLE, DOBLE, SUITE)
 * y un precio base asociado.
 *
 * <p>La clase permite gestionar la disponibilidad de la habitación y
 * reservarla si está disponible.</p>
 *
 * @author Patricia Cid González
 */
public class Habitacion {
    private final int numero;
    private final String tipo;
    private final double precioBase;

    // TODO: cambiar la forma de gestionar la disponibilidad en base a las fechas de las reservas
    private boolean disponible; // Indica si la habitación está disponible para reservar

    /**
     * Crea una nueva instancia de la clase Habitacion.
     *
     * @param numero      Número de la habitación.
     * @param tipo        Tipo de la habitación (por ejemplo, SIMPLE, DOBLE, SUITE).
     * @param precioBase  Precio base de la habitación.
     * El parámetro disponible no es necesario pasarlo al constructor, ya que la habitación se inicia siempre como disponible.
     */
    public Habitacion(int numero, String tipo, double precioBase) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.disponible = true;
    }


    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public boolean isDisponible() {
        return disponible;
    }


     /**
     * Reserva la habitación si está disponible.
     * Si ya está reservada, no realiza la acción.
     */
    public void reservar() {
        if (disponible) {
            System.out.println("Habitación #" + numero + " ya reservada");
        }
        disponible = true;
    }
}
