package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Inscripcion(
                String id,
                @JsonProperty("studentId") String estudianteId,
                @JsonProperty("courseId") String cursoId,
                @JsonProperty("semester") String semestre,
                String status) {
}
