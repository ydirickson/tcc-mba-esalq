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

import br.usp.exemplo.posgraduacao.dtos.TurmaDTO;
import br.usp.exemplo.posgraduacao.dtos.mappers.DTOMapper;
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

@ExtendWith(MockitoExtension.class)
class TurmaServicoTest {

    @Mock
    private TurmaRepositorio turmaRepositorio;

    @Mock
    private CursoRepositorio cursoRepositorio;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private TurmaServico turmaServico;

    @Captor
    private ArgumentCaptor<Turma> turmaCaptor;

    @Test
    void listarTodos_deveRetornarListaDeDtos() {
        Curso curso = criarCurso(1L, "Mestrado em Economia");
        List<Turma> entidades = List.of(
                criarTurma(1L, curso, "TURMA-2024-1", "2024/1", Turno.NOITE, 30, "Dr. Silva", StatusTurma.ABERTA)
        );
        List<TurmaDTO> dtos = List.of(new TurmaDTO());

        when(turmaRepositorio.findAll()).thenReturn(entidades);
        when(dtoMapper.toTurmaDto(entidades)).thenReturn(dtos);

        assertThat(turmaServico.listarTodos()).isEqualTo(dtos);
        verify(turmaRepositorio).findAll();
    }

    @Test
    void buscarPorId_quandoEncontrarDeveRetornarDto() {
        Curso curso = criarCurso(1L, "Mestrado em Economia");
        Turma turma = criarTurma(2L, curso, "TURMA-2024-2", "2024/2", Turno.MANHA, 25, "Dr. Santos", StatusTurma.ABERTA);
        TurmaDTO dto = new TurmaDTO();

        when(turmaRepositorio.findById(2L)).thenReturn(Optional.of(turma));
        when(dtoMapper.toTurmaDto(turma)).thenReturn(dto);

        assertThat(turmaServico.buscarPorId(2L)).isEqualTo(dto);
    }

