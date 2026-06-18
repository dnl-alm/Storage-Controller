package br.com.storage_controller.entity.alerta;

import br.com.storage_controller.controller.AlertaController;
import br.com.storage_controller.dto.alerta.AlertaListagemDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AlertaAssembler
        implements RepresentationModelAssembler<AlertaListagemDTO, EntityModel<AlertaListagemDTO>> {

    @Override
    public EntityModel<AlertaListagemDTO> toModel(AlertaListagemDTO dto) {
        return EntityModel.of(dto,
                // GET / — lista todos ativos
                linkTo(methodOn(AlertaController.class)
                        .listarAtivos()).withRel("alertas-ativos"),

                // GET /recurso/{recursoId}
                linkTo(methodOn(AlertaController.class)
                        .listarPorRecurso(dto.recursoId())).withRel("alertas-recurso"),

                // GET /setor/{setorId}
                linkTo(methodOn(AlertaController.class)
                        .listarPorSetor(dto.setorId())).withRel("alertas-setor")

        );
    }
}