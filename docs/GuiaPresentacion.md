# Guía de Presentación del Proyecto

## 1. Introducción
Este proyecto es un **Sistema de Gestión Universitaria** integral diseñado para modernizar la administración académica.
Utiliza una arquitectura **MVC** (Modelo-Vista-Controlador) sobre **Java 17** y **Maven**, con un frontend dinámico en **HTML5/JS**.

## 2. Características Clave (Puntos a resaltar)
- **Gestión en Tiempo Real**: No se requiere recargar la página para ver los cambios.
- **Integridad de Datos**:
  - Validaciones estrictas en el servidor (Emails, campos vacíos, notas válidas).
  - Prevención de errores de formato numérico.
- **Persistencia Automática**: Los datos se guardan en disco (`data.json`) instantáneamente.
- **Interconexión**: El sistema vincula Estudiantes, Cursos e Inscripciones de forma relacional.

## 3. Demostración (Flujo sugerido)
1.  **Estudiantes**: Cree un estudiante. Muestre cómo el sistema valida si falta el email o tiene formato incorrecto.
2.  **Profesores y Cursos**: Registre un profesor y cree un curso asignado a su ID (copie el ID generado del profesor).
3.  **Inscripciones**: Vaya a la pestaña de Inscripciones. Seleccione el estudiante y curso creados. Muestre cómo aparece inmediatamente en la tabla de "Materias Registradas".
4.  **Calificaciones**: Asigne una nota a esa inscripción. Intente poner un 6.0 para mostrar el error de validación (Rango 0-5).
5.  **Persistencia**: Cierre el programa y vuélvalo a abrir para demostrar que los datos persisten.

## 4. Tecnologías Usadas
- **Backend**: Java, Javalin (Servidor Web), Jackson (JSON).
- **Frontend**: JavaScript (Fetch API), Bootstrap 5.
- **Herramientas**: Maven, Git.
