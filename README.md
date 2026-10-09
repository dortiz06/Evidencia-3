# Sistema de gestión de tareas

Aplicación de consola en Java para registrar, consultar, modificar y asignar tareas. La persistencia se realiza en un archivo JSON (`data/tareas.json`) para que el estado sobreviva entre ejecuciones.

Este repositorio contiene **solo la base compartida**: modelo, repositorio, menús y configuración Maven. Cada integrante implementará su capa de servicio y sus pruebas en `src/test/java`.

## Tecnologías

- Java 17
- Maven
- Gson (serialización JSON)
- JUnit 5 (pruebas; las escribe cada integrante)

## Estructura

```
task-manager/
├── pom.xml
├── README.md
├── data/                          # tareas.json se crea al ejecutar
└── src/
    ├── main/java/com/equipo/tareas/
    │   ├── Main.java
    │   ├── model/                 # Estado, Prioridad, Tarea
    │   ├── repository/            # persistencia JSON
    │   ├── service/               # lo implementa cada integrante
    │   └── ui/                    # menús de consola
    └── test/java/com/equipo/tareas/service/
```

## Compilar

Desde la carpeta `task-manager/`:

```bash
mvn compile
```

## Ejecutar

```bash
mvn compile exec:java
```

## Probar

Cuando existan pruebas en `src/test/java`:

```bash
mvn test
```

## Convención de servicios

Cada clase de servicio debe vivir en `com.equipo.tareas.service`, recibir un `TareaRepository` en el constructor y lanzar `IllegalArgumentException` si los datos no son válidos. Los menús ya están preparados para conectar esas clases.

## Integrantes

<!-- Completar nombres, roles y módulo asignado -->

## Flujo de trabajo

<!-- Completar acuerdos de ramas, commits y cómo integrar cada módulo -->
