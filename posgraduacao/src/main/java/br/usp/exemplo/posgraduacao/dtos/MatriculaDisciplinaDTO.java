package br.usp.exemplo.posgraduacao.dtos;

import java.math.BigDecimal;

import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoMatriculaDisciplina;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDisciplinaDTO {
    private Long id;
    private Long alunoId;
    private Long disciplinaId;
    private Long turmaId;
    private String periodo;
    private SituacaoMatriculaDisciplina situacao;
    private BigDecimal nota;
    private BigDecimal frequencia;
}
