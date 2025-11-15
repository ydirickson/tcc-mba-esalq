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

import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.forms.AlunoForm;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import br.usp.exemplo.graduacao.repositorios.MatriculaRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AlunoControleIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private MatriculaRepositorio matriculaRepositorio;

    @BeforeEach
    void limparBanco() {
        matriculaRepositorio.deleteAll();
        alunoRepositorio.deleteAll();
    }

    @Test
    void deveCriarAluno() throws Exception {
        AlunoForm form = new AlunoForm();
        form.setNome("João da Silva");
        form.setEmail("joao@usp.br");

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("João da Silva")))
                .andExpect(jsonPath("$.email", is("joao@usp.br")));
    }

    @Test
    void deveListarAlunos() throws Exception {
        salvarAluno("Maria", "maria@usp.br");

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Maria")))
                .andExpect(jsonPath("$[0].email", is("maria@usp.br")));
    }

    @Test
    void deveAtualizarAluno() throws Exception {
        Aluno aluno = salvarAluno("Pedro", "pedro@usp.br");

        AlunoForm form = new AlunoForm();
        form.setNome("Pedro Atualizado");
        form.setEmail("pedro.novo@usp.br");

        mockMvc.perform(put("/alunos/{id}", aluno.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Pedro Atualizado")))
                .andExpect(jsonPath("$.email", is("pedro.novo@usp.br")));
    }

    @Test
    void deveDeletarAluno() throws Exception {
        Aluno aluno = salvarAluno("Carlos", "carlos@usp.br");

        mockMvc.perform(delete("/alunos/{id}", aluno.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private Aluno salvarAluno(String nome, String email) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setEmail(email);
        return alunoRepositorio.save(aluno);
    }
}
