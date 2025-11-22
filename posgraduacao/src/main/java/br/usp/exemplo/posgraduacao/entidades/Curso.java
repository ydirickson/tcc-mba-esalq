package br.usp.exemplo.posgraduacao.entidades;

import br.usp.exemplo.posgraduacao.entidades.enums.Modalidade;
import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusOfertaCurso;
import br.usp.exemplo.posgraduacao.forms.CursoForm;
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
@Entity(name = "curso_pos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelPosGraduacao nivel;

    private String areaConhecimento;
    private String linhaPesquisa;

    @Enumerated(EnumType.STRING)
    private Modalidade modalidade;

    private Integer cargaHorariaTotal;
    private String titulacaoConferida;

    @Enumerated(EnumType.STRING)
    private StatusOfertaCurso statusOferta;

    public Curso(CursoForm form) {
        this.nome = form.getNome();
        this.nivel = form.getNivel();
        this.areaConhecimento = form.getAreaConhecimento();
        this.linhaPesquisa = form.getLinhaPesquisa();
        this.modalidade = form.getModalidade();
        this.cargaHorariaTotal = form.getCargaHorariaTotal();
        this.titulacaoConferida = form.getTitulacaoConferida();
        this.statusOferta = form.getStatusOferta();
    }
}
