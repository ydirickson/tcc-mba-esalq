package br.usp.exemplo.graduacao.forms;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatriculaForm {

    @NotNull
    private Long alunoId;

    @NotNull
    private Long cursoId;

    // Getters e Setters
}
