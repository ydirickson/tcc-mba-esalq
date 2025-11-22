package br.usp.exemplo.posgraduacao.forms;

import br.usp.exemplo.posgraduacao.entidades.enums.Modalidade;
import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusOfertaCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CursoForm {

    @NotBlank(message = "O nome do curso é obrigatório")
    private String nome;

    @NotNull
    private NivelPosGraduacao nivel;

    private String areaConhecimento;
    private String linhaPesquisa;
    private Modalidade modalidade;
    private Integer cargaHorariaTotal;
    private String titulacaoConferida;
    private StatusOfertaCurso statusOferta;
}
