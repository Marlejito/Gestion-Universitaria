# Gestión Universitaria

Pequeño proyecto Spring Boot para gestionar datos universitarios (estudiantes, materias).

Resumen

- Java: el proyecto apunta a Java 21 (LTS). Probado con Oracle JDK 21.0.8.
- Compilación: Maven (3.9.x). Comando para compilar: `mvn package`.
- Ejecutar en desarrollo (perfil `dev` con H2):

  java -jar target/gestion-universitaria-1.0.0.jar --spring.profiles.active=dev

  - Usa una base de datos H2 en memoria para desarrollo local y activa la consola H2 en `/h2-console`.
- Ejecutar en producción / por defecto:

  java -jar target/gestion-universitaria-1.0.0.jar

  - Usa la configuración de `application.properties` (por defecto SQL Server). Asegúrate de que SQL Server esté accesible o configura las variables de entorno adecuadas.

Notas de desarrollo

- El repositorio fue limpiado para eliminar artefactos compilados y duplicados; el código fuente principal está en `src/main/java`.
- Se añadió un perfil `dev` con configuración H2 en `src/main/resources/application-dev.properties` para facilitar el desarrollo local.
- Dependencia H2 incluida en `pom.xml` con scope runtime.

Cómo cambiar de base de datos

- Ejecuta con el perfil `dev` para usar H2:

  java -jar target/gestion-universitaria-1.0.0.jar --spring.profiles.active=dev

- Ejecuta por defecto para usar SQL Server (requiere credenciales y acceso de red configurados en `application.properties`).

Acciones recomendadas

- Externalizar secretos: no dejar la contraseña de SQL Server en texto claro en `application.properties`; usar variables de entorno o un gestor de secretos.
- Añadir pruebas unitarias en `src/test/java` y un pipeline CI para ejecutarlas.
- Crear ramas para nuevas características y PRs para revisiones.


## Nota sobre la interfaz web e inclusión en el JAR

- El archivo `index.html` ahora se incluye en el JAR bajo `src/main/resources/static/` y se sirve desde la raíz de la aplicación.
  - Accede a la interfaz web en http://localhost:8080/ cuando la aplicación esté ejecutándose.

## Limpieza de artefactos de build

- Se eliminó la carpeta `target/` del índice Git para evitar subir artefactos compilados al repositorio. `target/` sigue existiendo en tu disco local cuando compiles, pero ya no se trackea.

Comandos útiles:

```powershell
mvn package
java -jar target/gestion-universitaria-1.0.0.jar --spring.profiles.active=dev
```

Si necesitas eliminar por completo los artefactos del historial (reduce el tamaño del repositorio), puedo ayudarte a ejecutar herramientas como BFG o `git filter-repo` (esto reescribe el historial y requiere coordinación).


