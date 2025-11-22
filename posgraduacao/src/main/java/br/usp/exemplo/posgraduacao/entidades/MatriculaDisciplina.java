package br.usp.exemplo.posgraduacao.entidades;

import java.math.BigDecimal;

import br.usp.exemplo.posgraduacao.entidades.enums.SituacaoMatriculaDisciplina;
import br.usp.exemplo.posgraduacao.forms.MatriculaDisciplinaForm;
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
@Entity(name = "matricula_disciplina_pos")
public class MatriculaDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    private String periodo;

    @Enumerated(EnumType.STRING)
    private SituacaoMatriculaDisciplina situacao;

    private BigDecimal nota;
    private BigDecimal frequencia;

    public MatriculaDisciplina(MatriculaDisciplinaForm form, Aluno aluno, Disciplina disciplina, Turma turma) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.turma = turma;
        this.periodo = form.getPeriodo();
        this.situacao = form.getSituacao();
        this.nota = form.getNota();
        this.frequencia = form.getFrequencia();
    }

    public void atualizarDados(MatriculaDisciplinaForm form, Aluno aluno, Disciplina disciplina, Turma turma) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.turma = turma;
        this.periodo = form.getPeriodo();
        this.situacao = form.getSituacao();
        this.nota = form.getNota();
        this.frequencia = form.getFrequencia();
    }
}
