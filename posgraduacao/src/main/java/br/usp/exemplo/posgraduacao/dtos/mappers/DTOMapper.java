package br.usp.exemplo.posgraduacao.dtos.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.usp.exemplo.posgraduacao.dtos.AlunoDTO;
import br.usp.exemplo.posgraduacao.dtos.BancaDefesaDTO;
import br.usp.exemplo.posgraduacao.dtos.CursoDTO;
import br.usp.exemplo.posgraduacao.dtos.DisciplinaDTO;
import br.usp.exemplo.posgraduacao.dtos.DocenteDTO;
import br.usp.exemplo.posgraduacao.dtos.MatriculaDisciplinaDTO;
import br.usp.exemplo.posgraduacao.dtos.ProjetoPesquisaDTO;
import br.usp.exemplo.posgraduacao.dtos.TurmaDTO;
import br.usp.exemplo.posgraduacao.entidades.Aluno;
import br.usp.exemplo.posgraduacao.entidades.BancaDefesa;
import br.usp.exemplo.posgraduacao.entidades.Curso;
import br.usp.exemplo.posgraduacao.entidades.Disciplina;
import br.usp.exemplo.posgraduacao.entidades.Docente;
import br.usp.exemplo.posgraduacao.entidades.MatriculaDisciplina;
import br.usp.exemplo.posgraduacao.entidades.ProjetoPesquisa;
import br.usp.exemplo.posgraduacao.entidades.Turma;

@Mapper(componentModel = "spring")
public interface DTOMapper {

    CursoDTO toCursoDto(Curso curso);
    List<CursoDTO> toCursoDto(List<Curso> cursos);

    @Mapping(target = "cursoId", source = "curso.id")
    TurmaDTO toTurmaDto(Turma turma);
    List<TurmaDTO> toTurmaDto(List<Turma> turmas);

    @Mapping(target = "cursoId", source = "curso.id")
    DisciplinaDTO toDisciplinaDto(Disciplina disciplina);
    List<DisciplinaDTO> toDisciplinaDto(List<Disciplina> disciplinas);

    DocenteDTO toDocenteDto(Docente docente);
    List<DocenteDTO> toDocenteDto(List<Docente> docentes);

    @Mapping(target = "orientadorId", source = "orientador.id")
    AlunoDTO toAlunoDto(Aluno aluno);
    List<AlunoDTO> toAlunoDto(List<Aluno> alunos);

    @Mapping(target = "alunoId", source = "aluno.id")
    @Mapping(target = "orientadorId", source = "orientador.id")
    @Mapping(target = "coorientadorId", source = "coorientador.id")
    ProjetoPesquisaDTO toProjetoPesquisaDto(ProjetoPesquisa projeto);
    List<ProjetoPesquisaDTO> toProjetoPesquisaDto(List<ProjetoPesquisa> projetos);

    @Mapping(target = "alunoId", source = "aluno.id")
    @Mapping(target = "disciplinaId", source = "disciplina.id")
    @Mapping(target = "turmaId", source = "turma.id")
    MatriculaDisciplinaDTO toMatriculaDisciplinaDto(MatriculaDisciplina matriculaDisciplina);
    List<MatriculaDisciplinaDTO> toMatriculaDisciplinaDto(List<MatriculaDisciplina> matriculas);

    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "alunoId", source = "aluno.id")
    @Mapping(target = "orientadorId", source = "orientador.id")
    BancaDefesaDTO toBancaDefesaDto(BancaDefesa banca);
    List<BancaDefesaDTO> toBancaDefesaDto(List<BancaDefesa> bancas);
}
