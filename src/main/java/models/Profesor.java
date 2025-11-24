package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Profesor(
                String id,
                @JsonProperty("professorId") String codigoProfesor,
                @JsonProperty("firstName") String nombre,
                @JsonProperty("lastName") String apellido,
                String email,
                @JsonProperty("phone") String telefono,
                @JsonProperty("department") String departamento,
                @JsonProperty("specialization") String especializacion,
                String status) {
}
