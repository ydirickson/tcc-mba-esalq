package br.usp.exemplo.graduacao.servicos;

import br.usp.exemplo.graduacao.entidades.Matricula;
import br.usp.exemplo.graduacao.repositorios.MatriculaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaServico {
    private final MatriculaRepositorio matriculaRepositorio;

    public MatriculaServico(MatriculaRepositorio matriculaRepositorio) {
        this.matriculaRepositorio = matriculaRepositorio;
    }

    public List<Matricula> listarTodos() {
        return matriculaRepositorio.findAll();
    }

    public Optional<Matricula> buscarPorId(int id) {
        return matriculaRepositorio.findById(id);
    }

    public Matricula salvar(Matricula matricula) {
        return matriculaRepositorio.save(matricula);
    }

    public void deletar(int id) {
        matriculaRepositorio.deleteById(id);
    }
}
