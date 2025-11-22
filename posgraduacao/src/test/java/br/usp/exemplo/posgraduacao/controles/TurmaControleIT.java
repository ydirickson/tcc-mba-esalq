package br.usp.exemplo.posgraduacao.controles;

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

import br.usp.exemplo.posgraduacao.entidades.Curso;
import br.usp.exemplo.posgraduacao.entidades.Turma;
import br.usp.exemplo.posgraduacao.entidades.enums.Modalidade;
import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusOfertaCurso;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusTurma;
import br.usp.exemplo.posgraduacao.entidades.enums.Turno;
import br.usp.exemplo.posgraduacao.forms.TurmaForm;
import br.usp.exemplo.posgraduacao.repositorios.CursoRepositorio;
import br.usp.exemplo.posgraduacao.repositorios.TurmaRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TurmaControleIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TurmaRepositorio turmaRepositorio;

    @Autowired
    private CursoRepositorio cursoRepositorio;

    @BeforeEach
    void limparBanco() {
        turmaRepositorio.deleteAll();
        cursoRepositorio.deleteAll();
    }

    @Test
    void deveCriarTurma() throws Exception {
        Curso curso = salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO);

        TurmaForm form = new TurmaForm();
        form.setCursoId(curso.getId());
        form.setCodigo("TURMA-2024-1");
        form.setPeriodo("2024/1");
        form.setTurno(Turno.NOITE);
        form.setCapacidade(30);
        form.setDocentesResponsaveis("Dr. Silva, Dra. Santos");
        form.setStatus(StatusTurma.ABERTA);

        mockMvc.perform(post("/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.codigo", is("TURMA-2024-1")))
                .andExpect(jsonPath("$.periodo", is("2024/1")))
                .andExpect(jsonPath("$.turno", is("NOITE")))
                .andExpect(jsonPath("$.capacidade", is(30)))
                .andExpect(jsonPath("$.docentesResponsaveis", is("Dr. Silva, Dra. Santos")))
                .andExpect(jsonPath("$.status", is("ABERTA")))
                        .andExpect(jsonPath("$.cursoId", is(curso.getId().intValue())));
    }

    @Test
    void deveListarTurmas() throws Exception {
        Curso curso = salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO);
        salvarTurma(curso, "TURMA-2024-1", "2024/1", Turno.NOITE, 30, 
                "Dr. Silva", StatusTurma.ABERTA);

        mockMvc.perform(get("/turmas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].codigo", is("TURMA-2024-1")))
                .andExpect(jsonPath("$[0].periodo", is("2024/1")));
    }

    @Test
    void deveBuscarTurmaPorId() throws Exception {
        Curso curso = salvarCurso("Doutorado em Administração", NivelPosGraduacao.DOUTORADO);
        Turma turma = salvarTurma(curso, "TURMA-2024-2", "2024/2", Turno.MANHA, 25, 
                "Dr. Oliveira", StatusTurma.ABERTA);

        mockMvc.perform(get("/turmas/{id}", turma.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(turma.getId().intValue())))
                .andExpect(jsonPath("$.codigo", is("TURMA-2024-2")))
                .andExpect(jsonPath("$.turno", is("MANHA")));
    }

    @Test
    void deveRetornarNotFoundQuandoTurmaNaoExistir() throws Exception {
        mockMvc.perform(get("/turmas/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarTurma() throws Exception {
        Curso cursoAntigo = salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO);
        Curso cursoNovo = salvarCurso("Doutorado em Economia", NivelPosGraduacao.DOUTORADO);
        Turma turma = salvarTurma(cursoAntigo, "TURMA-2024-1", "2024/1", Turno.NOITE, 
                30, "Dr. Silva", StatusTurma.ABERTA);

        TurmaForm form = new TurmaForm();
        form.setCursoId(cursoNovo.getId());
        form.setCodigo("TURMA-2024-1-ATUALIZADA");
        form.setPeriodo("2024/2");
        form.setTurno(Turno.TARDE);
        form.setCapacidade(40);
        form.setDocentesResponsaveis("Dr. Santos, Dra. Oliveira");
        form.setStatus(StatusTurma.FECHADA);

        mockMvc.perform(put("/turmas/{id}", turma.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is("TURMA-2024-1-ATUALIZADA")))
                .andExpect(jsonPath("$.periodo", is("2024/2")))
                .andExpect(jsonPath("$.turno", is("TARDE")))
                .andExpect(jsonPath("$.capacidade", is(40)))
                .andExpect(jsonPath("$.docentesResponsaveis", is("Dr. Santos, Dra. Oliveira")))
                .andExpect(jsonPath("$.status", is("FECHADA")))
                        .andExpect(jsonPath("$.cursoId", is(cursoNovo.getId().intValue())));
    }

    @Test
    void deveRetornarNotFoundAoAtualizarTurmaInexistente() throws Exception {
        Curso curso = salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO);
        
        TurmaForm form = new TurmaForm();
        form.setCursoId(curso.getId());
        form.setCodigo("TURMA-2024-1");

        mockMvc.perform(put("/turmas/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarNotFoundAoCriarTurmaComCursoInexistente() throws Exception {
        TurmaForm form = new TurmaForm();
        form.setCursoId(999L);
        form.setCodigo("TURMA-2024-1");
        form.setPeriodo("2024/1");

        mockMvc.perform(post("/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarTurma() throws Exception {
        Curso curso = salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO);
        Turma turma = salvarTurma(curso, "TURMA-2024-1", "2024/1", Turno.NOITE, 
                30, "Dr. Silva", StatusTurma.ABERTA);

        mockMvc.perform(delete("/turmas/{id}", turma.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/turmas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void deveRetornarNotFoundAoDeletarTurmaInexistente() throws Exception {
        mockMvc.perform(delete("/turmas/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarBadRequestQuandoCamposObrigatoriosEstiveremAusentes() throws Exception {
        TurmaForm form = new TurmaForm();
        // cursoId e codigo são obrigatórios, não foram preenchidos

        mockMvc.perform(post("/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest());
    }

    private Curso salvarCurso(String nome, NivelPosGraduacao nivel) {
        Curso curso = new Curso();
        curso.setNome(nome);
        curso.setNivel(nivel);
        curso.setAreaConhecimento("Ciências Sociais Aplicadas");
        curso.setLinhaPesquisa("Pesquisa");
        curso.setModalidade(Modalidade.PRESENCIAL);
        curso.setCargaHorariaTotal(2400);
        curso.setTitulacaoConferida("Mestre");
        curso.setStatusOferta(StatusOfertaCurso.ABERTO);
        return cursoRepositorio.save(curso);
    }

    private Turma salvarTurma(Curso curso, String codigo, String periodo, Turno turno,
                              Integer capacidade, String docentes, StatusTurma status) {
        Turma turma = new Turma();
        turma.setCurso(curso);
        turma.setCodigo(codigo);
        turma.setPeriodo(periodo);
        turma.setTurno(turno);
        turma.setCapacidade(capacidade);
        turma.setDocentesResponsaveis(docentes);
        turma.setStatus(status);
        return turmaRepositorio.save(turma);
    }
}
