package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.graduacao.dtos.CursoDTO;
import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.forms.CursoForm;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CursoServico {
    private final CursoRepositorio cursoRepositorio;

    public List<CursoDTO> listarTodos() {
        return cursoRepositorio.findAll().stream().map(CursoDTO::new).toList();
    }

    public CursoDTO buscarPorId(Long id) {
        return cursoRepositorio.findById(id).map(CursoDTO::new).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id));
    }

    public CursoDTO salvar(CursoForm curso) {
        Curso entidade = new Curso(curso);
        Curso novo = this.cursoRepositorio.save(entidade);
        return new CursoDTO(novo);
    }

    public CursoDTO atualizar(Long id, CursoForm curso) {
        Curso entidade = this.cursoRepositorio.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id));
        entidade.setNome(curso.getNome());
        entidade.setDescricao(curso.getDescricao());
        entidade.setSigla(curso.getSigla());
        Curso retorno = this.cursoRepositorio.save(entidade);
        return new CursoDTO(retorno);
    }

    public void deletar(Long id) {
        if(!cursoRepositorio.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id);
        }
        cursoRepositorio.deleteById(id);
    }
}
