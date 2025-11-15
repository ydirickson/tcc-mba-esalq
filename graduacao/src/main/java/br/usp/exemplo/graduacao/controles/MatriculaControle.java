package br.usp.exemplo.graduacao.controles;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.usp.exemplo.graduacao.dtos.MatriculaDTO;
import br.usp.exemplo.graduacao.forms.MatriculaForm;
import br.usp.exemplo.graduacao.servicos.MatriculaServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaControle {
    private final MatriculaServico matriculaServico;

    @GetMapping
    public List<MatriculaDTO> listarTodos() {
        return matriculaServico.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDTO> buscarPorId(@PathVariable Long id) {
        MatriculaDTO dto = matriculaServico.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public MatriculaDTO salvar(@RequestBody @Valid MatriculaForm matricula) {
        return matriculaServico.salvar(matricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MatriculaForm matricula) {
        MatriculaDTO dto = matriculaServico.atualizar(id, matricula);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        matriculaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
