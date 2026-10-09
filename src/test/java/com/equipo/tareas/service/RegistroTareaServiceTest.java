package com.equipo.tareas.service;

import com.equipo.tareas.model.Estado;
import com.equipo.tareas.model.Prioridad;
import com.equipo.tareas.model.Tarea;
import com.equipo.tareas.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistroTareaServiceTest {

    @TempDir
    Path directorioTemporal;

    private TareaRepository repositorio;
    private RegistroTareaService servicio;

    @BeforeEach
    void setUp() {
        repositorio = new TareaRepository(directorioTemporal.resolve("tareas.json").toString());
        servicio = new RegistroTareaService(repositorio);
    }

    @Test
    void registrarAsignaIdGuardaYUsaEstadoPendiente() {
        Tarea tarea = servicio.registrar("Estudiar Java", "Repasar colecciones", Prioridad.ALTA);

        assertEquals(1, tarea.getId());
        assertEquals("Estudiar Java", tarea.getTitulo());
        assertEquals("Repasar colecciones", tarea.getDescripcion());
        assertEquals(Estado.PENDIENTE, tarea.getEstado());
        assertEquals(Prioridad.ALTA, tarea.getPrioridad());
        assertNull(tarea.getResponsable());
        assertEquals(1, repositorio.cargar().size());
    }

    @Test
    void registrarUsaPrioridadMediaSiEsNula() {
        Tarea tarea = servicio.registrar("Comprar leche", "Ir al súper", null);

        assertEquals(Prioridad.MEDIA, tarea.getPrioridad());
    }

    @Test
    void registrarRechazaTituloNuloOVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar(null, "desc", Prioridad.BAJA));
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar("   ", "desc", Prioridad.BAJA));
    }

    @Test
    void registrarIncrementaElIdSegunLasTareasExistentes() {
        servicio.registrar("Primera", "a", Prioridad.BAJA);
        Tarea segunda = servicio.registrar("Segunda", "b", Prioridad.MEDIA);

        assertEquals(2, segunda.getId());
        assertEquals(2, repositorio.cargar().size());
    }
}
