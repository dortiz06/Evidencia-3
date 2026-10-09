package com.equipo.tareas.ui;

import com.equipo.tareas.model.Estado;
import com.equipo.tareas.model.Prioridad;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;
import com.equipo.tareas.service.ConsultaTareaService;
import com.equipo.tareas.service.FiltroTareaService;
import com.equipo.tareas.service.RegistroTareaService;

import java.util.List;
import java.util.Scanner;

public class MenuRegistroConsulta {

    private final Scanner scanner;
    private final RegistroTareaService registroTareaService;
    private final ConsultaTareaService consultaTareaService;
    private final FiltroTareaService filtroTareaService;

    public MenuRegistroConsulta(Scanner scanner, TareaRepository repository) {
        this.scanner = scanner;
        this.registroTareaService = new RegistroTareaService(repository);
        this.consultaTareaService = new ConsultaTareaService(repository);
        this.filtroTareaService = new FiltroTareaService(repository);
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- Registro, consulta y filtros ---");
            System.out.println("1) Registrar tarea");
            System.out.println("2) Listar tareas");
            System.out.println("3) Ver tarea por id");
            System.out.println("4) Filtrar por estado");
            System.out.println("5) Filtrar por responsable");
            System.out.println("0) Volver");
            opcion = Menu.leerEntero(scanner, "Opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarTarea();
                    case 2 -> listarTareas();
                    case 3 -> verTareaPorId();
                    case 4 -> filtrarPorEstado();
                    case 5 -> filtrarPorResponsable();
                    case 0 -> { }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void registrarTarea() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        Prioridad prioridad = leerPrioridadOpcional();
        Tarea tarea = registroTareaService.registrar(titulo, descripcion, prioridad);
        System.out.println("Tarea registrada: " + tarea);
    }

    private void listarTareas() {
        mostrarLista(consultaTareaService.listar(), "No hay tareas registradas.");
    }

    private void verTareaPorId() {
        int id = Menu.leerEntero(scanner, "Id de la tarea: ");
        Tarea tarea = consultaTareaService.obtenerPorId(id);
        System.out.println(tarea);
    }

    private void filtrarPorEstado() {
        Estado estado = leerEstado();
        mostrarLista(
                filtroTareaService.filtrarPorEstado(estado),
                "No hay tareas con estado " + estado.getEtiqueta() + ".");
    }

    private void filtrarPorResponsable() {
        System.out.print("Responsable: ");
        String responsable = scanner.nextLine();
        mostrarLista(
                filtroTareaService.filtrarPorResponsable(responsable),
                "No hay tareas asignadas a ese responsable.");
    }

    private void mostrarLista(List<Tarea> tareas, String mensajeVacio) {
        if (tareas.isEmpty()) {
            System.out.println(mensajeVacio);
            return;
        }
        tareas.forEach(System.out::println);
    }

    private Prioridad leerPrioridadOpcional() {
        System.out.println("Prioridad (Enter para Media):");
        for (int i = 0; i < Prioridad.values().length; i++) {
            System.out.println((i + 1) + ") " + Prioridad.values()[i].getEtiqueta());
        }
        System.out.print("Opción: ");
        String linea = scanner.nextLine().trim();
        if (linea.isEmpty()) {
            return null;
        }
        try {
            int indice = Integer.parseInt(linea);
            if (indice < 1 || indice > Prioridad.values().length) {
                throw new IllegalArgumentException(
                        "Prioridad no válida. Use un número entre 1 y " + Prioridad.values().length + ".");
            }
            return Prioridad.values()[indice - 1];
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La prioridad debe ser un número o quedar vacía para Media.");
        }
    }

    private Estado leerEstado() {
        System.out.println("Estado:");
        for (int i = 0; i < Estado.values().length; i++) {
            System.out.println((i + 1) + ") " + Estado.values()[i].getEtiqueta());
        }
        int indice = Menu.leerEntero(scanner, "Opción: ");
        if (indice < 1 || indice > Estado.values().length) {
            throw new IllegalArgumentException(
                    "Estado no válido. Use un número entre 1 y " + Estado.values().length + ".");
        }
        return Estado.values()[indice - 1];
    }
}
