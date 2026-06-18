package br.com.storage_controller.dto.recurso;

import br.com.storage_controller.entity.recurso.StatusRecurso;
import java.time.LocalDateTime;

public record RecursoListagemDTO(
        Long id,
        String nome,
        String categoria,
        Double quantidade,
        Double minimo,
        Double capacidadeMaxima,
        Boolean critico,
        StatusRecurso status,
        LocalDateTime ultimaAtualizacao,
        Long setorId,
        String setorNome,
        Long baseId
) {}