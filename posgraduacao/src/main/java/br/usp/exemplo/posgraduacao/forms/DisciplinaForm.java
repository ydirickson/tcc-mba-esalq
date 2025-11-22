package br.usp.exemplo.posgraduacao.forms;

import br.usp.exemplo.posgraduacao.entidades.enums.StatusDisciplina;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoDisciplina;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DisciplinaForm {

    @NotNull
    private Long cursoId;

    @NotBlank
    private String codigo;

    @NotBlank
    private String nome;

    private String ementa;
    private Integer creditos;
    private TipoDisciplina tipo;
    private String preRequisitos;
    private StatusDisciplina status;
}
