package br.usp.exemplo.posgraduacao.entidades;

import java.time.LocalDate;

import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoProjeto;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoProjeto;
import br.usp.exemplo.posgraduacao.forms.ProjetoPesquisaForm;
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
@Entity(name = "projeto_pesquisa")
public class ProjetoPesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orientador_id")
    private Docente orientador;

    @ManyToOne
    @JoinColumn(name = "coorientador_id")
    private Docente coorientador;

    private String linhaPesquisa;

    @Enumerated(EnumType.STRING)
    private TipoProjeto tipo;

    @Enumerated(EnumType.STRING)
    private SituacaoProjeto situacao;

    private LocalDate dataInicio;
    private LocalDate prazo;
    private String palavrasChave;
    private String resumo;

    public ProjetoPesquisa(ProjetoPesquisaForm form, Aluno aluno, Docente orientador, Docente coorientador) {
        this.titulo = form.getTitulo();
        this.aluno = aluno;
        this.orientador = orientador;
        this.coorientador = coorientador;
        this.linhaPesquisa = form.getLinhaPesquisa();
        this.tipo = form.getTipo();
        this.situacao = form.getSituacao();
        this.dataInicio = form.getDataInicio();
        this.prazo = form.getPrazo();
        this.palavrasChave = form.getPalavrasChave();
        this.resumo = form.getResumo();
    }

    public void atualizarDados(ProjetoPesquisaForm form, Aluno aluno, Docente orientador, Docente coorientador) {
        this.titulo = form.getTitulo();
        this.aluno = aluno;
        this.orientador = orientador;
        this.coorientador = coorientador;
        this.linhaPesquisa = form.getLinhaPesquisa();
        this.tipo = form.getTipo();
        this.situacao = form.getSituacao();
        this.dataInicio = form.getDataInicio();
        this.prazo = form.getPrazo();
        this.palavrasChave = form.getPalavrasChave();
        this.resumo = form.getResumo();
    }
}
