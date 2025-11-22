package br.usp.exemplo.posgraduacao.forms;

import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoAluno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlunoForm {

    @NotBlank
    private String nome;

    @NotBlank
    private String matricula;

    @NotNull
    private NivelPosGraduacao nivel;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Long orientadorId;

    private SituacaoAluno situacao;
    private String ingresso;
    private String linhaPesquisa;
}
