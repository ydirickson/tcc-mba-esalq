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

import br.usp.exemplo.graduacao.dtos.CursoDTO;
import br.usp.exemplo.graduacao.dtos.mappers.CursoMapper;
import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.forms.CursoForm;
import br.usp.exemplo.graduacao.repositorios.CursoRepositorio;

@ExtendWith(MockitoExtension.class)
class CursoServicoTest {

    @Mock
    private CursoRepositorio cursoRepositorio;

    @Mock
    private CursoMapper cursoMapper;

    @InjectMocks
    private CursoServico cursoServico;

    @Captor
    private ArgumentCaptor<Curso> cursoCaptor;

    @Test
    void listarTodos_deveRetornarListaDeDtos() {
        List<Curso> entidades = List.of(criarCurso(1L, "ECO", "Economia", "Economia para gestão"));
        List<CursoDTO> dtos = List.of(new CursoDTO(1L, "Economia", "Economia para gestão"));

        when(cursoRepositorio.findAll()).thenReturn(entidades);
        when(cursoMapper.toDto(entidades)).thenReturn(dtos);

        assertThat(cursoServico.listarTodos()).isEqualTo(dtos);
        verify(cursoRepositorio).findAll();
    }

    @Test
    void buscarPorId_quandoEncontrarDeveRetornarDto() {
        Curso curso = criarCurso(2L, "ADM", "Administração", "Administração Pública");
        CursoDTO dto = new CursoDTO(2L, "Administração", "Administração Pública");

        when(cursoRepositorio.findById(2L)).thenReturn(Optional.of(curso));
        when(cursoMapper.toDto(curso)).thenReturn(dto);

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
        form.setSigla("ECO");
        form.setNome("Economia");
        form.setDescricao("Economia aplicada");

        Curso salvo = criarCurso(3L, form.getSigla(), form.getNome(), form.getDescricao());
        when(cursoRepositorio.save(any(Curso.class))).thenReturn(salvo);
        when(cursoMapper.toDto(salvo)).thenReturn(new CursoDTO(3L, form.getNome(), form.getDescricao()));

        CursoDTO resultado = cursoServico.salvar(form);

        verify(cursoRepositorio).save(cursoCaptor.capture());
        Curso entidade = cursoCaptor.getValue();
        assertThat(entidade.getSigla()).isEqualTo(form.getSigla());
        assertThat(entidade.getNome()).isEqualTo(form.getNome());
        assertThat(entidade.getDescricao()).isEqualTo(form.getDescricao());
        assertThat(resultado.getId()).isEqualTo(3L);
    }

    @Test
    void atualizar_deveAlterarDadosDoCurso() {
        Curso existente = criarCurso(4L, "ECO", "Economia", "Descricao");
        CursoForm form = new CursoForm();
        form.setSigla("ECO");
        form.setNome("Economia Atualizada");
        form.setDescricao("Descricao atualizada");

        when(cursoRepositorio.findById(4L)).thenReturn(Optional.of(existente));
        when(cursoRepositorio.save(existente)).thenReturn(existente);

        CursoDTO dto = new CursoDTO(4L, form.getNome(), form.getDescricao());
        when(cursoMapper.toDto(existente)).thenReturn(dto);

        CursoDTO resultado = cursoServico.atualizar(4L, form);

        assertThat(existente.getNome()).isEqualTo(form.getNome());
        assertThat(existente.getDescricao()).isEqualTo(form.getDescricao());
        assertThat(resultado).isEqualTo(dto);
    }

    @Test
    void atualizar_quandoCursoNaoExistirDeveLancarExcecao() {
        CursoForm form = new CursoForm();
        form.setNome("Economia");

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

    private Curso criarCurso(Long id, String sigla, String nome, String descricao) {
        Curso curso = new Curso();
        curso.setId(id);
        curso.setSigla(sigla);
        curso.setNome(nome);
        curso.setDescricao(descricao);
        return curso;
    }
}
