package br.usp.exemplo.graduacao.dtos;

import br.usp.exemplo.graduacao.entidades.Curso;
import lombok.Getter;

@Getter
public class CursoDTO {

    private Long id;
    private String nome;
    private String descricao;

    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
    }

}
