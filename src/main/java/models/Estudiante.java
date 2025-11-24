package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

public record Estudiante(
        String id,
        @JsonProperty("studentId") @JsonAlias( {
                "codigoEstudiante" }) String codigoEstudiante,
        @JsonProperty("firstName") String nombre,
        @JsonProperty("lastName") String apellido,
        String email,
        @JsonProperty("phone") String telefono,
        @JsonProperty("program") String programa,
        @JsonProperty("semester") int semestre,
        String status){
}
