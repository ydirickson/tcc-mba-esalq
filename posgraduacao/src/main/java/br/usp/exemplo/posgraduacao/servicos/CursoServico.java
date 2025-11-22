package br.usp.exemplo.posgraduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.posgraduacao.dtos.CursoDTO;
import br.usp.exemplo.posgraduacao.dtos.mappers.DTOMapper;
import br.usp.exemplo.posgraduacao.entidades.Curso;
import br.usp.exemplo.posgraduacao.forms.CursoForm;
import br.usp.exemplo.posgraduacao.repositorios.CursoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CursoServico {

    private final CursoRepositorio cursoRepositorio;
    private final DTOMapper dtoMapper;

    public List<CursoDTO> listarTodos() {
        return dtoMapper.toCursoDto(cursoRepositorio.findAll());
    }

    public CursoDTO buscarPorId(Long id) {
        Curso curso = cursoRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id));
        return dtoMapper.toCursoDto(curso);
    }

    public CursoDTO salvar(CursoForm form) {
        Curso novo = new Curso(form);
        return dtoMapper.toCursoDto(cursoRepositorio.save(novo));
    }

    public CursoDTO atualizar(Long id, CursoForm form) {
        Curso curso = cursoRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id));
        curso.setNome(form.getNome());
        curso.setNivel(form.getNivel());
        curso.setAreaConhecimento(form.getAreaConhecimento());
        curso.setLinhaPesquisa(form.getLinhaPesquisa());
        curso.setModalidade(form.getModalidade());
        curso.setCargaHorariaTotal(form.getCargaHorariaTotal());
        curso.setTitulacaoConferida(form.getTitulacaoConferida());
        curso.setStatusOferta(form.getStatusOferta());
        return dtoMapper.toCursoDto(cursoRepositorio.save(curso));
    }

    public void deletar(Long id) {
        if (!cursoRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id);
        }
        cursoRepositorio.deleteById(id);
    }
}
