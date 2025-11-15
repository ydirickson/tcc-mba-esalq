package br.usp.exemplo.graduacao.dtos.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import br.usp.exemplo.graduacao.dtos.CursoDTO;
import br.usp.exemplo.graduacao.entidades.Curso;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CursoDTO toDto(Curso curso);

    List<CursoDTO> toDto(List<Curso> cursos);
}
