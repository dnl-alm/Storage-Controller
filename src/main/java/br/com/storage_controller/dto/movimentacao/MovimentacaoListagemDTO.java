package br.com.storage_controller.dto.movimentacao;

import br.com.storage_controller.entity.movimentacao.TipoMovimentacao;
import java.time.LocalDateTime;

public record MovimentacaoListagemDTO(
        Long id,
        Long recursoId,
        String recursoNome,
        Long setorId,
        String setorNome,
        Long usuarioId,
        String usuarioNome,
        TipoMovimentacao tipoMovimentacao,
        Double quantidade,
        String descricao,
        LocalDateTime dataMovimentacao
) {}