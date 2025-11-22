package br.usp.exemplo.posgraduacao.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.exemplo.posgraduacao.entidades.Docente;

@Repository
public interface DocenteRepositorio extends JpaRepository<Docente, Long> {
    Optional<Docente> findByEmail(String email);
}
