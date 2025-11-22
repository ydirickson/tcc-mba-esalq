package br.usp.exemplo.posgraduacao.forms;

import br.usp.exemplo.posgraduacao.entidades.enums.RegimeTrabalho;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusDocente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DocenteForm {

    @NotBlank
    private String nome;

    private String titulacao;
    private String areaAtuacao;
    private RegimeTrabalho regimeTrabalho;

    @NotBlank
    @Email
    private String email;

    private String curriculoLattes;
    private StatusDocente status;
}
