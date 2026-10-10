package com.equipo.tareas.service;

import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;

import java.util.List;

/** Asigna un responsable a una tarea existente. */
public class AsignacionTareaService {

    private final TareaRepository repositorio;

    public AsignacionTareaService(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Asigna el responsable a la tarea indicada, la guarda y la devuelve.
     *
     * @throws IllegalArgumentException si el id no existe o el responsable es null o está vacío
     */
    public Tarea asignar(int id, String responsable) {
        if (responsable == null || responsable.isBlank()) {
            throw new IllegalArgumentException("El responsable no puede estar vacío");
        }

        List<Tarea> tareas = repositorio.cargar();
        Tarea tarea = repositorio.buscarPorId(tareas, id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una tarea con id " + id));

        tarea.setResponsable(responsable.trim());
        repositorio.guardar(tareas);
        return tarea;
    }
}
