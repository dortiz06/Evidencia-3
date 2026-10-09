package com.equipo.tareas.ui;

import com.equipo.tareas.repository.TareaRepository;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final TareaRepository repository;
    private final MenuRegistroConsulta menuRegistroConsulta;
    private final MenuModificarEliminar menuModificarEliminar;
    private final MenuAsignacionEstados menuAsignacionEstados;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.repository = new TareaRepository();
        this.menuRegistroConsulta = new MenuRegistroConsulta(scanner, repository);
        this.menuModificarEliminar = new MenuModificarEliminar(scanner, repository);
        this.menuAsignacionEstados = new MenuAsignacionEstados(scanner, repository);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> menuRegistroConsulta.mostrar();
                case 2 -> menuModificarEliminar.mostrar();
                case 3 -> menuAsignacionEstados.mostrar();
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("=== Sistema de gestión de tareas ===");
        System.out.println("1) Registro, consulta y filtros");
        System.out.println("2) Modificar y eliminar");
        System.out.println("3) Asignación, estados y prioridades");
        System.out.println("0) Salir");
    }

    static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private int leerEntero(String mensaje) {
        return leerEntero(scanner, mensaje);
    }
}
