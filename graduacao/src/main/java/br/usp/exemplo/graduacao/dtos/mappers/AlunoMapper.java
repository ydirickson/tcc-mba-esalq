package br.usp.exemplo.graduacao.dtos.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import br.usp.exemplo.graduacao.dtos.AlunoDTO;
import br.usp.exemplo.graduacao.entidades.Aluno;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    AlunoDTO toDto(Aluno aluno);

    List<AlunoDTO> toDto(List<Aluno> alunos);
}
