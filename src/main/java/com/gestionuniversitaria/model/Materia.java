package com.gestionuniversitaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Entidad JPA que representa una materia en la base de datos.
@Entity
public class Materia {

    /** Identificador único de la materia (clave primaria) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de la materia */
    private String nombre;
    /** Código único de la materia */
    private String codigo;
    /** Créditos de la materia */
    private int creditos;

    /** Lista de notas asociadas a la materia */
    @jakarta.persistence.ElementCollection
    private java.util.List<Double> notas = new java.util.ArrayList<>();
    public java.util.List<Double> getNotas() {
        return notas;
    }
    public void setNotas(java.util.List<Double> notas) {
        this.notas = notas;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public int getCreditos() {
        return creditos;
    }
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
}
