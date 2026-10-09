package com.equipo.tareas.ui;

import com.equipo.tareas.repository.TareaRepository;

import java.util.Scanner;

public class MenuAsignacionEstados {

    private final Scanner scanner;
    private final TareaRepository repository;

    public MenuAsignacionEstados(Scanner scanner, TareaRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- Asignación, estados y prioridades ---");
            System.out.println("1) Asignar responsable");
            System.out.println("2) Cambiar estado");
            System.out.println("3) Cambiar prioridad");
            System.out.println("0) Volver");
            opcion = Menu.leerEntero(scanner, "Opción: ");
            switch (opcion) {
                case 1 -> pendiente("asignar responsable");
                case 2 -> pendiente("cambiar estado");
                case 3 -> pendiente("cambiar prioridad");
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
