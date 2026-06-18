package br.com.storage_controller.dto.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BaseCadastroDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome
) {}