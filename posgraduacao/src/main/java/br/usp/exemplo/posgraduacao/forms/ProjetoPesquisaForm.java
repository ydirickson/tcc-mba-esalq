package br.usp.exemplo.posgraduacao.forms;

import java.time.LocalDate;

import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoProjeto;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoProjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjetoPesquisaForm {

    @NotBlank
    private String titulo;

    @NotNull
    private Long alunoId;

    @NotNull
    private Long orientadorId;

    private Long coorientadorId;
    private String linhaPesquisa;
    private TipoProjeto tipo;
    private SituacaoProjeto situacao;
    private LocalDate dataInicio;
    private LocalDate prazo;
    private String palavrasChave;
    private String resumo;
}
