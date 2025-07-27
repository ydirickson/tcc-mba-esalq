package br.usp.exemplo.graduacao.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity(name = "matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
}

enum StatusMatricula {
    CURSANDO,
    FINALIZADO,
    CANCELADO
}
