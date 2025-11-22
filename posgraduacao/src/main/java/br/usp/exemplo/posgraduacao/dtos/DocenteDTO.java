package br.usp.exemplo.posgraduacao.dtos;

import br.usp.exemplo.posgraduacao.entidades.enums.RegimeTrabalho;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusDocente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocenteDTO {
    private Long id;
    private String nome;
    private String titulacao;
    private String areaAtuacao;
    private RegimeTrabalho regimeTrabalho;
    private String email;
    private String curriculoLattes;
    private StatusDocente status;
}
