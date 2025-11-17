package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.graduacao.dtos.AlunoDTO;
import br.usp.exemplo.graduacao.dtos.mappers.DTOMapper;
import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.forms.AlunoForm;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoServico {
  
  private final AlunoRepositorio alunoRepositorio;
  private final DTOMapper dtoMapper;

  public AlunoDTO obterPorId(Long id) {
    Aluno aluno = alunoRepositorio.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + id)
    );
    return dtoMapper.toAlunoDto(aluno);
  }

  public List<AlunoDTO> listarTodos() {
    return dtoMapper.toAlunoDto(alunoRepositorio.findAll());
  }

  public AlunoDTO salvar(AlunoForm aluno) {
    Aluno entidade = new Aluno(aluno);
    Aluno retorno = alunoRepositorio.save(entidade);
    return dtoMapper.toAlunoDto(retorno);
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
    return dtoMapper.toAlunoDto(retorno);
  }

}
