package br.usp.exemplo.posgraduacao.servicos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import br.usp.exemplo.posgraduacao.dtos.CursoDTO;
import br.usp.exemplo.posgraduacao.dtos.mappers.DTOMapper;
import br.usp.exemplo.posgraduacao.entidades.Curso;
import br.usp.exemplo.posgraduacao.entidades.enums.Modalidade;
import br.usp.exemplo.posgraduacao.entidades.enums.NivelPosGraduacao;
import br.usp.exemplo.posgraduacao.entidades.enums.StatusOfertaCurso;
import br.usp.exemplo.posgraduacao.forms.CursoForm;
import br.usp.exemplo.posgraduacao.repositorios.CursoRepositorio;

@ExtendWith(MockitoExtension.class)
class CursoServicoTest {

    @Mock
    private CursoRepositorio cursoRepositorio;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private CursoServico cursoServico;

    @Captor
    private ArgumentCaptor<Curso> cursoCaptor;

    @Test
    void listarTodos_deveRetornarListaDeDtos() {
        List<Curso> entidades = List.of(
                criarCurso(1L, "Mestrado em Economia", NivelPosGraduacao.MESTRADO, "Economia", "Desenvolvimento Econômico",
                        Modalidade.PRESENCIAL, 2400, "Mestre em Economia", StatusOfertaCurso.ABERTO)
        );
        List<CursoDTO> dtos = List.of(new CursoDTO());

        when(cursoRepositorio.findAll()).thenReturn(entidades);
        when(dtoMapper.toCursoDto(entidades)).thenReturn(dtos);

        assertThat(cursoServico.listarTodos()).isEqualTo(dtos);
        verify(cursoRepositorio).findAll();
    }

    @Test
    void buscarPorId_quandoEncontrarDeveRetornarDto() {
        Curso curso = criarCurso(2L, "Doutorado em Administração", NivelPosGraduacao.DOUTORADO,
                "Administração", "Gestão Estratégica", Modalidade.PRESENCIAL, 3600,
                "Doutor em Administração", StatusOfertaCurso.ABERTO);
        CursoDTO dto = new CursoDTO();

        when(cursoRepositorio.findById(2L)).thenReturn(Optional.of(curso));
        when(dtoMapper.toCursoDto(curso)).thenReturn(dto);

        assertThat(cursoServico.buscarPorId(2L)).isEqualTo(dto);
    }

    @Test
    void buscarPorId_quandoNaoEncontrarDeveLancarExcecao() {
        when(cursoRepositorio.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursoServico.buscarPorId(9L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Curso não encontrado");
    }

    @Test
    void salvar_devePersistirCurso() {
        CursoForm form = new CursoForm();
        form.setNome("Mestrado em Economia");
        form.setNivel(NivelPosGraduacao.MESTRADO);
        form.setAreaConhecimento("Economia");
        form.setLinhaPesquisa("Economia Aplicada");
        form.setModalidade(Modalidade.PRESENCIAL);
        form.setCargaHorariaTotal(2400);
        form.setTitulacaoConferida("Mestre em Economia");
        form.setStatusOferta(StatusOfertaCurso.ABERTO);

        Curso salvo = criarCurso(3L, form.getNome(), form.getNivel(), form.getAreaConhecimento(),
                form.getLinhaPesquisa(), form.getModalidade(), form.getCargaHorariaTotal(),
                form.getTitulacaoConferida(), form.getStatusOferta());
        when(cursoRepositorio.save(any(Curso.class))).thenReturn(salvo);
        when(dtoMapper.toCursoDto(salvo)).thenReturn(new CursoDTO());

        CursoDTO resultado = cursoServico.salvar(form);

        verify(cursoRepositorio).save(cursoCaptor.capture());
        Curso entidade = cursoCaptor.getValue();
        assertThat(entidade.getNome()).isEqualTo(form.getNome());
        assertThat(entidade.getNivel()).isEqualTo(form.getNivel());
        assertThat(entidade.getAreaConhecimento()).isEqualTo(form.getAreaConhecimento());
        assertThat(entidade.getLinhaPesquisa()).isEqualTo(form.getLinhaPesquisa());
        assertThat(entidade.getModalidade()).isEqualTo(form.getModalidade());
        assertThat(resultado).isNotNull();
    }

    @Test
    void atualizar_deveAlterarDadosDoCurso() {
        Curso existente = criarCurso(4L, "Mestrado em Economia", NivelPosGraduacao.MESTRADO,
                "Economia", "Linha Antiga", Modalidade.PRESENCIAL, 2400,
                "Mestre", StatusOfertaCurso.ABERTO);
        CursoForm form = new CursoForm();
        form.setNome("Mestrado em Economia Atualizado");
        form.setNivel(NivelPosGraduacao.MESTRADO);
        form.setAreaConhecimento("Economia Aplicada");
        form.setLinhaPesquisa("Desenvolvimento Econômico");
        form.setModalidade(Modalidade.HIBRIDA);
        form.setCargaHorariaTotal(2600);
        form.setTitulacaoConferida("Mestre em Economia");
        form.setStatusOferta(StatusOfertaCurso.ABERTO);

        when(cursoRepositorio.findById(4L)).thenReturn(Optional.of(existente));
        when(cursoRepositorio.save(existente)).thenReturn(existente);

        CursoDTO dto = new CursoDTO();
        when(dtoMapper.toCursoDto(existente)).thenReturn(dto);

        CursoDTO resultado = cursoServico.atualizar(4L, form);

        assertThat(existente.getNome()).isEqualTo(form.getNome());
        assertThat(existente.getAreaConhecimento()).isEqualTo(form.getAreaConhecimento());
        assertThat(existente.getLinhaPesquisa()).isEqualTo(form.getLinhaPesquisa());
        assertThat(existente.getModalidade()).isEqualTo(form.getModalidade());
        assertThat(existente.getCargaHorariaTotal()).isEqualTo(form.getCargaHorariaTotal());
        assertThat(resultado).isEqualTo(dto);
    }

    @Test
    void atualizar_quandoCursoNaoExistirDeveLancarExcecao() {
        CursoForm form = new CursoForm();
        form.setNome("Mestrado em Economia");
        form.setNivel(NivelPosGraduacao.MESTRADO);

        when(cursoRepositorio.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursoServico.atualizar(100L, form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Curso não encontrado");
    }

    @Test
    void deletar_quandoCursoExistirDeveRemover() {
        when(cursoRepositorio.existsById(2L)).thenReturn(true);

        cursoServico.deletar(2L);

        verify(cursoRepositorio).deleteById(2L);
    }

    @Test
    void deletar_quandoCursoNaoExistirDeveLancarExcecao() {
        when(cursoRepositorio.existsById(2L)).thenReturn(false);

        assertThatThrownBy(() -> cursoServico.deletar(2L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Curso não encontrado");
        verify(cursoRepositorio, times(0)).deleteById(2L);
    }

    private Curso criarCurso(Long id, String nome, NivelPosGraduacao nivel, String areaConhecimento,
                             String linhaPesquisa, Modalidade modalidade, Integer cargaHoraria,
                             String titulacao, StatusOfertaCurso status) {
        Curso curso = new Curso();
        curso.setId(id);
        curso.setNome(nome);
        curso.setNivel(nivel);
        curso.setAreaConhecimento(areaConhecimento);
        curso.setLinhaPesquisa(linhaPesquisa);
        curso.setModalidade(modalidade);
        curso.setCargaHorariaTotal(cargaHoraria);
        curso.setTitulacaoConferida(titulacao);
        curso.setStatusOferta(status);
        return curso;
    }
}
