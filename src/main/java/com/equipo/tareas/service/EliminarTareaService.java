package com.equipo.tareas.service;

import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;

import java.util.List;

/** Elimina una tarea por identificador. */
public class EliminarTareaService {

    private final TareaRepository repositorio;

    public EliminarTareaService(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Elimina la tarea con el {@code id} indicado y la devuelve.
     *
     * @throws IllegalArgumentException si el id no existe
     */
    public Tarea eliminar(int id) {
        List<Tarea> tareas = repositorio.cargar();
        Tarea tarea = repositorio.buscarPorId(tareas, id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una tarea con id " + id));

        tareas.remove(tarea);
        repositorio.guardar(tareas);
        return tarea;
    }
}
