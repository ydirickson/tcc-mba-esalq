package br.usp.exemplo.posgraduacao.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.exemplo.posgraduacao.entidades.MatriculaDisciplina;

@Repository
public interface MatriculaDisciplinaRepositorio extends JpaRepository<MatriculaDisciplina, Long> {
}
