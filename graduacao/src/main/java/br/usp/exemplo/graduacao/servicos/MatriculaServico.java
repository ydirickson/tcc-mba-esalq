package br.usp.exemplo.graduacao.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.graduacao.dtos.MatriculaDTO;
import br.usp.exemplo.graduacao.entidades.Matricula;
import br.usp.exemplo.graduacao.forms.MatriculaForm;
import br.usp.exemplo.graduacao.repositorios.MatriculaRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatriculaServico {
    private final MatriculaRepositorio matriculaRepositorio;

    public List<MatriculaDTO> listarTodos() {
        return matriculaRepositorio.findAll().stream()
                .map(MatriculaDTO::new)
                .toList();
    }

    public MatriculaDTO buscarPorId(Long id) {
        return matriculaRepositorio.findById(id)
                .map(MatriculaDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada com o ID: " + id));
    }

    public MatriculaDTO salvar(MatriculaForm matricula) {
        Matricula novo = new Matricula(matricula);
        return new MatriculaDTO(matriculaRepositorio.save(novo));
    }

    public MatriculaDTO atualizar(Long id, MatriculaForm form) {
        Matricula matriculaExistente = matriculaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada com o ID: " + id));
        matriculaExistente.atualizarDados(form);
        return new MatriculaDTO(matriculaRepositorio.save(matriculaExistente));
    }

    public void deletar(Long id) {
        if(!matriculaRepositorio.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada com o ID: " + id);
        }
        matriculaRepositorio.deleteById(id);
    }
}
