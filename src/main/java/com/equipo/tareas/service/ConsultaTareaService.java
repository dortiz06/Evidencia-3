package com.equipo.tareas.service;

import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;

import java.util.List;

public class ConsultaTareaService {

    private final TareaRepository repositorio;

    public ConsultaTareaService(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Tarea> listar() {
        return repositorio.cargar();
    }

    public Tarea obtenerPorId(int id) {
        List<Tarea> tareas = repositorio.cargar();
        return repositorio.buscarPorId(tareas, id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una tarea con id " + id + "."));
    }
}
