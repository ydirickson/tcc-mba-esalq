package br.usp.exemplo.graduacao.servicos;

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

import br.usp.exemplo.graduacao.dtos.MatriculaDTO;
import br.usp.exemplo.graduacao.dtos.mappers.DTOMapper;
import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.entidades.Matricula;
import br.usp.exemplo.graduacao.forms.MatriculaForm;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;
import br.usp.exemplo.graduacao.repositorios.MatriculaRepositorio;

@ExtendWith(MockitoExtension.class)
class MatriculaServicoTest {

    @Mock
    private MatriculaRepositorio matriculaRepositorio;

    @Mock
    private DTOMapper dtoMapper;

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @Mock
    private CursoRepositorio cursoRepositorio;

    @InjectMocks
    private MatriculaServico matriculaServico;

    @Captor
    private ArgumentCaptor<Matricula> matriculaCaptor;

    @Test
    void listarTodos_deveRetornarListaDeDtos() {
        List<Matricula> entidades = List.of(criarMatricula(1L, 1L, 1L));
        List<MatriculaDTO> dtos = List.of(new MatriculaDTO(1L, 1L, 1L));

        when(matriculaRepositorio.findAll()).thenReturn(entidades);
        when(dtoMapper.toMatriculaDto(entidades)).thenReturn(dtos);

        assertThat(matriculaServico.listarTodos()).isEqualTo(dtos);
    }

    @Test
    void buscarPorId_quandoExistirDeveRetornarDto() {
        Matricula matricula = criarMatricula(2L, 3L, 4L);
        MatriculaDTO dto = new MatriculaDTO(2L, 3L, 4L);

        when(matriculaRepositorio.findById(2L)).thenReturn(Optional.of(matricula));
        when(dtoMapper.toMatriculaDto(matricula)).thenReturn(dto);

        assertThat(matriculaServico.buscarPorId(2L)).isEqualTo(dto);
    }

    @Test
    void buscarPorId_quandoNaoExistirDeveLancarExcecao() {
        when(matriculaRepositorio.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matriculaServico.buscarPorId(10L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Matrícula não encontrada");
    }

    @Test
    void salvar_deveAssociarAlunoECurso() {
        MatriculaForm form = new MatriculaForm();
        form.setAlunoId(1L);
        form.setCursoId(2L);

        Aluno aluno = criarAluno(1L);
        Curso curso = criarCurso(2L);
        Matricula salvo = criarMatricula(5L, 1L, 2L);

        when(alunoRepositorio.findById(1L)).thenReturn(Optional.of(aluno));
        when(cursoRepositorio.findById(2L)).thenReturn(Optional.of(curso));
        when(matriculaRepositorio.save(any(Matricula.class))).thenReturn(salvo);
        when(dtoMapper.toMatriculaDto(salvo)).thenReturn(new MatriculaDTO(5L, 1L, 2L));

        MatriculaDTO resultado = matriculaServico.salvar(form);

        verify(matriculaRepositorio).save(matriculaCaptor.capture());
        Matricula entidade = matriculaCaptor.getValue();
        assertThat(entidade.getAluno()).isEqualTo(aluno);
        assertThat(entidade.getCurso()).isEqualTo(curso);
        assertThat(resultado.getAlunoId()).isEqualTo(1L);
        assertThat(resultado.getCursoId()).isEqualTo(2L);
    }

    @Test
    void salvar_quandoAlunoNaoExistirDeveLancarExcecao() {
        MatriculaForm form = new MatriculaForm();
        form.setAlunoId(9L);
        form.setCursoId(1L);

        when(alunoRepositorio.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matriculaServico.salvar(form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Aluno não encontrado");
    }

    @Test
    void salvar_quandoCursoNaoExistirDeveLancarExcecao() {
        MatriculaForm form = new MatriculaForm();
        form.setAlunoId(1L);
        form.setCursoId(9L);

        when(alunoRepositorio.findById(1L)).thenReturn(Optional.of(criarAluno(1L)));
        when(cursoRepositorio.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matriculaServico.salvar(form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Curso não encontrado");
    }

    @Test
    void atualizar_deveAlterarRelacionamentos() {
        Matricula existente = criarMatricula(4L, 1L, 1L);
        MatriculaForm form = new MatriculaForm();
        form.setAlunoId(2L);
        form.setCursoId(3L);

        Aluno novoAluno = criarAluno(2L);
        Curso novoCurso = criarCurso(3L);

        when(matriculaRepositorio.findById(4L)).thenReturn(Optional.of(existente));
        when(alunoRepositorio.findById(2L)).thenReturn(Optional.of(novoAluno));
        when(cursoRepositorio.findById(3L)).thenReturn(Optional.of(novoCurso));
        when(matriculaRepositorio.save(existente)).thenReturn(existente);

        MatriculaDTO dto = new MatriculaDTO(4L, 2L, 3L);
        when(dtoMapper.toMatriculaDto(existente)).thenReturn(dto);

        MatriculaDTO resultado = matriculaServico.atualizar(4L, form);

        assertThat(existente.getAluno()).isEqualTo(novoAluno);
        assertThat(existente.getCurso()).isEqualTo(novoCurso);
        assertThat(resultado).isEqualTo(dto);
    }

    @Test
    void atualizar_quandoMatriculaNaoExistirDeveLancarExcecao() {
        MatriculaForm form = new MatriculaForm();
        form.setAlunoId(2L);
        form.setCursoId(3L);

        when(matriculaRepositorio.findById(4L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matriculaServico.atualizar(4L, form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Matrícula não encontrada");
    }

    @Test
    void deletar_quandoMatriculaExistirDeveRemover() {
        when(matriculaRepositorio.existsById(6L)).thenReturn(true);

        matriculaServico.deletar(6L);

        verify(matriculaRepositorio).deleteById(6L);
    }

    @Test
    void deletar_quandoMatriculaNaoExistirDeveLancarExcecao() {
        when(matriculaRepositorio.existsById(6L)).thenReturn(false);

        assertThatThrownBy(() -> matriculaServico.deletar(6L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Matrícula não encontrada");
        verify(matriculaRepositorio, times(0)).deleteById(6L);
    }

    private Matricula criarMatricula(Long id, Long alunoId, Long cursoId) {
        Aluno aluno = criarAluno(alunoId);
        Curso curso = criarCurso(cursoId);
        Matricula matricula = new Matricula(aluno, curso);
        matricula.setId(id);
        return matricula;
    }

    private Aluno criarAluno(Long id) {
        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome("Aluno " + id);
        aluno.setEmail("aluno" + id + "@usp.br");
        return aluno;
    }

    private Curso criarCurso(Long id) {
        Curso curso = new Curso();
        curso.setId(id);
        curso.setNome("Curso " + id);
        curso.setSigla("C" + id);
        curso.setDescricao("Descricao " + id);
        return curso;
    }
}
