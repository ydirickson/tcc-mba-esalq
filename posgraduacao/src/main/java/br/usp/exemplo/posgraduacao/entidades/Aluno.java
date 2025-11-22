package br.usp.exemplo.posgraduacao.entidades;

import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoAluno;
import br.usp.exemplo.posgraduacao.forms.AlunoForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "aluno_pos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelPosGraduacao nivel;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "orientador_id")
    private Docente orientador;

    @Enumerated(EnumType.STRING)
    private SituacaoAluno situacao;

    private String ingresso;
    private String linhaPesquisa;

    public Aluno(AlunoForm form, Docente orientador) {
        this.nome = form.getNome();
        this.matricula = form.getMatricula();
        this.nivel = form.getNivel();
        this.email = form.getEmail();
        this.orientador = orientador;
        this.situacao = form.getSituacao();
        this.ingresso = form.getIngresso();
        this.linhaPesquisa = form.getLinhaPesquisa();
    }

    public void atualizarDados(AlunoForm form, Docente orientador) {
        this.nome = form.getNome();
        this.matricula = form.getMatricula();
        this.nivel = form.getNivel();
        this.email = form.getEmail();
        this.orientador = orientador;
        this.situacao = form.getSituacao();
        this.ingresso = form.getIngresso();
        this.linhaPesquisa = form.getLinhaPesquisa();
    }
}
