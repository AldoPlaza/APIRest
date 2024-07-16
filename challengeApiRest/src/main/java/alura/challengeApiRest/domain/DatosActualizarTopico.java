package alura.challengeApiRest.domain;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(

        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}
