package br.usp.exemplo.graduacao.controles;

import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.servicos.CursoServico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoControle {
    private final CursoServico cursoServico;

    public CursoControle(CursoServico cursoServico) {
        this.cursoServico = cursoServico;
    }

    @GetMapping
    public List<Curso> listarTodos() {
        return cursoServico.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable int id) {
        return cursoServico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Curso salvar(@RequestBody Curso curso) {
        return cursoServico.salvar(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable int id, @RequestBody Curso curso) {
        return cursoServico.buscarPorId(id)
                .map(existing -> {
                    curso.setId(id);
                    return ResponseEntity.ok(cursoServico.salvar(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (cursoServico.buscarPorId(id).isPresent()) {
            cursoServico.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
