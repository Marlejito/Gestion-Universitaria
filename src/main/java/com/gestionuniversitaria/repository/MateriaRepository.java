package com.gestionuniversitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionuniversitaria.model.Materia;

// Repositorio JPA para acceder a las materias en la base de datos.
public interface MateriaRepository extends JpaRepository<Materia, Long> {
	Materia findByCodigo(String codigo);
}
