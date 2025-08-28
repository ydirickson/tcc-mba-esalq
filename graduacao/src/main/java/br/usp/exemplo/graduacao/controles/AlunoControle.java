package br.usp.exemplo.graduacao.controles;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.usp.exemplo.graduacao.dtos.AlunoDTO;
import br.usp.exemplo.graduacao.forms.AlunoForm;
import br.usp.exemplo.graduacao.servicos.AlunoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Slf4j
public class AlunoControle {
  
  private final AlunoServico alunoServico;

  @GetMapping
  public List<AlunoDTO> listarTodos() {
    log.info("Listando todos os alunos");
    return alunoServico.listarTodos();
  }

  @GetMapping("/{id}")
  public AlunoDTO obterPorId(@PathVariable Long id) {
    log.info("Obtendo aluno com ID: {}", id);
    return alunoServico.obterPorId(id);
  }

  @PostMapping
  public AlunoDTO salvar(@RequestBody @Valid AlunoForm aluno) {
    log.info("Salvando novo aluno: {}", aluno);
    return alunoServico.salvar(aluno);
  }

  @PutMapping("/{id}")
  public AlunoDTO atualizar(@PathVariable Long id, @RequestBody AlunoForm aluno) {
    log.info("Atualizando aluno com ID: {}", id);
    return alunoServico.atualizar(id, aluno);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    log.info("Deletando aluno com ID: {}", id);
    alunoServico.deletar(id);
  }

}
