package br.usp.exemplo.graduacao.entidades;

import java.time.LocalDate;

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

    public Matricula(Aluno aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
        this.dataMatricula = LocalDate.now();
        this.status = StatusMatricula.CURSANDO;
    }

    public void atualizarDados(Aluno aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
    }
}

enum StatusMatricula {
    CURSANDO,
    FINALIZADO,
    CANCELADO
}
