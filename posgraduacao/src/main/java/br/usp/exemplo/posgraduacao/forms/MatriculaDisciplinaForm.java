package br.usp.exemplo.posgraduacao.forms;

import java.math.BigDecimal;

import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoMatriculaDisciplina;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatriculaDisciplinaForm {

    @NotNull
    private Long alunoId;

    @NotNull
    private Long disciplinaId;

    private Long turmaId;
    private String periodo;
    private SituacaoMatriculaDisciplina situacao;
    private BigDecimal nota;
    private BigDecimal frequencia;
}
