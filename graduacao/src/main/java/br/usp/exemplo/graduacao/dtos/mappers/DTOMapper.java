package br.usp.exemplo.graduacao.dtos.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.usp.exemplo.graduacao.dtos.AlunoDTO;
import br.usp.exemplo.graduacao.dtos.CursoDTO;
import br.usp.exemplo.graduacao.dtos.MatriculaDTO;
import br.usp.exemplo.graduacao.entidades.Aluno;
import br.usp.exemplo.graduacao.entidades.Curso;
import br.usp.exemplo.graduacao.entidades.Matricula;

@Mapper(componentModel = "spring")
public interface DTOMapper {

    AlunoDTO toAlunoDto(Aluno aluno);
    List<AlunoDTO> toAlunoDto(List<Aluno> alunos);

    CursoDTO toCursoDto(Curso curso);
    List<CursoDTO> toCursoDto(List<Curso> cursos);

    @Mapping(target = "alunoId", source = "aluno.id")
    @Mapping(target = "cursoId", source = "curso.id")
    MatriculaDTO toMatriculaDto(Matricula matricula);
    List<MatriculaDTO> toMatriculaDto(List<Matricula> matriculas);
}
