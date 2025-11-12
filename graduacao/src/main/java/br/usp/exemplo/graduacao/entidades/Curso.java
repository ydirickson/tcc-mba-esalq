package br.usp.exemplo.graduacao.entidades;

import br.usp.exemplo.graduacao.forms.CursoForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "curso")
@NoArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sigla;

    @Column(nullable = false)
    private String nome;
    
    private String descricao;

    public Curso(CursoForm form) {
        this.sigla = form.getSigla();
        this.nome = form.getNome();
        this.descricao = form.getDescricao();
    }
}
