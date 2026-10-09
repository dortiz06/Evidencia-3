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

class EstadoPrioridadServiceTest {

    @TempDir
    Path directorioTemporal;

    private TareaRepository repositorio;
    private EstadoPrioridadService servicio;

    @BeforeEach
    void configurar() {
        Path archivo = directorioTemporal.resolve("tareas.json");
        repositorio = new TareaRepository(archivo.toString());
        servicio = new EstadoPrioridadService(repositorio);

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea(1, "Revisar informe", "Pendiente de revisión", Prioridad.MEDIA));
        repositorio.guardar(tareas);
    }

    @Test
    void cambiarEstadoGuardaElNuevoEstadoYLoDevuelve() {
        Tarea actualizada = servicio.cambiarEstado(1, Estado.EN_PROCESO);

        assertEquals(Estado.EN_PROCESO, actualizada.getEstado());
        assertEquals(Prioridad.MEDIA, actualizada.getPrioridad());
        assertNull(actualizada.getResponsable());

        Tarea persistida = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals(Estado.EN_PROCESO, persistida.getEstado());
    }

    @Test
    void cambiarPrioridadGuardaLaNuevaPrioridadYLaDevuelve() {
        Tarea actualizada = servicio.cambiarPrioridad(1, Prioridad.ALTA);

        assertEquals(Prioridad.ALTA, actualizada.getPrioridad());
        assertEquals(Estado.PENDIENTE, actualizada.getEstado());

        Tarea persistida = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals(Prioridad.ALTA, persistida.getPrioridad());
    }

    @Test
    void cambiarEstadoLanzaExcepcionSiElIdNoExisteOElEstadoEsNulo() {
        IllegalArgumentException idInexistente = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.cambiarEstado(99, Estado.TERMINADA));
        assertEquals("No existe una tarea con id 99", idInexistente.getMessage());

        IllegalArgumentException estadoNulo = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.cambiarEstado(1, null));
        assertEquals("El estado no puede ser nulo", estadoNulo.getMessage());

        Tarea sinCambios = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals(Estado.PENDIENTE, sinCambios.getEstado());
    }

    @Test
    void cambiarPrioridadLanzaExcepcionSiElIdNoExisteOLaPrioridadEsNula() {
        IllegalArgumentException idInexistente = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.cambiarPrioridad(40, Prioridad.BAJA));
        assertEquals("No existe una tarea con id 40", idInexistente.getMessage());

        IllegalArgumentException prioridadNula = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.cambiarPrioridad(1, null));
        assertEquals("La prioridad no puede ser nula", prioridadNula.getMessage());

        Tarea sinCambios = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals(Prioridad.MEDIA, sinCambios.getPrioridad());
    }
}
