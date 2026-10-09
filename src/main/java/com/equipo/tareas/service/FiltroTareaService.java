package com.equipo.tareas.service;

import com.equipo.tareas.model.Estado;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;

import java.util.List;

public class FiltroTareaService {

    private final TareaRepository repositorio;

    public FiltroTareaService(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Tarea> filtrarPorEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        return repositorio.cargar().stream()
                .filter(tarea -> estado.equals(tarea.getEstado()))
                .toList();
    }

    public List<Tarea> filtrarPorResponsable(String responsable) {
        if (responsable == null || responsable.isBlank()) {
            throw new IllegalArgumentException("El responsable no puede ser nulo ni vacío.");
        }
        String buscado = responsable.trim();
        return repositorio.cargar().stream()
                .filter(tarea -> tarea.getResponsable() != null
                        && tarea.getResponsable().equalsIgnoreCase(buscado))
                .toList();
    }
}
