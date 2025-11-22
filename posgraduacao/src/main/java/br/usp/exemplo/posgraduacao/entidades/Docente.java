package br.usp.exemplo.posgraduacao.entidades;

import br.usp.exemplo.posgraduacao.entidades.enums.RegimeTrabalho;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusDocente;
import br.usp.exemplo.posgraduacao.forms.DocenteForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "docente_pos")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String titulacao;
    private String areaAtuacao;

    @Enumerated(EnumType.STRING)
    private RegimeTrabalho regimeTrabalho;

    @Column(nullable = false, unique = true)
    private String email;

    private String curriculoLattes;

    @Enumerated(EnumType.STRING)
    private StatusDocente status;

    public Docente(DocenteForm form) {
        this.nome = form.getNome();
        this.titulacao = form.getTitulacao();
        this.areaAtuacao = form.getAreaAtuacao();
        this.regimeTrabalho = form.getRegimeTrabalho();
        this.email = form.getEmail();
        this.curriculoLattes = form.getCurriculoLattes();
        this.status = form.getStatus();
    }
}
