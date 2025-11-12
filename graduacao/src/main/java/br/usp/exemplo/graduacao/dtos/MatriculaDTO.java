package br.usp.exemplo.graduacao.dtos;

import br.usp.exemplo.graduacao.entidades.Matricula;
import lombok.Getter;

@Getter
public class MatriculaDTO {

    private Long id;
    private Long alunoId;
    private Long cursoId;

    public MatriculaDTO(Matricula matricula) {
        this.id = matricula.getId();
        this.alunoId = matricula.getAluno().getId();
        this.cursoId = matricula.getCurso().getId();
    }

    // Getters e Setters
}
