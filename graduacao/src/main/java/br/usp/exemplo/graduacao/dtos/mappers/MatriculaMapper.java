package br.usp.exemplo.graduacao.dtos.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.usp.exemplo.graduacao.dtos.MatriculaDTO;
import br.usp.exemplo.graduacao.entidades.Matricula;

@Mapper(componentModel = "spring")
public interface MatriculaMapper {

    @Mapping(target = "alunoId", source = "aluno.id")
    @Mapping(target = "cursoId", source = "curso.id")
    MatriculaDTO toDto(Matricula matricula);

    List<MatriculaDTO> toDto(List<Matricula> matriculas);
}
