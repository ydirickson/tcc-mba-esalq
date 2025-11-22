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

import br.usp.exemplo.posgraduacao.dtos.CursoDTO;
import br.usp.exemplo.posgraduacao.forms.CursoForm;
import br.usp.exemplo.posgraduacao.servicos.CursoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
@Slf4j
public class CursoControle {

    private final CursoServico cursoServico;

    @GetMapping({"","/"})
    public List<CursoDTO> listarTodos() {
        log.info("Listando todos os cursos de pós-graduação");
        return cursoServico.listarTodos();
    }

    @GetMapping("/{id}")
    public CursoDTO buscarPorId(@PathVariable Long id) {
        log.info("Buscando curso de pós-graduação com ID: {}", id);
        return cursoServico.buscarPorId(id);
    }

    @PostMapping
    public CursoDTO salvar(@RequestBody @Valid CursoForm curso) {
        log.info("Salvando novo curso de pós-graduação: {}", curso);
        return cursoServico.salvar(curso);
    }

    @PutMapping("/{id}")
    public CursoDTO atualizar(@PathVariable Long id, @RequestBody @Valid CursoForm curso) {
        log.info("Atualizando curso de pós-graduação com ID: {}", id);
        return cursoServico.atualizar(id, curso);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        log.info("Deletando curso de pós-graduação com ID: {}", id);
        cursoServico.deletar(id);
    }
}
