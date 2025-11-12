package br.usp.exemplo.graduacao.repositorios;

import br.usp.exemplo.graduacao.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, Long> {
}
