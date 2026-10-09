package com.equipo.tareas.service;

import com.equipo.tareas.model.Prioridad;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsultaTareaServiceTest {

    @TempDir
    Path directorioTemporal;

    private TareaRepository repositorio;
    private ConsultaTareaService servicio;

    @BeforeEach
    void setUp() {
        repositorio = new TareaRepository(directorioTemporal.resolve("tareas.json").toString());
        servicio = new ConsultaTareaService(repositorio);
    }

    @Test
    void listarDevuelveListaVaciaSiNoHayTareas() {
        assertTrue(servicio.listar().isEmpty());
    }

    @Test
    void listarDevuelveLasTareasGuardadasEnElRepositorio() {
        guardar(new Tarea(1, "Tarea A", "a", Prioridad.BAJA));
        guardar(new Tarea(2, "Tarea B", "b", Prioridad.ALTA));

        List<Tarea> tareas = servicio.listar();

        assertEquals(2, tareas.size());
        assertEquals("Tarea A", tareas.get(0).getTitulo());
        assertEquals("Tarea B", tareas.get(1).getTitulo());
    }

    @Test
    void obtenerPorIdDevuelveLaTareaExistente() {
        guardar(new Tarea(5, "Buscada", "d", Prioridad.MEDIA));

        Tarea encontrada = servicio.obtenerPorId(5);

        assertEquals(5, encontrada.getId());
        assertEquals("Buscada", encontrada.getTitulo());
    }

    @Test
    void obtenerPorIdLanzaExcepcionSiNoExiste() {
        guardar(new Tarea(1, "Única", "d", Prioridad.BAJA));

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.obtenerPorId(99));
        assertTrue(error.getMessage().contains("99"));
    }

    private void guardar(Tarea tarea) {
        List<Tarea> actuales = repositorio.cargar();
        actuales.add(tarea);
        repositorio.guardar(actuales);
    }
}
