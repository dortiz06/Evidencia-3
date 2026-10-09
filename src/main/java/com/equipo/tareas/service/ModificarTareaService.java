package com.equipo.tareas.service;

import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;

import java.util.List;

/** Actualiza título y/o descripción de una tarea existente. */
public class ModificarTareaService {

    private final TareaRepository repositorio;

    public ModificarTareaService(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Modifica la tarea con el {@code id} indicado.
     * Solo aplica los parámetros distintos de {@code null}.
     *
     * @throws IllegalArgumentException si el id no existe o el nuevo título está vacío
     */
    public Tarea modificar(int id, String nuevoTitulo, String nuevaDescripcion) {
        List<Tarea> tareas = repositorio.cargar();
        Tarea tarea = repositorio.buscarPorId(tareas, id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una tarea con id " + id));

        if (nuevoTitulo != null) {
            String titulo = nuevoTitulo.trim();
            if (titulo.isEmpty()) {
                throw new IllegalArgumentException("El título no puede estar vacío");
            }
            tarea.setTitulo(titulo);
        }
        if (nuevaDescripcion != null) {
            tarea.setDescripcion(nuevaDescripcion);
        }

        repositorio.guardar(tareas);
        return tarea;
    }
}
