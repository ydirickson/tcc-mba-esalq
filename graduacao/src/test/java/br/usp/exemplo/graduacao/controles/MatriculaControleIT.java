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
import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.entidades.Matricula;
import br.usp.exemplo.graduacao.forms.MatriculaForm;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;
import br.usp.exemplo.graduacao.repositorios.MatriculaRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MatriculaControleIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatriculaRepositorio matriculaRepositorio;

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private CursoRepositorio cursoRepositorio;

    @BeforeEach
    void limparBanco() {
        matriculaRepositorio.deleteAll();
        alunoRepositorio.deleteAll();
        cursoRepositorio.deleteAll();
    }

    @Test
    void deveCriarMatricula() throws Exception {
        Aluno aluno = salvarAluno("Ana", "ana@usp.br");
        Curso curso = salvarCurso("ECO", "Economia", "Economia aplicada");

        MatriculaForm form = new MatriculaForm();
        form.setAlunoId(aluno.getId());
        form.setCursoId(curso.getId());

        mockMvc.perform(post("/matriculas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.alunoId", is(aluno.getId().intValue())))
                .andExpect(jsonPath("$.cursoId", is(curso.getId().intValue())));
    }

    @Test
    void deveAtualizarMatricula() throws Exception {
        Aluno alunoOriginal = salvarAluno("Pedro", "pedro@usp.br");
        Curso cursoOriginal = salvarCurso("ADM", "Administração", "Administração pública");
        Matricula matricula = matriculaRepositorio.save(new Matricula(alunoOriginal, cursoOriginal));

        Aluno novoAluno = salvarAluno("Maria", "maria@usp.br");
        Curso novoCurso = salvarCurso("ECO", "Economia", "Economia aplicada");

        MatriculaForm form = new MatriculaForm();
        form.setAlunoId(novoAluno.getId());
        form.setCursoId(novoCurso.getId());

        mockMvc.perform(put("/matriculas/{id}", matricula.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alunoId", is(novoAluno.getId().intValue())))
                .andExpect(jsonPath("$.cursoId", is(novoCurso.getId().intValue())));
    }

    @Test
    void deveListarMatriculas() throws Exception {
        Aluno aluno = salvarAluno("Clara", "clara@usp.br");
        Curso curso = salvarCurso("ECO", "Economia", "Economia aplicada");
        Matricula matricula = new Matricula(aluno, curso);
        matriculaRepositorio.save(matricula);

        mockMvc.perform(get("/matriculas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].alunoId", is(aluno.getId().intValue())));
    }

    @Test
    void deveDeletarMatricula() throws Exception {
        Aluno aluno = salvarAluno("João", "joao@usp.br");
        Curso curso = salvarCurso("ADM", "Administração", "Administração");
        Matricula matricula = matriculaRepositorio.save(new Matricula(aluno, curso));

        mockMvc.perform(delete("/matriculas/{id}", matricula.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/matriculas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private Aluno salvarAluno(String nome, String email) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setEmail(email);
        return alunoRepositorio.save(aluno);
    }

    private Curso salvarCurso(String sigla, String nome, String descricao) {
        Curso curso = new Curso();
        curso.setSigla(sigla);
        curso.setNome(nome);
        curso.setDescricao(descricao);
        return cursoRepositorio.save(curso);
    }
}
