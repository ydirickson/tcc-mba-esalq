package br.usp.exemplo.posgraduacao.forms;

import java.time.LocalDateTime;

import br.usp.exemplo.posgraduacao.entidades.enums.ResultadoBanca;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoBanca;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BancaDefesaForm {

    @NotNull
    private TipoBanca tipo;

    @NotNull
    private Long projetoId;

    @NotNull
    private Long alunoId;

    @NotNull
    private Long orientadorId;

    private String membros;
    private LocalDateTime dataHora;
    private String local;
    private String linkOnline;
    private ResultadoBanca resultado;
    private String ata;
}
