package br.usp.exemplo.graduacao.controles;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.forms.CursoForm;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;
import br.usp.exemplo.graduacao.repositorios.MatriculaRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CursoControleIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CursoRepositorio cursoRepositorio;

    @Autowired
    private MatriculaRepositorio matriculaRepositorio;

    @BeforeEach
    void limparBanco() {
        matriculaRepositorio.deleteAll();
        cursoRepositorio.deleteAll();
    }

    @Test
    void deveCriarCurso() throws Exception {
        CursoForm form = new CursoForm();
        form.setSigla("ECO");
        form.setNome("Economia");
        form.setDescricao("Economia aplicada");

        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Economia")))
                .andExpect(jsonPath("$.descricao", is("Economia aplicada")));
    }

    @Test
    void deveListarCursos() throws Exception {
        salvarCurso("ECO", "Economia", "Economia aplicada");

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Economia")));
    }

    @Test
    void deveAtualizarCurso() throws Exception {
        Curso curso = salvarCurso("ECO", "Economia", "Economia aplicada");

        CursoForm form = new CursoForm();
        form.setSigla("ECO");
        form.setNome("Economia Atualizada");
        form.setDescricao("Nova descrição");

        mockMvc.perform(put("/cursos/{id}", curso.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Economia Atualizada")))
                .andExpect(jsonPath("$.descricao", is("Nova descrição")));
    }

    @Test
    void deveDeletarCurso() throws Exception {
        Curso curso = salvarCurso("ECO", "Economia", "Economia aplicada");

        mockMvc.perform(delete("/cursos/{id}", curso.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private Curso salvarCurso(String sigla, String nome, String descricao) {
        Curso curso = new Curso();
        curso.setSigla(sigla);
        curso.setNome(nome);
        curso.setDescricao(descricao);
        return cursoRepositorio.save(curso);
    }
}
