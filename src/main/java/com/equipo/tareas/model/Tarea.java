package com.equipo.tareas.model;

import java.time.LocalDate;

public class Tarea {

    private int id;
    private String titulo;
    private String descripcion;
    private Estado estado;
    private Prioridad prioridad;
    private String responsable;
    private String fechaCreacion;

    public Tarea() {
    }

    public Tarea(int id, String titulo, String descripcion, Prioridad prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = Estado.PENDIENTE;
        this.responsable = null;
        this.fechaCreacion = LocalDate.now().toString();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public String toString() {
        String nombreResponsable = (responsable == null || responsable.isBlank())
                ? "Sin asignar"
                : responsable;
        return String.format(
                "id=%d | %s | estado=%s | prioridad=%s | responsable=%s",
                id,
                titulo,
                estado != null ? estado.getEtiqueta() : "-",
                prioridad != null ? prioridad.getEtiqueta() : "-",
                nombreResponsable);
    }
}
