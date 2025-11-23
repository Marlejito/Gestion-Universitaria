# Manual de Usuario

## Ejecución del Proyecto (IMPORTANTE)
Este proyecto requiere que ejecutes el servidor Java para que la página web funcione y pueda guardar datos.

### Pasos para Ejecutar:
1.  Abre la carpeta del proyecto en tu terminal o consola de comandos.
2.  Ejecuta el siguiente comando para compilar y empaquetar el proyecto:
    ```bash
    mvn clean package
    ```
3.  Una vez finalizado, ejecuta el servidor con:
    ```bash
    java -jar target/gestion-universitaria-1.0.jar
    ```
    *(Si usas Windows y te da error, intenta usar `java -jar target\gestion-universitaria-1.0.jar`)*.

4.  Abre tu navegador (Chrome, Edge, etc.) y ve a la dirección:
    **http://localhost:7000**

## Funcionalidades del Sistema

### 1. Gestión de Datos Básicos
-   **Estudiantes**: Registra alumnos con Nombre y Email.
-   **Profesores**: Registra docentes con Nombre y Especialidad.
-   **Cursos**: Crea cursos asignados a un profesor. **Importante**: Debes asignar los porcentajes (%) para los 3 cortes, asegurándote de que sumen 100%.

### 2. Matrículas (Inscripciones)
-   Ve a la pestaña **Inscripciones**.
-   Selecciona un estudiante y un curso para matricularlo.
-   **Borrar Inscripción**: Si te equivocaste, en la lista de materias verás una **'x' roja** junto al nombre de la materia. Haz clic para eliminar esa inscripción.

### 3. Calificaciones
-   Ve a la pestaña **Calificaciones**.
-   Selecciona la inscripción (Estudiante - Materia).
-   Elige el **Corte** (1, 2 o 3) e ingresa la nota (0.0 a 5.0).
-   **Borrar Nota**: En la tabla inferior aparecerá un botón rojo "X" para borrar cualquier nota ingresada por error.

### 4. Reportes y Boletines
-   **Boletín**: Selecciona un estudiante para ver su rendimiento detallado. El sistema calcula automáticamente la **Definitiva** basándose en los porcentajes del curso.
    -   Si la definitiva es menor a 3.0, aparecerá en rojo.
    -   Si es mayor o igual a 3.0, en verde.
-   **Reportes**: Vista rápida de totales del sistema.

## Persistencia de Datos
Toda la información se guarda automáticamente en el archivo `data.json` en la carpeta del proyecto.
-   Si cierras el programa y lo vuelves a abrir, tus datos seguirán ahí.
-   Si deseas "resetear" el sistema, simplemente borra el archivo `data.json` antes de iniciar el servidor.
