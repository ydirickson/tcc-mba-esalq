package br.usp.exemplo.graduacao.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.exemplo.graduacao.entidades.Aluno;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {
  Optional<Aluno> findByEmail(String email);
}
