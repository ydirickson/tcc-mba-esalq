package br.usp.exemplo.graduacao.controles;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.usp.exemplo.graduacao.dtos.AlunoDTO;
import br.usp.exemplo.graduacao.forms.AlunoForm;
import br.usp.exemplo.graduacao.servicos.AlunoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoControle {
  
  private final AlunoServico alunoServico;

  @GetMapping
  public List<AlunoDTO> listarTodos() {
    return alunoServico.listarTodos();
  }

  @GetMapping("/{id}")
  public AlunoDTO obterPorId(@PathVariable Long id) {
    return alunoServico.obterPorId(id);
  }

  @PostMapping
  public AlunoDTO salvar(@RequestBody @Valid AlunoForm aluno) {
    return alunoServico.salvar(aluno);
  }

  @PutMapping("/{id}")
  public AlunoDTO atualizar(@PathVariable Long id, @RequestBody AlunoForm aluno) {
    return alunoServico.atualizar(id, aluno);
  }

  @DeleteMapping("/{id}")
  public void deletar(@PathVariable Long id) {
    alunoServico.deletar(id);
  }

}
