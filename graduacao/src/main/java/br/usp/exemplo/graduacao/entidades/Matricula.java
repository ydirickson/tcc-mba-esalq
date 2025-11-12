package br.usp.exemplo.graduacao.entidades;

import java.time.LocalDate;

import br.usp.exemplo.graduacao.forms.MatriculaForm;
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
@Entity(name = "matricula")
@NoArgsConstructor
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private LocalDate dataMatricula;
    private LocalDate dataFechamento;

    @Enumerated(EnumType.STRING)
    private StatusMatricula status;

    public Matricula(MatriculaForm form) {
        // this.aluno = new Aluno(form.getAlunoId());
        // this.curso = new Curso(form.getCursoId());
        this.dataMatricula = LocalDate.now();
        this.status = StatusMatricula.CURSANDO;
    }

    public void atualizarDados(MatriculaForm form) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarDados'");
    }
}

enum StatusMatricula {
    CURSANDO,
    FINALIZADO,
    CANCELADO
}
