package br.usp.exemplo.posgraduacao.dtos;

import java.time.LocalDate;

import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoProjeto;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoProjeto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoPesquisaDTO {
    private Long id;
    private String titulo;
    private Long alunoId;
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
