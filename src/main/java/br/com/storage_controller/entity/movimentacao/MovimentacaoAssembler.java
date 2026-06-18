package br.com.storage_controller.entity.movimentacao;

import br.com.storage_controller.controller.MovimentacaoController;
import br.com.storage_controller.dto.movimentacao.MovimentacaoListagemDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MovimentacaoAssembler
        implements RepresentationModelAssembler<MovimentacaoListagemDTO, EntityModel<MovimentacaoListagemDTO>> {

    @Override
    public EntityModel<MovimentacaoListagemDTO> toModel(MovimentacaoListagemDTO dto) {
        return EntityModel.of(dto,
                // GET /recurso/{recursoId}
                linkTo(methodOn(MovimentacaoController.class)
                        .listarPorRecurso(dto.recursoId())).withRel("movimentacoes-recurso"),

                // GET /usuario/{usuarioId}
                linkTo(methodOn(MovimentacaoController.class)
                        .listarPorUsuario(dto.usuarioId())).withRel("movimentacoes-usuario"),

                // GET /setor/{setorId}
                linkTo(methodOn(MovimentacaoController.class)
                        .listarPorSetor(dto.setorId())).withRel("movimentacoes-setor")
        );
    }
}