package br.usp.exemplo.posgraduacao.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.exemplo.posgraduacao.entidades.Aluno;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByMatricula(String matricula);
}
