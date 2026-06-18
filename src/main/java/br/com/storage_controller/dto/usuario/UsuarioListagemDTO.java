package br.com.storage_controller.dto.usuario;

public record UsuarioListagemDTO(
        Long id,
        String nome,
        String email,
        Long baseId,
        TipoUsuario tipoUsuario
) {}