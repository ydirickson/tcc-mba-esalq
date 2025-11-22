package br.usp.exemplo.posgraduacao.forms;

import br.usp.exemplo.posgraduacao.entidades.enums.StatusTurma;
import br.usp.exemplo.posgraduacao.entidades.enums.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TurmaForm {

    @NotNull
    private Long cursoId;

    @NotBlank
    private String codigo;

    private String periodo;
    private Turno turno;
    private Integer capacidade;
    private String docentesResponsaveis;
    private StatusTurma status;
}
