package com.gestionuniversitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionuniversitaria.model.Estudiante;

// Repositorio JPA para acceder a los estudiantes en la base de datos.
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
