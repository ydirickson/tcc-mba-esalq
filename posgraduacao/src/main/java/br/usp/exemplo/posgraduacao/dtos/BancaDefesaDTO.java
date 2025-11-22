package br.usp.exemplo.posgraduacao.dtos;

import java.time.LocalDateTime;

import br.usp.exemplo.posgraduacao.entidades.enums.ResultadoBanca;
import br.usp.exemplo.posgraduacao.entidades.enums.TipoBanca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BancaDefesaDTO {
    private Long id;
    private TipoBanca tipo;
    private Long projetoId;
    private Long alunoId;
    private Long orientadorId;
    private String membros;
    private LocalDateTime dataHora;
    private String local;
    private String linkOnline;
    private ResultadoBanca resultado;
    private String ata;
}
