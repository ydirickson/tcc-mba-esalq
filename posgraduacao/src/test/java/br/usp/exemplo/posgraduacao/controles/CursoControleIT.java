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
import br.usp.exemplo.posgraduacao.entidades.enums.Modalidade;
import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusOfertaCurso;
import br.usp.exemplo.posgraduacao.forms.CursoForm;
import br.usp.exemplo.posgraduacao.repositorios.CursoRepositorio;
import br.usp.exemplo.posgraduacao.repositorios.TurmaRepositorio;

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
    private TurmaRepositorio turmaRepositorio;

    @BeforeEach
    void limparBanco() {
        turmaRepositorio.deleteAll();
        cursoRepositorio.deleteAll();
    }

    @Test
    void deveCriarCurso() throws Exception {
        CursoForm form = new CursoForm();
        form.setNome("Mestrado em Economia");
        form.setNivel(NivelPosGraduacao.MESTRADO);
        form.setAreaConhecimento("Ciências Sociais Aplicadas");
        form.setLinhaPesquisa("Economia Aplicada");
        form.setModalidade(Modalidade.PRESENCIAL);
        form.setCargaHorariaTotal(2400);
        form.setTitulacaoConferida("Mestre em Economia");
        form.setStatusOferta(StatusOfertaCurso.ABERTO);

        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Mestrado em Economia")))
                .andExpect(jsonPath("$.nivel", is("MESTRADO")))
                .andExpect(jsonPath("$.areaConhecimento", is("Ciências Sociais Aplicadas")))
                .andExpect(jsonPath("$.linhaPesquisa", is("Economia Aplicada")))
                .andExpect(jsonPath("$.modalidade", is("PRESENCIAL")))
                .andExpect(jsonPath("$.cargaHorariaTotal", is(2400)))
                .andExpect(jsonPath("$.titulacaoConferida", is("Mestre em Economia")))
                .andExpect(jsonPath("$.statusOferta", is("ABERTO")));
    }

    @Test
    void deveListarCursos() throws Exception {
        salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO, "Ciências Sociais", 
                "Economia Aplicada", Modalidade.PRESENCIAL, 2400, "Mestre", StatusOfertaCurso.ABERTO);

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Mestrado em Economia")))
                .andExpect(jsonPath("$[0].nivel", is("MESTRADO")));
    }

    @Test
    void deveBuscarCursoPorId() throws Exception {
        Curso curso = salvarCurso("Doutorado em Administração", NivelPosGraduacao.DOUTORADO, 
                "Administração", "Gestão Estratégica", Modalidade.PRESENCIAL, 3600, 
                "Doutor em Administração", StatusOfertaCurso.ABERTO);

        mockMvc.perform(get("/cursos/{id}", curso.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(curso.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("Doutorado em Administração")))
                .andExpect(jsonPath("$.nivel", is("DOUTORADO")));
    }

    @Test
    void deveRetornarNotFoundQuandoCursoNaoExistir() throws Exception {
        mockMvc.perform(get("/cursos/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarCurso() throws Exception {
        Curso curso = salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO, 
                "Ciências Sociais", "Economia Antiga", Modalidade.PRESENCIAL, 2400, 
                "Mestre", StatusOfertaCurso.ABERTO);

        CursoForm form = new CursoForm();
        form.setNome("Mestrado em Economia Aplicada");
        form.setNivel(NivelPosGraduacao.MESTRADO);
        form.setAreaConhecimento("Ciências Sociais Aplicadas");
        form.setLinhaPesquisa("Desenvolvimento Econômico");
        form.setModalidade(Modalidade.HIBRIDA);
        form.setCargaHorariaTotal(2600);
        form.setTitulacaoConferida("Mestre em Economia");
        form.setStatusOferta(StatusOfertaCurso.ABERTO);

        mockMvc.perform(put("/cursos/{id}", curso.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Mestrado em Economia Aplicada")))
                .andExpect(jsonPath("$.linhaPesquisa", is("Desenvolvimento Econômico")))
                .andExpect(jsonPath("$.modalidade", is("HIBRIDA")))
                .andExpect(jsonPath("$.cargaHorariaTotal", is(2600)));
    }

    @Test
    void deveRetornarNotFoundAoAtualizarCursoInexistente() throws Exception {
        CursoForm form = new CursoForm();
        form.setNome("Mestrado em Economia");
        form.setNivel(NivelPosGraduacao.MESTRADO);

        mockMvc.perform(put("/cursos/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarCurso() throws Exception {
        Curso curso = salvarCurso("Mestrado em Economia", NivelPosGraduacao.MESTRADO, 
                "Ciências Sociais", "Economia Aplicada", Modalidade.PRESENCIAL, 2400, 
                "Mestre", StatusOfertaCurso.ABERTO);

        mockMvc.perform(delete("/cursos/{id}", curso.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void deveRetornarNotFoundAoDeletarCursoInexistente() throws Exception {
        mockMvc.perform(delete("/cursos/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarBadRequestQuandoCamposObrigatoriosEstiveremAusentes() throws Exception {
        CursoForm form = new CursoForm();
        // Nome e nível são obrigatórios, não foram preenchidos

        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest());
    }

    private Curso salvarCurso(String nome, NivelPosGraduacao nivel, String areaConhecimento, 
                              String linhaPesquisa, Modalidade modalidade, Integer cargaHoraria,
                              String titulacao, StatusOfertaCurso status) {
        Curso curso = new Curso();
        curso.setNome(nome);
        curso.setNivel(nivel);
        curso.setAreaConhecimento(areaConhecimento);
        curso.setLinhaPesquisa(linhaPesquisa);
        curso.setModalidade(modalidade);
        curso.setCargaHorariaTotal(cargaHoraria);
        curso.setTitulacaoConferida(titulacao);
        curso.setStatusOferta(status);
        return cursoRepositorio.save(curso);
    }
}
