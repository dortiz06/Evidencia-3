package com.equipo.tareas.ui;

import com.equipo.tareas.repository.TareaRepository;

import java.util.Scanner;

/**
 * Menú de registro, listado y filtros.
 * Conectar aquí el servicio de registro/consulta (constructor con TareaRepository).
 */
public class MenuRegistroConsulta {

    private final Scanner scanner;
    private final TareaRepository repository;

    public MenuRegistroConsulta(Scanner scanner, TareaRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- Registro, consulta y filtros ---");
            System.out.println("1) Registrar tarea");
            System.out.println("2) Listar todas");
            System.out.println("3) Buscar por id");
            System.out.println("4) Filtrar por estado");
            System.out.println("5) Filtrar por prioridad");
            System.out.println("6) Filtrar por responsable");
            System.out.println("0) Volver");
            opcion = Menu.leerEntero(scanner, "Opción: ");
            switch (opcion) {
                case 1 -> pendiente("registrar tarea");
                case 2 -> pendiente("listar todas las tareas");
                case 3 -> pendiente("buscar por id");
                case 4 -> pendiente("filtrar por estado");
                case 5 -> pendiente("filtrar por prioridad");
                case 6 -> pendiente("filtrar por responsable");
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
