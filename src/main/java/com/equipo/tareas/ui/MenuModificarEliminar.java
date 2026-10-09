package com.equipo.tareas.ui;

import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;
import com.equipo.tareas.service.EliminarTareaService;
import com.equipo.tareas.service.ModificarTareaService;

import java.util.Scanner;

/**
 * Menú de modificación y eliminación.
 * Conectar aquí el servicio de modificar/eliminar (constructor con TareaRepository).
 */
public class MenuModificarEliminar {

    private final Scanner scanner;
    private final TareaRepository repository;
    private final ModificarTareaService modificarTareaService;
    private final EliminarTareaService eliminarTareaService;

    public MenuModificarEliminar(Scanner scanner, TareaRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
        this.modificarTareaService = new ModificarTareaService(repository);
        this.eliminarTareaService = new EliminarTareaService(repository);
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- Modificar y eliminar ---");
            System.out.println("1) Modificar tarea");
            System.out.println("2) Eliminar tarea");
            System.out.println("0) Volver");
            opcion = Menu.leerEntero(scanner, "Opción: ");
            switch (opcion) {
                case 1 -> modificarTarea();
                case 2 -> eliminarTarea();
                case 0 -> { }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void modificarTarea() {
        try {
            int id = Menu.leerEntero(scanner, "Id de la tarea: ");
            Tarea actual = repository.buscarPorId(repository.cargar(), id)
                    .orElse(null);
            if (actual == null) {
                System.out.println("No existe una tarea con id " + id + ".");
                return;
            }

            System.out.println("Tarea actual: " + actual);
            System.out.print("Nuevo título (Enter para conservar): ");
            String tituloIngresado = scanner.nextLine();
            String nuevoTitulo = tituloIngresado.isEmpty() ? null : tituloIngresado;

            System.out.print("Nueva descripción (Enter para conservar): ");
            String descripcionIngresada = scanner.nextLine();
            String nuevaDescripcion = descripcionIngresada.isEmpty() ? null : descripcionIngresada;

            if (nuevoTitulo == null && nuevaDescripcion == null) {
                System.out.println("No se realizaron cambios.");
                return;
            }

            Tarea actualizada = modificarTareaService.modificar(id, nuevoTitulo, nuevaDescripcion);
            System.out.println("Tarea actualizada: " + actualizada);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo modificar: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al modificar la tarea: " + e.getMessage());
        }
    }

    private void eliminarTarea() {
        try {
            int id = Menu.leerEntero(scanner, "Id de la tarea: ");
            Tarea actual = repository.buscarPorId(repository.cargar(), id)
                    .orElse(null);
            if (actual == null) {
                System.out.println("No existe una tarea con id " + id + ".");
                return;
            }

            System.out.println("Se eliminará: " + actual);
            if (!confirmar("¿Confirmar eliminación? (s/n): ")) {
                System.out.println("Eliminación cancelada.");
                return;
            }

            Tarea eliminada = eliminarTareaService.eliminar(id);
            System.out.println("Tarea eliminada: " + eliminada);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo eliminar: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al eliminar la tarea: " + e.getMessage());
        }
    }

    private boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim();
            if (respuesta.equalsIgnoreCase("s")) {
                return true;
            }
            if (respuesta.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Ingrese s o n.");
        }
    }
}
