package br.usp.exemplo.graduacao.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlunoForm {

    @NotBlank(message = "O nome do aluno não pode ser vazio")
    private String nome;

    @NotBlank(message = "O email do aluno não pode ser vazio")
    private String email;
   
}