    @Test
    void buscarPorId_quandoNaoEncontrarDeveLancarExcecao() {
        when(turmaRepositorio.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> turmaServico.buscarPorId(9L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Turma não encontrada");
    }

    @Test
    void salvar_devePersistirTurma() {
        Curso curso = criarCurso(1L, "Mestrado em Economia");
        TurmaForm form = new TurmaForm();
        form.setCursoId(1L);
        form.setCodigo("TURMA-2024-3");
        form.setPeriodo("2024/1");
        form.setTurno(Turno.TARDE);
        form.setCapacidade(35);
        form.setDocentesResponsaveis("Dr. Oliveira");
        form.setStatus(StatusTurma.ABERTA);

        Turma salva = criarTurma(3L, curso, form.getCodigo(), form.getPeriodo(), form.getTurno(),
                form.getCapacidade(), form.getDocentesResponsaveis(), form.getStatus());

        when(cursoRepositorio.findById(1L)).thenReturn(Optional.of(curso));
        when(turmaRepositorio.save(any(Turma.class))).thenReturn(salva);
        when(dtoMapper.toTurmaDto(salva)).thenReturn(new TurmaDTO());

        TurmaDTO resultado = turmaServico.salvar(form);

        verify(turmaRepositorio).save(turmaCaptor.capture());
        Turma entidade = turmaCaptor.getValue();
        assertThat(entidade.getCodigo()).isEqualTo(form.getCodigo());
        assertThat(entidade.getPeriodo()).isEqualTo(form.getPeriodo());
        assertThat(entidade.getTurno()).isEqualTo(form.getTurno());
        assertThat(entidade.getCapacidade()).isEqualTo(form.getCapacidade());
        assertThat(entidade.getDocentesResponsaveis()).isEqualTo(form.getDocentesResponsaveis());
        assertThat(resultado).isNotNull();
    }

    @Test
    void salvar_quandoCursoNaoExistirDeveLancarExcecao() {
        TurmaForm form = new TurmaForm();
        form.setCursoId(99L);
        form.setCodigo("TURMA-2024-1");

        when(cursoRepositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> turmaServico.salvar(form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Curso não encontrado");
    }

    @Test
    void atualizar_deveAlterarDadosDaTurma() {
        Curso cursoAntigo = criarCurso(1L, "Mestrado em Economia");
        Curso cursoNovo = criarCurso(2L, "Doutorado em Economia");
        Turma existente = criarTurma(4L, cursoAntigo, "TURMA-2024-1", "2024/1", Turno.NOITE,
                30, "Dr. Silva", StatusTurma.ABERTA);

        TurmaForm form = new TurmaForm();
        form.setCursoId(2L);
        form.setCodigo("TURMA-2024-1-ATUALIZADA");
        form.setPeriodo("2024/2");
        form.setTurno(Turno.MANHA);
        form.setCapacidade(40);
        form.setDocentesResponsaveis("Dr. Santos, Dr. Oliveira");
        form.setStatus(StatusTurma.FECHADA);

        when(turmaRepositorio.findById(4L)).thenReturn(Optional.of(existente));
        when(cursoRepositorio.findById(2L)).thenReturn(Optional.of(cursoNovo));
        when(turmaRepositorio.save(existente)).thenReturn(existente);

        TurmaDTO dto = new TurmaDTO();
        when(dtoMapper.toTurmaDto(existente)).thenReturn(dto);

        TurmaDTO resultado = turmaServico.atualizar(4L, form);

        assertThat(existente.getCurso()).isEqualTo(cursoNovo);
        assertThat(existente.getCodigo()).isEqualTo(form.getCodigo());
        assertThat(existente.getPeriodo()).isEqualTo(form.getPeriodo());
        assertThat(existente.getTurno()).isEqualTo(form.getTurno());
        assertThat(existente.getCapacidade()).isEqualTo(form.getCapacidade());
        assertThat(existente.getDocentesResponsaveis()).isEqualTo(form.getDocentesResponsaveis());
        assertThat(existente.getStatus()).isEqualTo(form.getStatus());
        assertThat(resultado).isEqualTo(dto);
    }

    @Test
    void atualizar_quandoTurmaNaoExistirDeveLancarExcecao() {
        TurmaForm form = new TurmaForm();
        form.setCursoId(1L);
        form.setCodigo("TURMA-2024-1");

        when(turmaRepositorio.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> turmaServico.atualizar(100L, form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Turma não encontrada");
    }

    @Test
    void atualizar_quandoCursoNaoExistirDeveLancarExcecao() {
        Curso curso = criarCurso(1L, "Mestrado em Economia");
        Turma existente = criarTurma(4L, curso, "TURMA-2024-1", "2024/1", Turno.NOITE,
                30, "Dr. Silva", StatusTurma.ABERTA);

        TurmaForm form = new TurmaForm();
        form.setCursoId(99L);
        form.setCodigo("TURMA-2024-1");

        when(turmaRepositorio.findById(4L)).thenReturn(Optional.of(existente));
        when(cursoRepositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> turmaServico.atualizar(4L, form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Curso não encontrado");
    }

    @Test
    void deletar_quandoTurmaExistirDeveRemover() {
        when(turmaRepositorio.existsById(2L)).thenReturn(true);

        turmaServico.deletar(2L);

        verify(turmaRepositorio).deleteById(2L);
    }

    @Test
    void deletar_quandoTurmaNaoExistirDeveLancarExcecao() {
        when(turmaRepositorio.existsById(2L)).thenReturn(false);

        assertThatThrownBy(() -> turmaServico.deletar(2L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Turma não encontrada");
        verify(turmaRepositorio, times(0)).deleteById(2L);
    }

    private Curso criarCurso(Long id, String nome) {
        Curso curso = new Curso();
        curso.setId(id);
        curso.setNome(nome);
        curso.setNivel(NivelPosGraduacao.MESTRADO);
        curso.setAreaConhecimento("Economia");
        curso.setModalidade(Modalidade.PRESENCIAL);
        curso.setCargaHorariaTotal(2400);
        curso.setTitulacaoConferida("Mestre em Economia");
        curso.setStatusOferta(StatusOfertaCurso.ABERTO);
        return curso;
    }

    private Turma criarTurma(Long id, Curso curso, String codigo, String periodo, Turno turno,
                             Integer capacidade, String docentes, StatusTurma status) {
        Turma turma = new Turma();
        turma.setId(id);
        turma.setCurso(curso);
        turma.setCodigo(codigo);
        turma.setPeriodo(periodo);
        turma.setTurno(turno);
        turma.setCapacidade(capacidade);
        turma.setDocentesResponsaveis(docentes);
        turma.setStatus(status);
        return turma;
    }
}
