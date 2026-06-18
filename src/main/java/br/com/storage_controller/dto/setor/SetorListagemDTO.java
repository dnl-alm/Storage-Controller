package br.com.storage_controller.dto.setor;

public record SetorListagemDTO(
        Long id,
        Long baseId,
        String baseNome,
        String nome,
        String descricao
) {}