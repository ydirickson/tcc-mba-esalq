package br.usp.exemplo.graduacao.controles;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.servicos.AlunoServico;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoControle {
  
  private final AlunoServico alunoServico;

  @GetMapping("/")
  public List<Aluno> listarTodos() {
    return alunoServico.listarTodos();
  }

  @GetMapping("/{id}")
  public Aluno obterPorId(@PathVariable Long id) {
    return alunoServico.obterPorId(id);
  }

}
