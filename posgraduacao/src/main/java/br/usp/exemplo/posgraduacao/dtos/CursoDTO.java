package br.usp.exemplo.posgraduacao.dtos;

import br.usp.exemplo.posgraduacao.entidades.enums.Modalidade;
import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusOfertaCurso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long id;
    private String nome;
    private NivelPosGraduacao nivel;
    private String areaConhecimento;
    private String linhaPesquisa;
    private Modalidade modalidade;
    private Integer cargaHorariaTotal;
    private String titulacaoConferida;
    private StatusOfertaCurso statusOferta;
}
