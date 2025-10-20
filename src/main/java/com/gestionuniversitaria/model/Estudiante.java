
// Entidad JPA que representa a un estudiante en la base de datos.
package com.gestionuniversitaria.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estudiante {

    /** Identificador único del estudiante (clave primaria) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código único del estudiante */
    private String codigo;
    /** Nombres del estudiante */
    private String nombres;
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    /** Apellidos del estudiante */
    private String apellidos;
    /** Correo electrónico del estudiante */
    private String email;
    /** Fecha de nacimiento del estudiante */
    private LocalDate fechaNacimiento;
    /** Carrera que cursa el estudiante */
    private String carrera;
    /** Promedio acumulado del estudiante */
    private double promedioAcumulado;
    /** Semestre actual del estudiante */
    private int semestreActual;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public double getPromedioAcumulado() {
        return promedioAcumulado;
    }
    public void setPromedioAcumulado(double promedioAcumulado) {
        this.promedioAcumulado = promedioAcumulado;
    }
    public int getSemestreActual() {
        return semestreActual;
    }
    public void setSemestreActual(int semestreActual) {
        this.semestreActual = semestreActual;
    }
}
