package br.usp.exemplo.posgraduacao.dtos;

import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoAluno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {
    private Long id;
    private String nome;
    private String matricula;
    private NivelPosGraduacao nivel;
    private String email;
    private Long orientadorId;
    private SituacaoAluno situacao;
    private String ingresso;
    private String linhaPesquisa;
}
