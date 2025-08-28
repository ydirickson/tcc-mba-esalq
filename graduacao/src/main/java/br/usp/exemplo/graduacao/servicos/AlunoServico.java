package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.graduacao.dtos.AlunoDTO;
import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.forms.AlunoForm;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoServico {
  
  private final AlunoRepositorio alunoRepositorio;

  public AlunoDTO obterPorId(Long id) {
    return alunoRepositorio.findById(id).map(AlunoDTO::new).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + id)
    );
  }

  public List<AlunoDTO> listarTodos() {
    return alunoRepositorio
            .findAll()
            .stream()
            .map(AlunoDTO::new)
            .toList();
  }

  public AlunoDTO salvar(AlunoForm aluno) {
    Aluno entidade = new Aluno(aluno);
    Aluno retorno = alunoRepositorio.save(entidade);
    return new AlunoDTO(retorno);
  }

  public void deletar(Long id) {
    if(!alunoRepositorio.existsById(id)){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + id);
    }
    alunoRepositorio.deleteById(id);
  }

  public AlunoDTO atualizar(Long id, AlunoForm aluno) {
    Aluno entidade = this.alunoRepositorio.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + id)
    );
    entidade.setEmail(aluno.getEmail());
    entidade.setNome(aluno.getNome());
    Aluno retorno = this.alunoRepositorio.save(entidade);
    return new AlunoDTO(retorno);
  }

}
