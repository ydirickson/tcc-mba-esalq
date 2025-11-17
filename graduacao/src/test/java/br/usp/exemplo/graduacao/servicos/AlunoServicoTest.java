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

import br.usp.exemplo.graduacao.dtos.AlunoDTO;
import br.usp.exemplo.graduacao.dtos.mappers.DTOMapper;
import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.forms.AlunoForm;
import br.usp.exemplo.graduacao.repositorios.AlunoRepositorio;

@ExtendWith(MockitoExtension.class)
class AlunoServicoTest {

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private AlunoServico alunoServico;

    @Captor
    private ArgumentCaptor<Aluno> alunoCaptor;

    @Test
    void listarTodos_deveRetornarListaDeDtos() {
        List<Aluno> entidades = List.of(criarAluno(1L, "Fulano", "fulano@usp.br"));
        List<AlunoDTO> dtos = List.of(new AlunoDTO(1L, "Fulano", "fulano@usp.br"));

        when(alunoRepositorio.findAll()).thenReturn(entidades);
        when(dtoMapper.toAlunoDto(entidades)).thenReturn(dtos);

        assertThat(alunoServico.listarTodos()).isEqualTo(dtos);
        verify(alunoRepositorio).findAll();
        verify(dtoMapper).toAlunoDto(entidades);
    }

    @Test
    void obterPorId_quandoEncontrarDeveRetornarDto() {
        Aluno aluno = criarAluno(7L, "Beltrano", "beltrano@usp.br");
        AlunoDTO dto = new AlunoDTO(7L, "Beltrano", "beltrano@usp.br");

        when(alunoRepositorio.findById(7L)).thenReturn(Optional.of(aluno));
        when(dtoMapper.toAlunoDto(aluno)).thenReturn(dto);

        assertThat(alunoServico.obterPorId(7L)).isEqualTo(dto);
    }

    @Test
    void obterPorId_quandoNaoExistirDeveLancarExcecao() {
        when(alunoRepositorio.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alunoServico.obterPorId(10L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Aluno não encontrado");
    }

    @Test
    void salvar_devePersistirComDadosDoFormulario() {
        AlunoForm form = new AlunoForm();
        form.setNome("Novo Aluno");
        form.setEmail("novo@usp.br");

        Aluno salvo = criarAluno(3L, form.getNome(), form.getEmail());
        when(alunoRepositorio.save(any(Aluno.class))).thenReturn(salvo);
        when(dtoMapper.toAlunoDto(salvo)).thenReturn(new AlunoDTO(3L, form.getNome(), form.getEmail()));

        AlunoDTO resultado = alunoServico.salvar(form);

        verify(alunoRepositorio).save(alunoCaptor.capture());
        Aluno entidade = alunoCaptor.getValue();
        assertThat(entidade.getNome()).isEqualTo(form.getNome());
        assertThat(entidade.getEmail()).isEqualTo(form.getEmail());
        assertThat(resultado.getId()).isEqualTo(3L);
    }

    @Test
    void atualizar_quandoAlunoExistirDeveAlterarDados() {
        Aluno existente = criarAluno(5L, "Antigo", "antigo@usp.br");
        AlunoForm form = new AlunoForm();
        form.setNome("Atualizado");
        form.setEmail("novo@usp.br");

        when(alunoRepositorio.findById(5L)).thenReturn(Optional.of(existente));
        when(alunoRepositorio.save(existente)).thenReturn(existente);
        AlunoDTO dto = new AlunoDTO(5L, form.getNome(), form.getEmail());
        when(dtoMapper.toAlunoDto(existente)).thenReturn(dto);

        AlunoDTO resultado = alunoServico.atualizar(5L, form);

        assertThat(existente.getNome()).isEqualTo(form.getNome());
        assertThat(existente.getEmail()).isEqualTo(form.getEmail());
        assertThat(resultado).isEqualTo(dto);
    }

    @Test
    void atualizar_quandoAlunoNaoExistirDeveLancarExcecao() {
        AlunoForm form = new AlunoForm();
        form.setNome("Atualizado");
        form.setEmail("novo@usp.br");

        when(alunoRepositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alunoServico.atualizar(99L, form))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Aluno não encontrado");
    }

    @Test
    void deletar_quandoAlunoExistirDeveRemover() {
        when(alunoRepositorio.existsById(1L)).thenReturn(true);

        alunoServico.deletar(1L);

        verify(alunoRepositorio).deleteById(1L);
    }

    @Test
    void deletar_quandoAlunoNaoExistirDeveLancarExcecao() {
        when(alunoRepositorio.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> alunoServico.deletar(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Aluno não encontrado");
        verify(alunoRepositorio, times(0)).deleteById(1L);
    }

    private Aluno criarAluno(Long id, String nome, String email) {
        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome(nome);
        aluno.setEmail(email);
        return aluno;
    }
}
