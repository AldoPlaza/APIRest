package alura.challengeApiRest.domain;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}
