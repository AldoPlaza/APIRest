package alura.challengeApiRest.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.Optional;

public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Operation(summary = "Crear un nuevo tópico", description = "Crear un nuevo tópico en el foro.")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    @Operation(summary = "Listar todos los tópicos", description = "Obtener una lista de todos los tópicos del foro.")
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopico(@PageableDefault(size = 10) Pageable paginacion) {
        Pageable sortedByFechaCreacionAsc = PageRequest.of(paginacion.getPageNumber(), paginacion.getPageSize(), Sort.by("fechaCreacion").ascending());
        return ResponseEntity.ok(topicoRepository.findBySinRespuestaTrue(sortedByFechaCreacionAsc)
                .map(DatosListadoTopico::new));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar un tópico", description = "Actualizar los detalles de un tópico existente mediante su ID.")
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        if (!id.equals((datosActualizarTopico.id()))) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Topico> verificarTopico = topicoRepository.findById(id);
        if (verificarTopico.isEmpty()) {
            throw new EntityNotFoundException("El ID " + id + " no existe.");
        }
        Topico topico = verificarTopico.get();
        topico.actualizarTopico((datosActualizarTopico));
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getAutor(), topico.getCurso()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un tópico", description = "Eliminar un tópico existente mediante su ID.")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> verificarTopico = topicoRepository.findById(id);
        if (verificarTopico.isPresent()) {
            topicoRepository.delete(verificarTopico.get());
            return ResponseEntity.ok("Su topico fue eliminado");
        } else {
            return ResponseEntity.status(404).body("El ID " + id + " no existe");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tópico por ID", description = "Obtener los detalles de un tópico específico mediante su ID.")
    public ResponseEntity<DatosListadoTopico> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> verificarTopico = topicoRepository.findById(id);
        if (verificarTopico.isPresent()) {
            Topico topico = verificarTopico.get();
            DatosListadoTopico datosListadoTopico = new DatosListadoTopico(topico);
            return ResponseEntity.ok(datosListadoTopico);
        } else {
            throw new EntityNotFoundException("El ID " + id + " no existe.");
        }
    }
}


