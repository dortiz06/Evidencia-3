package com.equipo.tareas.service;

import com.equipo.tareas.model.Estado;
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

class FiltroTareaServiceTest {

    @TempDir
    Path directorioTemporal;

    private TareaRepository repositorio;
    private FiltroTareaService servicio;

    @BeforeEach
    void setUp() {
        repositorio = new TareaRepository(directorioTemporal.resolve("tareas.json").toString());
        servicio = new FiltroTareaService(repositorio);
        prepararDatos();
    }

    @Test
    void filtrarPorEstadoDevuelveSoloLasCoincidentes() {
        List<Tarea> pendientes = servicio.filtrarPorEstado(Estado.PENDIENTE);
        List<Tarea> enProceso = servicio.filtrarPorEstado(Estado.EN_PROCESO);

        assertEquals(2, pendientes.size());
        assertEquals(1, enProceso.size());
        assertEquals("En curso", enProceso.get(0).getTitulo());
    }

    @Test
    void filtrarPorEstadoRechazaNulo() {
        assertThrows(IllegalArgumentException.class, () -> servicio.filtrarPorEstado(null));
    }

    @Test
    void filtrarPorResponsableIgnoraMayusculasYTareasSinAsignar() {
        List<Tarea> deAna = servicio.filtrarPorResponsable("ana");

        assertEquals(2, deAna.size());
        assertTrue(deAna.stream().allMatch(t -> t.getResponsable().equalsIgnoreCase("Ana")));
    }

    @Test
    void filtrarPorResponsableRechazaNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> servicio.filtrarPorResponsable(null));
        assertThrows(IllegalArgumentException.class, () -> servicio.filtrarPorResponsable("  "));
    }

    private void prepararDatos() {
        Tarea pendienteAna = new Tarea(1, "Pendiente de Ana", "a", Prioridad.BAJA);
        pendienteAna.setResponsable("Ana");

        Tarea enProcesoLuis = new Tarea(2, "En curso", "b", Prioridad.MEDIA);
        enProcesoLuis.setEstado(Estado.EN_PROCESO);
        enProcesoLuis.setResponsable("Luis");

        Tarea pendienteSinAsignar = new Tarea(3, "Sin dueño", "c", Prioridad.ALTA);

        Tarea terminadaAna = new Tarea(4, "Terminada de Ana", "d", Prioridad.MEDIA);
        terminadaAna.setEstado(Estado.TERMINADA);
        terminadaAna.setResponsable("ANA");

        repositorio.guardar(List.of(pendienteAna, enProcesoLuis, pendienteSinAsignar, terminadaAna));
    }
}
