package br.usp.exemplo.posgraduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.posgraduacao.dtos.TurmaDTO;
import br.usp.exemplo.posgraduacao.dtos.mappers.DTOMapper;
import br.usp.exemplo.posgraduacao.entidades.Curso;
import br.usp.exemplo.posgraduacao.entidades.Turma;
import br.usp.exemplo.posgraduacao.forms.TurmaForm;
import br.usp.exemplo.posgraduacao.repositorios.CursoRepositorio;
import br.usp.exemplo.posgraduacao.repositorios.TurmaRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TurmaServico {

    private final TurmaRepositorio turmaRepositorio;
    private final CursoRepositorio cursoRepositorio;
    private final DTOMapper dtoMapper;

    public List<TurmaDTO> listarTodos() {
        return dtoMapper.toTurmaDto(turmaRepositorio.findAll());
    }

    public TurmaDTO buscarPorId(Long id) {
        Turma turma = turmaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada com o ID: " + id));
        return dtoMapper.toTurmaDto(turma);
    }

    public TurmaDTO salvar(TurmaForm form) {
        Curso curso = buscarCurso(form.getCursoId());
        Turma turma = new Turma(form, curso);
        return dtoMapper.toTurmaDto(turmaRepositorio.save(turma));
    }

    public TurmaDTO atualizar(Long id, TurmaForm form) {
        Turma turma = turmaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada com o ID: " + id));
        Curso curso = buscarCurso(form.getCursoId());
        turma.atualizarDados(form, curso);
        return dtoMapper.toTurmaDto(turmaRepositorio.save(turma));
    }

    public void deletar(Long id) {
        if (!turmaRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada com o ID: " + id);
        }
        turmaRepositorio.deleteById(id);
    }

    private Curso buscarCurso(Long id) {
        return cursoRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + id));
    }
}
