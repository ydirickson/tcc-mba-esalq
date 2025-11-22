package br.usp.exemplo.posgraduacao.entidades;

import br.usp.exemplo.posgraduacao.entidades.enums.StatusDisciplina;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoDisciplina;
import br.usp.exemplo.posgraduacao.forms.DisciplinaForm;
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
@Entity(name = "disciplina_pos")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    private String ementa;
    private Integer creditos;

    @Enumerated(EnumType.STRING)
    private TipoDisciplina tipo;

    private String preRequisitos;

    @Enumerated(EnumType.STRING)
    private StatusDisciplina status;

    public Disciplina(DisciplinaForm form, Curso curso) {
        this.curso = curso;
        this.codigo = form.getCodigo();
        this.nome = form.getNome();
        this.ementa = form.getEmenta();
        this.creditos = form.getCreditos();
        this.tipo = form.getTipo();
        this.preRequisitos = form.getPreRequisitos();
        this.status = form.getStatus();
    }

    public void atualizarDados(DisciplinaForm form, Curso curso) {
        this.curso = curso;
        this.codigo = form.getCodigo();
        this.nome = form.getNome();
        this.ementa = form.getEmenta();
        this.creditos = form.getCreditos();
        this.tipo = form.getTipo();
        this.preRequisitos = form.getPreRequisitos();
        this.status = form.getStatus();
    }
}
