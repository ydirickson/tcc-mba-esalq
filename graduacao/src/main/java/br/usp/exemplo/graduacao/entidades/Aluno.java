package br.usp.exemplo.graduacao.entidades;

import br.usp.exemplo.graduacao.forms.AlunoForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "aluno")
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    public Aluno(AlunoForm aluno) {
        this.nome = aluno.getNome();
        this.email = aluno.getEmail();
    }

}
