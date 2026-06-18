package br.com.storage_controller.dto.alerta;

import java.time.LocalDateTime;

public record AlertaListagemDTO(
        Long id,
        Long recursoId,
        String recursoNome,
        Long setorId,
        String setorNome,
        Long baseId,
        String mensagem,
        String nivel,
        Boolean resolvido,
        LocalDateTime dataAlerta
) {}