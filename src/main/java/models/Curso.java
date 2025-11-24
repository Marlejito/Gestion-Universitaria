package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Curso(
                String id,
                @JsonProperty("courseCode") String codigoCurso,
                @JsonProperty("name") String nombre,
                @JsonProperty("description") String descripcion,
                @JsonProperty("department") String departamento,
                @JsonProperty("credits") int creditos,
                @JsonProperty("capacity") int capacidad,
                @JsonProperty("professorId") String profesorId,
                @JsonProperty("semester") String semestre,
                @JsonProperty("schedule") String horario,
                @JsonProperty("room") String salon,
                @JsonProperty("prerequisites") List<String> prerequisitos,
                String status) {
}
