package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo de Calificación con sistema colombiano (1.0-5.0)
 */
public record Calificacion(
        String id,
        @JsonProperty("enrollmentId") String inscripcionId,
        @JsonProperty("grade") Double nota, // Escala 1.0-5.0
        @JsonProperty("letterGrade") String notaLetra,
        String status) {
    /**
     * Convierte nota de 0-100 a escala colombiana 1.0-5.0
     */
    public static double convertirAEscalaColombiana(double notaCienPuntos) {
        if (notaCienPuntos >= 90)
            return 5.0;
        if (notaCienPuntos >= 80)
            return 4.5 + ((notaCienPuntos - 80) / 10) * 0.5;
        if (notaCienPuntos >= 70)
            return 4.0 + ((notaCienPuntos - 70) / 10) * 0.5;
        if (notaCienPuntos >= 60)
            return 3.0 + ((notaCienPuntos - 60) / 10) * 0.9;
        return 1.0 + (notaCienPuntos / 60) * 1.9;
    }

    /**
     * Obtiene calificación literal según nota
     */
    public static String obtenerNotaLetra(double nota) {
        if (nota >= 4.5)
            return "Excelente";
        if (nota >= 4.0)
            return "Sobresaliente";
        if (nota >= 3.5)
            return "Bueno";
        if (nota >= 3.0)
            return "Aceptable";
        return "Insuficiente";
    }
}
