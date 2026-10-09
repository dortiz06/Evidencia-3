package com.equipo.tareas.service;

import com.equipo.tareas.model.Prioridad;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;

import java.util.List;

public class RegistroTareaService {

    private final TareaRepository repositorio;

    public RegistroTareaService(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Tarea registrar(String titulo, String descripcion, Prioridad prioridad) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede ser nulo ni vacío.");
        }
        Prioridad prioridadFinal = prioridad != null ? prioridad : Prioridad.MEDIA;
        List<Tarea> tareas = repositorio.cargar();
        int id = repositorio.siguienteId(tareas);
        Tarea tarea = new Tarea(id, titulo.trim(), descripcion, prioridadFinal);
        tareas.add(tarea);
        repositorio.guardar(tareas);
        return tarea;
    }
}
