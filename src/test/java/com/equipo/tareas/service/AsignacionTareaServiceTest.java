package com.equipo.tareas.service;

import com.equipo.tareas.model.Estado;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AsignacionTareaServiceTest {

    @TempDir
    Path directorioTemporal;

    private TareaRepository repositorio;
    private AsignacionTareaService servicio;

    @BeforeEach
    void configurar() {
        Path archivo = directorioTemporal.resolve("tareas.json");
        repositorio = new TareaRepository(archivo.toString());
        servicio = new AsignacionTareaService(repositorio);

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea(1, "Revisar informe", "Pendiente de revisión", Prioridad.MEDIA));
        repositorio.guardar(tareas);
    }

    @Test
    void asignarGuardaElResponsableYLoDevuelve() {
        Tarea asignada = servicio.asignar(1, "Ana López");

        assertEquals("Ana López", asignada.getResponsable());
        assertEquals(Estado.PENDIENTE, asignada.getEstado());
        assertEquals(Prioridad.MEDIA, asignada.getPrioridad());

        Tarea persistida = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals("Ana López", persistida.getResponsable());
    }

    @Test
    void asignarRecortaEspaciosDelResponsable() {
        Tarea asignada = servicio.asignar(1, "  Carlos  ");

        assertEquals("Carlos", asignada.getResponsable());
        Tarea persistida = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals("Carlos", persistida.getResponsable());
    }

    @Test
    void asignarLanzaExcepcionSiElIdNoExiste() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.asignar(99, "Ana López"));
        assertEquals("No existe una tarea con id 99", excepcion.getMessage());

        Tarea sinCambios = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertNull(sinCambios.getResponsable());
    }

    @Test
    void asignarLanzaExcepcionSiElResponsableEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> servicio.asignar(1, null));
        assertThrows(IllegalArgumentException.class, () -> servicio.asignar(1, ""));
        assertThrows(IllegalArgumentException.class, () -> servicio.asignar(1, "   "));

        Tarea sinCambios = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertNull(sinCambios.getResponsable());
    }
}
