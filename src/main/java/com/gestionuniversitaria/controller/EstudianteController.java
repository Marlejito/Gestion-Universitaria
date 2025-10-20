package com.gestionuniversitaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestionuniversitaria.model.Estudiante;
import com.gestionuniversitaria.repository.EstudianteRepository;

// Controlador REST que gestiona las operaciones CRUD de estudiantes.
@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin
public class EstudianteController {
    @Autowired
    private EstudianteRepository repo;


    /**
     * Crea un nuevo estudiante en la base de datos.
     * @param est Estudiante recibido en el cuerpo de la petición
     * @return El estudiante guardado
     */
    @PostMapping
    public Estudiante crear(@RequestBody Estudiante est) {
        System.out.println("Registrando estudiante: " + est.getCodigo());
        return repo.save(est);
    }

    /**
     * Devuelve la lista de todos los estudiantes registrados.
     * @return Lista de estudiantes
     */
    @GetMapping
    public List<Estudiante> listar() {
        return repo.findAll();
    }
}
