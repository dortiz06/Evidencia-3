package com.equipo.tareas.ui;

import com.equipo.tareas.repository.TareaRepository;

import java.util.Scanner;

/**
 * Menú de modificación y eliminación.
 * Conectar aquí el servicio de modificar/eliminar (constructor con TareaRepository).
 */
public class MenuModificarEliminar {

    private final Scanner scanner;
    private final TareaRepository repository;

    public MenuModificarEliminar(Scanner scanner, TareaRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
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
                case 1 -> pendiente("modificar tarea");
                case 2 -> pendiente("eliminar tarea");
                case 0 -> { }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void pendiente(String accion) {
        System.out.println("Pendiente de implementar en com.equipo.tareas.service: " + accion + ".");
        System.out.println("(El repositorio ya está disponible: " + repository.getClass().getSimpleName() + ")");
    }
}
