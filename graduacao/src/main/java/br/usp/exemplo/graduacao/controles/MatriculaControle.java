package br.usp.exemplo.graduacao.controles;

import br.usp.exemplo.graduacao.entidades.Matricula;
import br.usp.exemplo.graduacao.servicos.MatriculaServico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaControle {
    private final MatriculaServico matriculaServico;

    public MatriculaControle(MatriculaServico matriculaServico) {
        this.matriculaServico = matriculaServico;
    }

    @GetMapping
    public List<Matricula> listarTodos() {
        return matriculaServico.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> buscarPorId(@PathVariable int id) {
        return matriculaServico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Matricula salvar(@RequestBody Matricula matricula) {
        return matriculaServico.salvar(matricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> atualizar(@PathVariable int id, @RequestBody Matricula matricula) {
        return matriculaServico.buscarPorId(id)
                .map(existing -> {
                    matricula.setId(id);
                    return ResponseEntity.ok(matriculaServico.salvar(matricula));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (matriculaServico.buscarPorId(id).isPresent()) {
            matriculaServico.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
