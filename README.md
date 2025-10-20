Gestion Universitaria
=====================

A small Spring Boot project for managing university data (students, subjects).

Summary of important information
- Java: project targets Java 21 (LTS). Tested with Oracle JDK 21.0.8.
- Build: Maven (3.9.x). Build command: `mvn package`.
- Run (H2 dev profile): `java -jar target/gestion-universitaria-1.0.0.jar --spring.profiles.active=dev`
  - This uses an in-memory H2 database for local development and enables the H2 console at `/h2-console`.
- Production / default run: `java -jar target/gestion-universitaria-1.0.0.jar`
  - This uses the `application.properties` datasource (SQL Server by default). Ensure SQL Server is reachable or set appropriate environment variables.

Development notes
- The repository was cleaned up to remove duplicate top-level `.java` and `.class` files. Canonical sources live under `src/main/java`.
- A `dev` Spring profile was added with H2 configuration in `src/main/resources/application-dev.properties` to make local runs easy without SQL Server.
- H2 dependency added to `pom.xml` (runtime scope).

How to switch databases
- Use the `dev` profile to run against H2:

  java -jar target/gestion-universitaria-1.0.0.jar --spring.profiles.active=dev

- Use the default profile to connect to SQL Server (requires proper credentials and network access as configured in `application.properties`).

Next recommended actions
- Externalize secrets: remove plaintext SQL Server password from `application.properties` and use environment variables or a secret manager.
- Add unit tests under `src/test/java` and CI pipeline to run them.
- Create a Git branch and commit the cleanup and additions.

Contact
- If you want me to commit changes or open a PR, say so and I'll make a branch and commit the edits.
