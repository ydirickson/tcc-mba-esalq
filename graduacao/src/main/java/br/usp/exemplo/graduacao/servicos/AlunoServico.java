package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoServico {
  
  private final AlunoRepositorio alunoRepositorio;

  public Aluno obterPorId(Long id) {
    return alunoRepositorio.findById(id).orElseThrow(
        () -> new RuntimeException("Aluno não encontrado com o ID: " + id)
    );
  }

  public List<Aluno> listarTodos() {
    return alunoRepositorio.findAll();
  }

}
