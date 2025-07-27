package br.usp.exemplo.graduacao.servicos;

import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServico {
    private final CursoRepositorio cursoRepositorio;

    public CursoServico(CursoRepositorio cursoRepositorio) {
        this.cursoRepositorio = cursoRepositorio;
    }

    public List<Curso> listarTodos() {
        return cursoRepositorio.findAll();
    }

    public Optional<Curso> buscarPorId(int id) {
        return cursoRepositorio.findById(id);
    }

    public Curso salvar(Curso curso) {
        return cursoRepositorio.save(curso);
    }

    public void deletar(int id) {
        cursoRepositorio.deleteById(id);
    }
}
