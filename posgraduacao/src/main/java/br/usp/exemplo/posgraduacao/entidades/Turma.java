package br.usp.exemplo.posgraduacao.entidades;

import br.usp.exemplo.posgraduacao.entidades.enums.StatusTurma;
import br.usp.exemplo.posgraduacao.entidades.enums.Turno;
import br.usp.exemplo.posgraduacao.forms.TurmaForm;
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
@Entity(name = "turma_pos")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false)
    private String codigo;

    private String periodo;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    private Integer capacidade;
    private String docentesResponsaveis;

    @Enumerated(EnumType.STRING)
    private StatusTurma status;

    public Turma(TurmaForm form, Curso curso) {
        this.curso = curso;
        this.codigo = form.getCodigo();
        this.periodo = form.getPeriodo();
        this.turno = form.getTurno();
        this.capacidade = form.getCapacidade();
        this.docentesResponsaveis = form.getDocentesResponsaveis();
        this.status = form.getStatus();
    }

    public void atualizarDados(TurmaForm form, Curso curso) {
        this.curso = curso;
        this.codigo = form.getCodigo();
        this.periodo = form.getPeriodo();
        this.turno = form.getTurno();
        this.capacidade = form.getCapacidade();
        this.docentesResponsaveis = form.getDocentesResponsaveis();
        this.status = form.getStatus();
    }
}
