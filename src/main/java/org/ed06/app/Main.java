package org.ed06.app;

import org.ed06.model.Cliente;
import org.ed06.model.Habitacion;
import org.ed06.model.Hotel;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Clase principal que contiene el menú de gestión de un hotel.
 * Permite registrar habitaciones, registrar clientes, listar habitaciones disponibles,
 * listar clientes, listar reservas y realizar reservas.
 *
 * <p>Se utiliza un menú de opciones gestionado mediante un bucle que permite
 * al usuario interactuar con el sistema a través de la consola. Esta clase simula la gestión
 * de un hotel, permitiendo realizar operaciones de administración de reservas y clientes.</p>
 *
 * @author Patricia Cid González
 */
public class Main {
    static final Scanner scanner = new Scanner(System.in);
    // Definimos constantes para las diferentes opciones del menú
    private static final int REGISTRAR_HABITACION = 1;
    private static final int LISTAR_HABITACIONES_DISPONIBLES = 2;
    private static final int RESERVAR_HABITACION = 11;
    private static final int LISTAR_RESERVAS = 12;
    private static final int LISTAR_CLIENTES = 21;
    private static final int REGISTRAR_CLIENTE = 22;
    private static final int SALIR = 0;

    /**
     * Método principal que inicia la aplicación del sistema de gestión del hotel.
     * Registra algunos datos de prueba (habitaciones y clientes), muestra un menú interactivo
     * y gestiona las acciones del usuario según la opción seleccionada.
     * El sistema simula un entorno de gestión de reservas de un hotel, permitiendo realizar
     * operaciones como registrar habitaciones, registrar clientes y realizar reservas.
     *
     *  @param args Parámetros de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        String tipo;

        Hotel hotel = new Hotel("El mirador", "Calle Entornos de Desarrollo 6", "123456789");

        // Registramos algunas habitaciones
        hotel.registrarHabitacion("SIMPLE", 50);
        hotel.registrarHabitacion("DOBLE", 80);
        hotel.registrarHabitacion("SUITE", 120);
        hotel.registrarHabitacion("LITERAS", 200);
        hotel.registrarHabitacion("SIMPLE", 65);
        hotel.registrarHabitacion("DOBLE", 100);
        hotel.registrarHabitacion("SUITE", 150);
        hotel.registrarHabitacion("LITERAS", 250);

        // Registramos algunos clientes
        hotel.registrarCliente("Daniel", "daniel@daniel.com", "12345678A", true);
        hotel.registrarCliente("Adrián", "adrian@adrian.es", "87654321B", false);

        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case REGISTRAR_HABITACION:
                    System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
                    tipo = scanner.nextLine();
                    System.out.println("Introduce el precio base de la habitación: ");
                    double precioBase = scanner.nextDouble();
                    scanner.nextLine();
                    hotel.registrarHabitacion(tipo, precioBase);
                    System.out.println("Habitación registrada: " + tipo + " - Precio base: " + precioBase);
                    break;
                case LISTAR_HABITACIONES_DISPONIBLES:
                    hotel.listarHabitacionesDisponibles();
                    break;
                case RESERVAR_HABITACION:
                    System.out.println("Introduce el id del cliente: ");
                    int clienteId = scanner.nextInt();
                    System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
                    tipo = scanner.next();
                    System.out.println("Introduce la fecha de entrada (año): ");
                    int anioEntrada = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Introduce la fecha de entrada (mes): ");
                    int mesEntrada = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Introduce la fecha de entrada (día): ");
                    int diaEntrada = scanner.nextInt();
                    scanner.nextLine();
                    LocalDate fechaEntrada = LocalDate.of(anioEntrada, mesEntrada, diaEntrada);
                    System.out.println("Introduce la fecha de salida (año): ");
                    int anioSalida = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Introduce la fecha de salida (mes): ");
                    int mesSalida = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Introduce la fecha de salida (día): ");
                    int diaSalida = scanner.nextInt();
                    scanner.nextLine();
                    LocalDate fechaSalida = LocalDate.of(anioSalida, mesSalida, diaSalida);
                    int numeroHabitacion = hotel.reservarHabitacion(clienteId, tipo, fechaEntrada,
                        fechaSalida);
                    System.out.println("Datos de la habitacion");
                    Habitacion habitacion = hotel.getHabitacion(numeroHabitacion);
                    System.out.println(
                        "Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo()
                            + " - Precio base: " + habitacion.getPrecioBase());
                    System.out.println("Número de habitación reservada: " + numeroHabitacion);
                    break;
                case LISTAR_RESERVAS:
                    hotel.listarReservas();
                    break;
                case LISTAR_CLIENTES:
                    hotel.listarClientes();
                    break;
                case REGISTRAR_CLIENTE:
                    String nombre;
                    String email;
                    String dni;

                    while(true) {
                        try {
                            System.out.println("Introduce el nombre del cliente: ");
                            nombre = scanner.next();
                            Cliente.validarNombre(nombre);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Nombre no válido. Inténtalo de nuevo.");
                        }
                    }
                    while (true) {
                        try {
                            System.out.println("Introduce el email del cliente: ");
                            email = scanner.next();
                            Cliente.validarEmail(email);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Email no válido. Inténtalo de nuevo.");
                        }
                    }
                    while (true) {
                        try {
                            System.out.println("Introduce el DNI del cliente: ");
                            dni = scanner.next();
                            Cliente.validarDni(dni);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("DNI no válido. Inténtalo de nuevo.");
                        }
                    }
                    System.out.println("¿Es VIP? (true/false): ");
                    boolean esVip = scanner.nextBoolean();
                    hotel.registrarCliente(nombre, email, dni, esVip);
                    break;
                case SALIR:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    /**
     * Muestra por consola el menú principal de opciones para la gestión del hotel.
     * Las opciones incluyen registrar habitaciones y clientes, listar datos y realizar reservas.
     */
    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Registrar habitación");
        System.out.println("2. Listar habitaciones disponibles");
        System.out.println("11. Reservar habitación");
        System.out.println("12. Listar reservas");
        System.out.println("21. Listar clientes");
        System.out.println("22. Registrar cliente");
        System.out.println("0. Salir");
    }
}