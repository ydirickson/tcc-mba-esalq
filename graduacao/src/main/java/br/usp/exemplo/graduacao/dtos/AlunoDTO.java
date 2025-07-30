package br.usp.exemplo.graduacao.dtos;

import br.usp.exemplo.graduacao.entidades.Aluno;
import lombok.Getter;

@Getter
public class AlunoDTO {

    private String email;
    private Long id;
    private String nome;

    public AlunoDTO(Aluno aluno){
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.email = aluno.getEmail();
    }
}
