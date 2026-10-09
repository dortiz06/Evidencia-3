package com.equipo.tareas.service;

import com.equipo.tareas.model.Prioridad;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EliminarTareaServiceTest {

    @TempDir
    Path directorioTemporal;

    private TareaRepository repositorio;
    private EliminarTareaService servicio;

    @BeforeEach
    void configurar() {
        Path archivo = directorioTemporal.resolve("tareas.json");
        repositorio = new TareaRepository(archivo.toString());
        servicio = new EliminarTareaService(repositorio);

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea(1, "Primera", "Desc 1", Prioridad.BAJA));
        tareas.add(new Tarea(2, "Segunda", "Desc 2", Prioridad.ALTA));
        repositorio.guardar(tareas);
    }

    @Test
    void eliminarDevuelveLaTareaYLaQuitaDelRepositorio() {
        Tarea eliminada = servicio.eliminar(1);

        assertEquals(1, eliminada.getId());
        assertEquals("Primera", eliminada.getTitulo());

        List<Tarea> restantes = repositorio.cargar();
        assertEquals(1, restantes.size());
        assertEquals(2, restantes.get(0).getId());
        assertTrue(repositorio.buscarPorId(restantes, 1).isEmpty());
    }

    @Test
    void eliminarLanzaExcepcionSiElIdNoExiste() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.eliminar(50));
        assertEquals("No existe una tarea con id 50", excepcion.getMessage());

        assertEquals(2, repositorio.cargar().size());
    }
}
