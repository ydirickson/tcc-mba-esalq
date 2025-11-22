package br.usp.exemplo.posgraduacao.controles;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.usp.exemplo.posgraduacao.dtos.TurmaDTO;
import br.usp.exemplo.posgraduacao.forms.TurmaForm;
import br.usp.exemplo.posgraduacao.servicos.TurmaServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
@Slf4j
public class TurmaControle {

    private final TurmaServico turmaServico;

    @GetMapping({"","/"})
    public List<TurmaDTO> listarTodos() {
        log.info("Listando todas as turmas de pós-graduação");
        return turmaServico.listarTodos();
    }

    @GetMapping("/{id}")
    public TurmaDTO buscarPorId(@PathVariable Long id) {
        log.info("Buscando turma de pós-graduação com ID: {}", id);
        return turmaServico.buscarPorId(id);
    }

    @PostMapping
    public TurmaDTO salvar(@RequestBody @Valid TurmaForm turma) {
        log.info("Salvando nova turma de pós-graduação: {}", turma);
        return turmaServico.salvar(turma);
    }

    @PutMapping("/{id}")
    public TurmaDTO atualizar(@PathVariable Long id, @RequestBody @Valid TurmaForm turma) {
        log.info("Atualizando turma de pós-graduação com ID: {}", id);
        return turmaServico.atualizar(id, turma);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        log.info("Deletando turma de pós-graduação com ID: {}", id);
        turmaServico.deletar(id);
    }
}
