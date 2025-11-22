package br.usp.exemplo.posgraduacao.dtos;

import br.usp.exemplo.posgraduacao.entidades.enums.StatusDisciplina;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoDisciplina;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {
    private Long id;
    private Long cursoId;
    private String codigo;
    private String nome;
    private String ementa;
    private Integer creditos;
    private TipoDisciplina tipo;
    private String preRequisitos;
    private StatusDisciplina status;
}
