package com.gestionuniversitaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestionuniversitaria.model.Materia;
import com.gestionuniversitaria.repository.MateriaRepository;

// Controlador REST que gestiona las operaciones CRUD de materias.
@RestController
@RequestMapping("/api/materias")
@CrossOrigin
public class MateriaController {
    @Autowired
    private MateriaRepository repo;


    /**
     * Crea una nueva materia en la base de datos.
     * @param mat Materia recibida en el cuerpo de la petición
     * @return La materia guardada
     */
    @PostMapping
    public Materia crear(@RequestBody Materia mat) {
        System.out.println("Registrando materia: " + mat.getCodigo());
        return repo.save(mat);
    }

    /**
     * Devuelve la lista de todas las materias registradas.
     * @return Lista de materias
     */
    @GetMapping
    public List<Materia> listar() {
        return repo.findAll();
    }

    /**
     * Actualiza las notas de una materia según su código.
     * @param codigo Código de la materia
     * @param notas Lista de notas a actualizar
     * @return La materia actualizada
     */
    @PutMapping("/{codigo}/notas")
    public Materia actualizarNotas(@PathVariable String codigo, @RequestBody List<Double> notas) {
        Materia mat = repo.findByCodigo(codigo);
        mat.setNotas(notas);
        System.out.println("Actualizando notas de " + codigo + ": " + notas);
        return repo.save(mat);
    }
}
