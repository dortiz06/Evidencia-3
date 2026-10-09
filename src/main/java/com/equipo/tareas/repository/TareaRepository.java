package com.equipo.tareas.repository;

import com.equipo.tareas.model.Tarea;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TareaRepository {

    public static final String RUTA_POR_DEFECTO = "data/tareas.json";

    private static final Type TIPO_LISTA = new TypeToken<List<Tarea>>() { }.getType();

    private final Path ruta;
    private final Gson gson;

    public TareaRepository() {
        this(RUTA_POR_DEFECTO);
    }

    public TareaRepository(String ruta) {
        this.ruta = Path.of(ruta);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<Tarea> cargar() {
        try {
            if (Files.notExists(ruta)) {
                crearArchivoVacio();
                return new ArrayList<>();
            }
            String json = Files.readString(ruta, StandardCharsets.UTF_8);
            List<Tarea> tareas = gson.fromJson(json, TIPO_LISTA);
            if (tareas == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(tareas);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer " + ruta, e);
        }
    }

    public void guardar(List<Tarea> tareas) {
        try {
            Path padre = ruta.getParent();
            if (padre != null) {
                Files.createDirectories(padre);
            }
            List<Tarea> aGuardar = tareas != null ? tareas : List.of();
            Files.writeString(ruta, gson.toJson(aGuardar), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir " + ruta, e);
        }
    }

    public int siguienteId(List<Tarea> tareas) {
        if (tareas == null || tareas.isEmpty()) {
            return 1;
        }
        return tareas.stream()
                .mapToInt(Tarea::getId)
                .max()
                .orElse(0) + 1;
    }

    public Optional<Tarea> buscarPorId(List<Tarea> tareas, int id) {
        if (tareas == null) {
            return Optional.empty();
        }
        return tareas.stream()
                .filter(tarea -> tarea.getId() == id)
                .findFirst();
    }

    private void crearArchivoVacio() throws IOException {
        Path padre = ruta.getParent();
        if (padre != null) {
            Files.createDirectories(padre);
        }
        Files.writeString(ruta, "[]", StandardCharsets.UTF_8);
    }
}
