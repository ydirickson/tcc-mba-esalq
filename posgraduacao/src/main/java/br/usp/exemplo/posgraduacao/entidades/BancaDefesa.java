package br.usp.exemplo.posgraduacao.entidades;

import java.time.LocalDateTime;

import br.usp.exemplo.posgraduacao.entidades.enums.ResultadoBanca;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoBanca;
import br.usp.exemplo.posgraduacao.forms.BancaDefesaForm;
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
@Entity(name = "banca_defesa_pos")
public class BancaDefesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoBanca tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "projeto_id")
    private ProjetoPesquisa projeto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orientador_id")
    private Docente orientador;

    @Column(length = 1000)
    private String membros;

    private LocalDateTime dataHora;
    private String local;
    private String linkOnline;

    @Enumerated(EnumType.STRING)
    private ResultadoBanca resultado;

    private String ata;

    public BancaDefesa(BancaDefesaForm form, ProjetoPesquisa projeto, Aluno aluno, Docente orientador) {
        this.tipo = form.getTipo();
        this.projeto = projeto;
        this.aluno = aluno;
        this.orientador = orientador;
        this.membros = form.getMembros();
        this.dataHora = form.getDataHora();
        this.local = form.getLocal();
        this.linkOnline = form.getLinkOnline();
        this.resultado = form.getResultado();
        this.ata = form.getAta();
    }

    public void atualizarDados(BancaDefesaForm form, ProjetoPesquisa projeto, Aluno aluno, Docente orientador) {
        this.tipo = form.getTipo();
        this.projeto = projeto;
        this.aluno = aluno;
        this.orientador = orientador;
        this.membros = form.getMembros();
        this.dataHora = form.getDataHora();
        this.local = form.getLocal();
        this.linkOnline = form.getLinkOnline();
        this.resultado = form.getResultado();
        this.ata = form.getAta();
    }
}
