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

class ModificarTareaServiceTest {

    @TempDir
    Path directorioTemporal;

    private TareaRepository repositorio;
    private ModificarTareaService servicio;

    @BeforeEach
    void configurar() {
        Path archivo = directorioTemporal.resolve("tareas.json");
        repositorio = new TareaRepository(archivo.toString());
        servicio = new ModificarTareaService(repositorio);

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea(1, "Título original", "Descripción original", Prioridad.MEDIA));
        repositorio.guardar(tareas);
    }

    @Test
    void modificarActualizaTituloYDescripcion() {
        Tarea actualizada = servicio.modificar(1, "Título nuevo", "Descripción nueva");

        assertEquals("Título nuevo", actualizada.getTitulo());
        assertEquals("Descripción nueva", actualizada.getDescripcion());

        Tarea persistida = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals("Título nuevo", persistida.getTitulo());
        assertEquals("Descripción nueva", persistida.getDescripcion());
    }

    @Test
    void modificarSoloAplicaParametrosNoNulos() {
        Tarea soloTitulo = servicio.modificar(1, "Solo título", null);
        assertEquals("Solo título", soloTitulo.getTitulo());
        assertEquals("Descripción original", soloTitulo.getDescripcion());

        Tarea soloDescripcion = servicio.modificar(1, null, "Solo descripción");
        assertEquals("Solo título", soloDescripcion.getTitulo());
        assertEquals("Solo descripción", soloDescripcion.getDescripcion());
    }

    @Test
    void modificarLanzaExcepcionSiElIdNoExiste() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.modificar(99, "Otro", "Otra"));
        assertEquals("No existe una tarea con id 99", excepcion.getMessage());
    }

    @Test
    void modificarLanzaExcepcionSiElNuevoTituloEstaVacio() {
        assertThrows(IllegalArgumentException.class, () -> servicio.modificar(1, "   ", "Nueva"));
        assertThrows(IllegalArgumentException.class, () -> servicio.modificar(1, "", null));

        Tarea sinCambios = repositorio.buscarPorId(repositorio.cargar(), 1).orElseThrow();
        assertEquals("Título original", sinCambios.getTitulo());
        assertEquals("Descripción original", sinCambios.getDescripcion());
    }
}
