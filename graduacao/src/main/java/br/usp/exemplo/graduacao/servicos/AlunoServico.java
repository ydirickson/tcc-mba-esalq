package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoServico {
  
  private final AlunoRepositorio alunoRepositorio;

  public Aluno obterPorId(Long id) {
    return alunoRepositorio.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + id)
    );
  }

  public List<Aluno> listarTodos() {
    return alunoRepositorio.findAll();
  }

  public Aluno salvar(Aluno aluno) {
    return alunoRepositorio.save(aluno);
  }

  public void deletar(Long id) {
    alunoRepositorio.deleteById(id);
  }

}
