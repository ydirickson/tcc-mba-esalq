package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.graduacao.dtos.CursoDTO;
import br.usp.exemplo.graduacao.dtos.mappers.CursoMapper;
import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.forms.CursoForm;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CursoServico {
    private final CursoRepositorio cursoRepositorio;
    private final CursoMapper cursoMapper;

    public List<CursoDTO> listarTodos() {
        return cursoMapper.toDto(cursoRepositorio.findAll());
    }

    public CursoDTO buscarPorId(Long id) {
        Curso curso = cursoRepositorio.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id));
        return cursoMapper.toDto(curso);
    }

    public CursoDTO salvar(CursoForm curso) {
        Curso entidade = new Curso(curso);
        Curso novo = this.cursoRepositorio.save(entidade);
        return cursoMapper.toDto(novo);
    }

    public CursoDTO atualizar(Long id, CursoForm curso) {
        Curso entidade = this.cursoRepositorio.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id));
        entidade.setNome(curso.getNome());
        entidade.setDescricao(curso.getDescricao());
        entidade.setSigla(curso.getSigla());
        Curso retorno = this.cursoRepositorio.save(entidade);
        return cursoMapper.toDto(retorno);
    }

    public void deletar(Long id) {
        if(!cursoRepositorio.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id);
        }
        cursoRepositorio.deleteById(id);
    }
}
