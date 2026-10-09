package com.equipo.tareas.ui;

import com.equipo.tareas.model.Estado;
import com.equipo.tareas.model.Prioridad;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;
import com.equipo.tareas.service.AsignacionTareaService;
import com.equipo.tareas.service.EstadoPrioridadService;

import java.util.Scanner;

/**
 * Menú de asignación de responsable, cambio de estado y prioridad.
 */
public class MenuAsignacionEstados {

    private final Scanner scanner;
    private final TareaRepository repository;
    private final AsignacionTareaService asignacionTareaService;
    private final EstadoPrioridadService estadoPrioridadService;

    public MenuAsignacionEstados(Scanner scanner, TareaRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
        this.asignacionTareaService = new AsignacionTareaService(repository);
        this.estadoPrioridadService = new EstadoPrioridadService(repository);
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
                case 1 -> asignarResponsable();
                case 2 -> cambiarEstado();
                case 3 -> cambiarPrioridad();
                case 0 -> { }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void asignarResponsable() {
        try {
            int id = Menu.leerEntero(scanner, "Id de la tarea: ");
            if (!mostrarTarea(id)) {
                return;
            }
            System.out.print("Responsable: ");
            String responsable = scanner.nextLine();
            Tarea actualizada = asignacionTareaService.asignar(id, responsable);
            System.out.println("Responsable asignado: " + actualizada);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo asignar: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al asignar el responsable: " + e.getMessage());
        }
    }

    private void cambiarEstado() {
        try {
            int id = Menu.leerEntero(scanner, "Id de la tarea: ");
            if (!mostrarTarea(id)) {
                return;
            }
            Estado[] estados = Estado.values();
            for (int i = 0; i < estados.length; i++) {
                System.out.println((i + 1) + ") " + estados[i].getEtiqueta());
            }
            int seleccion = Menu.leerEntero(scanner, "Estado: ");
            if (seleccion < 1 || seleccion > estados.length) {
                System.out.println("Opción de estado no válida.");
                return;
            }
            Tarea actualizada = estadoPrioridadService.cambiarEstado(id, estados[seleccion - 1]);
            System.out.println("Estado actualizado: " + actualizada);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo cambiar el estado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al cambiar el estado: " + e.getMessage());
        }
    }

    private void cambiarPrioridad() {
        try {
            int id = Menu.leerEntero(scanner, "Id de la tarea: ");
            if (!mostrarTarea(id)) {
                return;
            }
            Prioridad[] prioridades = Prioridad.values();
            for (int i = 0; i < prioridades.length; i++) {
                System.out.println((i + 1) + ") " + prioridades[i].getEtiqueta());
            }
            int seleccion = Menu.leerEntero(scanner, "Prioridad: ");
            if (seleccion < 1 || seleccion > prioridades.length) {
                System.out.println("Opción de prioridad no válida.");
                return;
            }
            Tarea actualizada = estadoPrioridadService.cambiarPrioridad(id, prioridades[seleccion - 1]);
            System.out.println("Prioridad actualizada: " + actualizada);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo cambiar la prioridad: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al cambiar la prioridad: " + e.getMessage());
        }
    }

    private boolean mostrarTarea(int id) {
        Tarea actual = repository.buscarPorId(repository.cargar(), id).orElse(null);
        if (actual == null) {
            System.out.println("No existe una tarea con id " + id + ".");
            return false;
        }
        System.out.println("Tarea actual: " + actual);
        return true;
    }
}
