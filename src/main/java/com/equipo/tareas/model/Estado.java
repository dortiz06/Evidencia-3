package com.equipo.tareas.model;

public enum Estado {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En proceso"),
    TERMINADA("Terminada");

    private final String etiqueta;

    Estado(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
