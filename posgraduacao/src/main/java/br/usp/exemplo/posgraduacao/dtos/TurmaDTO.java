package br.usp.exemplo.posgraduacao.dtos;

import br.usp.exemplo.posgraduacao.entidades.enums.StatusTurma;
import br.usp.exemplo.posgraduacao.entidades.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurmaDTO {
    private Long id;
    private Long cursoId;
    private String codigo;
    private String periodo;
    private Turno turno;
    private Integer capacidade;
    private String docentesResponsaveis;
    private StatusTurma status;
}
