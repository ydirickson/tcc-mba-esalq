package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.graduacao.dtos.MatriculaDTO;
import br.usp.exemplo.graduacao.dtos.mappers.DTOMapper;
import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.entidades.Matricula;
import br.usp.exemplo.graduacao.forms.MatriculaForm;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;
import br.usp.exemplo.graduacao.repositorios.MatriculaRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatriculaServico {
    private final MatriculaRepositorio matriculaRepositorio;
    private final DTOMapper dtoMapper;
    private final AlunoRepositorio alunoRepositorio;
    private final CursoRepositorio cursoRepositorio;

    public List<MatriculaDTO> listarTodos() {
        return dtoMapper.toMatriculaDto(matriculaRepositorio.findAll());
    }

    public MatriculaDTO buscarPorId(Long id) {
        Matricula matricula = matriculaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada com o ID: " + id));
        return dtoMapper.toMatriculaDto(matricula);
    }

    public MatriculaDTO salvar(MatriculaForm matricula) {
        Aluno aluno = buscarAluno(matricula.getAlunoId());
        Curso curso = buscarCurso(matricula.getCursoId());
        Matricula novo = new Matricula(aluno, curso);
        return dtoMapper.toMatriculaDto(matriculaRepositorio.save(novo));
    }

    public MatriculaDTO atualizar(Long id, MatriculaForm form) {
        Matricula matriculaExistente = matriculaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada com o ID: " + id));
        Aluno aluno = buscarAluno(form.getAlunoId());
        Curso curso = buscarCurso(form.getCursoId());
        matriculaExistente.atualizarDados(aluno, curso);
        return dtoMapper.toMatriculaDto(matriculaRepositorio.save(matriculaExistente));
    }

    public void deletar(Long id) {
        if(!matriculaRepositorio.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada com o ID: " + id);
        }
        matriculaRepositorio.deleteById(id);
    }

    private Aluno buscarAluno(Long alunoId) {
        return alunoRepositorio.findById(alunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + alunoId));
    }

    private Curso buscarCurso(Long cursoId) {
        return cursoRepositorio.findById(cursoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado com o ID: " + cursoId));
    }
}
