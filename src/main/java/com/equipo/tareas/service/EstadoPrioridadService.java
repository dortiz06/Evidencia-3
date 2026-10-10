package com.equipo.tareas.service;

import com.equipo.tareas.model.Estado;
import com.equipo.tareas.model.Prioridad;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;

import java.util.List;

/** Actualiza el estado y la prioridad de una tarea existente. */
public class EstadoPrioridadService {

    private final TareaRepository repositorio;

    public EstadoPrioridadService(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Cambia el estado de la tarea indicada, la guarda y la devuelve.
     *
     * @throws IllegalArgumentException si el id no existe o el estado es null
     */
    public Tarea cambiarEstado(int id, Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        List<Tarea> tareas = repositorio.cargar();
        Tarea tarea = buscar(tareas, id);
        tarea.setEstado(estado);
        repositorio.guardar(tareas);
        return tarea;
    }

    /**
     * Cambia la prioridad de la tarea indicada, la guarda y la devuelve.
     *
     * @throws IllegalArgumentException si el id no existe o la prioridad es null
     */
    public Tarea cambiarPrioridad(int id, Prioridad prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad no puede ser nula");
        }

        List<Tarea> tareas = repositorio.cargar();
        Tarea tarea = buscar(tareas, id);
        tarea.setPrioridad(prioridad);
        repositorio.guardar(tareas);
        return tarea;
    }

    private Tarea buscar(List<Tarea> tareas, int id) {
        return repositorio.buscarPorId(tareas, id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una tarea con id " + id));
    }
}
