package br.usp.exemplo.posgraduacao.dtos.mappers;

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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-22T17:04:19-0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DTOMapperImpl implements DTOMapper {

    @Override
    public CursoDTO toCursoDto(Curso curso) {
        if ( curso == null ) {
            return null;
        }

        CursoDTO cursoDTO = new CursoDTO();

        cursoDTO.setAreaConhecimento( curso.getAreaConhecimento() );
        cursoDTO.setCargaHorariaTotal( curso.getCargaHorariaTotal() );
        cursoDTO.setId( curso.getId() );
        cursoDTO.setLinhaPesquisa( curso.getLinhaPesquisa() );
        cursoDTO.setModalidade( curso.getModalidade() );
        cursoDTO.setNivel( curso.getNivel() );
        cursoDTO.setNome( curso.getNome() );
        cursoDTO.setStatusOferta( curso.getStatusOferta() );
        cursoDTO.setTitulacaoConferida( curso.getTitulacaoConferida() );

        return cursoDTO;
    }

    @Override
    public List<CursoDTO> toCursoDto(List<Curso> cursos) {
        if ( cursos == null ) {
            return null;
        }

        List<CursoDTO> list = new ArrayList<CursoDTO>( cursos.size() );
        for ( Curso curso : cursos ) {
            list.add( toCursoDto( curso ) );
        }

        return list;
    }

    @Override
    public TurmaDTO toTurmaDto(Turma turma) {
        if ( turma == null ) {
            return null;
        }

        TurmaDTO turmaDTO = new TurmaDTO();

        turmaDTO.setCursoId( turmaCursoId( turma ) );
        turmaDTO.setCapacidade( turma.getCapacidade() );
        turmaDTO.setCodigo( turma.getCodigo() );
        turmaDTO.setDocentesResponsaveis( turma.getDocentesResponsaveis() );
        turmaDTO.setId( turma.getId() );
        turmaDTO.setPeriodo( turma.getPeriodo() );
        turmaDTO.setStatus( turma.getStatus() );
        turmaDTO.setTurno( turma.getTurno() );

        return turmaDTO;
    }

    @Override
    public List<TurmaDTO> toTurmaDto(List<Turma> turmas) {
        if ( turmas == null ) {
            return null;
        }

        List<TurmaDTO> list = new ArrayList<TurmaDTO>( turmas.size() );
        for ( Turma turma : turmas ) {
            list.add( toTurmaDto( turma ) );
        }

        return list;
    }

    @Override
    public DisciplinaDTO toDisciplinaDto(Disciplina disciplina) {
        if ( disciplina == null ) {
            return null;
        }

        DisciplinaDTO disciplinaDTO = new DisciplinaDTO();

        disciplinaDTO.setCursoId( disciplinaCursoId( disciplina ) );
        disciplinaDTO.setCodigo( disciplina.getCodigo() );
        disciplinaDTO.setCreditos( disciplina.getCreditos() );
        disciplinaDTO.setEmenta( disciplina.getEmenta() );
        disciplinaDTO.setId( disciplina.getId() );
        disciplinaDTO.setNome( disciplina.getNome() );
        disciplinaDTO.setPreRequisitos( disciplina.getPreRequisitos() );
        disciplinaDTO.setStatus( disciplina.getStatus() );
        disciplinaDTO.setTipo( disciplina.getTipo() );

        return disciplinaDTO;
    }

    @Override
    public List<DisciplinaDTO> toDisciplinaDto(List<Disciplina> disciplinas) {
        if ( disciplinas == null ) {
            return null;
        }

        List<DisciplinaDTO> list = new ArrayList<DisciplinaDTO>( disciplinas.size() );
        for ( Disciplina disciplina : disciplinas ) {
            list.add( toDisciplinaDto( disciplina ) );
        }

        return list;
    }

    @Override
    public DocenteDTO toDocenteDto(Docente docente) {
        if ( docente == null ) {
            return null;
        }

        DocenteDTO docenteDTO = new DocenteDTO();

        docenteDTO.setAreaAtuacao( docente.getAreaAtuacao() );
        docenteDTO.setCurriculoLattes( docente.getCurriculoLattes() );
        docenteDTO.setEmail( docente.getEmail() );
        docenteDTO.setId( docente.getId() );
        docenteDTO.setNome( docente.getNome() );
        docenteDTO.setRegimeTrabalho( docente.getRegimeTrabalho() );
        docenteDTO.setStatus( docente.getStatus() );
        docenteDTO.setTitulacao( docente.getTitulacao() );

        return docenteDTO;
    }

    @Override
    public List<DocenteDTO> toDocenteDto(List<Docente> docentes) {
        if ( docentes == null ) {
            return null;
        }

        List<DocenteDTO> list = new ArrayList<DocenteDTO>( docentes.size() );
        for ( Docente docente : docentes ) {
            list.add( toDocenteDto( docente ) );
        }

        return list;
    }

    @Override
    public AlunoDTO toAlunoDto(Aluno aluno) {
        if ( aluno == null ) {
            return null;
        }

        AlunoDTO alunoDTO = new AlunoDTO();

        alunoDTO.setOrientadorId( alunoOrientadorId( aluno ) );
        alunoDTO.setEmail( aluno.getEmail() );
        alunoDTO.setId( aluno.getId() );
        alunoDTO.setIngresso( aluno.getIngresso() );
        alunoDTO.setLinhaPesquisa( aluno.getLinhaPesquisa() );
        alunoDTO.setMatricula( aluno.getMatricula() );
        alunoDTO.setNivel( aluno.getNivel() );
        alunoDTO.setNome( aluno.getNome() );
        alunoDTO.setSituacao( aluno.getSituacao() );

        return alunoDTO;
    }

    @Override
    public List<AlunoDTO> toAlunoDto(List<Aluno> alunos) {
        if ( alunos == null ) {
            return null;
        }

        List<AlunoDTO> list = new ArrayList<AlunoDTO>( alunos.size() );
        for ( Aluno aluno : alunos ) {
            list.add( toAlunoDto( aluno ) );
        }

        return list;
    }

    @Override
    public ProjetoPesquisaDTO toProjetoPesquisaDto(ProjetoPesquisa projeto) {
        if ( projeto == null ) {
            return null;
        }

        ProjetoPesquisaDTO projetoPesquisaDTO = new ProjetoPesquisaDTO();

        projetoPesquisaDTO.setAlunoId( projetoAlunoId( projeto ) );
        projetoPesquisaDTO.setOrientadorId( projetoOrientadorId( projeto ) );
        projetoPesquisaDTO.setCoorientadorId( projetoCoorientadorId( projeto ) );
        projetoPesquisaDTO.setDataInicio( projeto.getDataInicio() );
        projetoPesquisaDTO.setId( projeto.getId() );
        projetoPesquisaDTO.setLinhaPesquisa( projeto.getLinhaPesquisa() );
        projetoPesquisaDTO.setPalavrasChave( projeto.getPalavrasChave() );
        projetoPesquisaDTO.setPrazo( projeto.getPrazo() );
        projetoPesquisaDTO.setResumo( projeto.getResumo() );
        projetoPesquisaDTO.setSituacao( projeto.getSituacao() );
        projetoPesquisaDTO.setTipo( projeto.getTipo() );
        projetoPesquisaDTO.setTitulo( projeto.getTitulo() );

        return projetoPesquisaDTO;
    }

    @Override
    public List<ProjetoPesquisaDTO> toProjetoPesquisaDto(List<ProjetoPesquisa> projetos) {
        if ( projetos == null ) {
            return null;
        }

        List<ProjetoPesquisaDTO> list = new ArrayList<ProjetoPesquisaDTO>( projetos.size() );
        for ( ProjetoPesquisa projetoPesquisa : projetos ) {
            list.add( toProjetoPesquisaDto( projetoPesquisa ) );
        }

        return list;
    }

    @Override
    public MatriculaDisciplinaDTO toMatriculaDisciplinaDto(MatriculaDisciplina matriculaDisciplina) {
        if ( matriculaDisciplina == null ) {
            return null;
        }

        MatriculaDisciplinaDTO matriculaDisciplinaDTO = new MatriculaDisciplinaDTO();

        matriculaDisciplinaDTO.setAlunoId( matriculaDisciplinaAlunoId( matriculaDisciplina ) );
        matriculaDisciplinaDTO.setDisciplinaId( matriculaDisciplinaDisciplinaId( matriculaDisciplina ) );
        matriculaDisciplinaDTO.setTurmaId( matriculaDisciplinaTurmaId( matriculaDisciplina ) );
        matriculaDisciplinaDTO.setFrequencia( matriculaDisciplina.getFrequencia() );
        matriculaDisciplinaDTO.setId( matriculaDisciplina.getId() );
        matriculaDisciplinaDTO.setNota( matriculaDisciplina.getNota() );
        matriculaDisciplinaDTO.setPeriodo( matriculaDisciplina.getPeriodo() );
        matriculaDisciplinaDTO.setSituacao( matriculaDisciplina.getSituacao() );

        return matriculaDisciplinaDTO;
    }

    @Override
    public List<MatriculaDisciplinaDTO> toMatriculaDisciplinaDto(List<MatriculaDisciplina> matriculas) {
        if ( matriculas == null ) {
            return null;
        }

        List<MatriculaDisciplinaDTO> list = new ArrayList<MatriculaDisciplinaDTO>( matriculas.size() );
        for ( MatriculaDisciplina matriculaDisciplina : matriculas ) {
            list.add( toMatriculaDisciplinaDto( matriculaDisciplina ) );
        }

        return list;
    }

    @Override
    public BancaDefesaDTO toBancaDefesaDto(BancaDefesa banca) {
        if ( banca == null ) {
            return null;
        }

        BancaDefesaDTO bancaDefesaDTO = new BancaDefesaDTO();

        bancaDefesaDTO.setProjetoId( bancaProjetoId( banca ) );
        bancaDefesaDTO.setAlunoId( bancaAlunoId( banca ) );
        bancaDefesaDTO.setOrientadorId( bancaOrientadorId( banca ) );
        bancaDefesaDTO.setAta( banca.getAta() );
        bancaDefesaDTO.setDataHora( banca.getDataHora() );
        bancaDefesaDTO.setId( banca.getId() );
        bancaDefesaDTO.setLinkOnline( banca.getLinkOnline() );
        bancaDefesaDTO.setLocal( banca.getLocal() );
        bancaDefesaDTO.setMembros( banca.getMembros() );
        bancaDefesaDTO.setResultado( banca.getResultado() );
        bancaDefesaDTO.setTipo( banca.getTipo() );

        return bancaDefesaDTO;
    }

    @Override
    public List<BancaDefesaDTO> toBancaDefesaDto(List<BancaDefesa> bancas) {
        if ( bancas == null ) {
            return null;
        }

        List<BancaDefesaDTO> list = new ArrayList<BancaDefesaDTO>( bancas.size() );
        for ( BancaDefesa bancaDefesa : bancas ) {
            list.add( toBancaDefesaDto( bancaDefesa ) );
        }

        return list;
    }

    private Long turmaCursoId(Turma turma) {
        Curso curso = turma.getCurso();
        if ( curso == null ) {
            return null;
        }
        return curso.getId();
    }

    private Long disciplinaCursoId(Disciplina disciplina) {
        Curso curso = disciplina.getCurso();
        if ( curso == null ) {
            return null;
        }
        return curso.getId();
    }

    private Long alunoOrientadorId(Aluno aluno) {
        Docente orientador = aluno.getOrientador();
        if ( orientador == null ) {
            return null;
        }
        return orientador.getId();
    }

    private Long projetoAlunoId(ProjetoPesquisa projetoPesquisa) {
        Aluno aluno = projetoPesquisa.getAluno();
        if ( aluno == null ) {
            return null;
        }
        return aluno.getId();
    }

    private Long projetoOrientadorId(ProjetoPesquisa projetoPesquisa) {
        Docente orientador = projetoPesquisa.getOrientador();
        if ( orientador == null ) {
            return null;
        }
        return orientador.getId();
    }

    private Long projetoCoorientadorId(ProjetoPesquisa projetoPesquisa) {
        Docente coorientador = projetoPesquisa.getCoorientador();
        if ( coorientador == null ) {
            return null;
        }
        return coorientador.getId();
    }

    private Long matriculaDisciplinaAlunoId(MatriculaDisciplina matriculaDisciplina) {
        Aluno aluno = matriculaDisciplina.getAluno();
        if ( aluno == null ) {
            return null;
        }
        return aluno.getId();
    }

    private Long matriculaDisciplinaDisciplinaId(MatriculaDisciplina matriculaDisciplina) {
        Disciplina disciplina = matriculaDisciplina.getDisciplina();
        if ( disciplina == null ) {
            return null;
        }
        return disciplina.getId();
    }

    private Long matriculaDisciplinaTurmaId(MatriculaDisciplina matriculaDisciplina) {
        Turma turma = matriculaDisciplina.getTurma();
        if ( turma == null ) {
            return null;
        }
        return turma.getId();
    }

    private Long bancaProjetoId(BancaDefesa bancaDefesa) {
        ProjetoPesquisa projeto = bancaDefesa.getProjeto();
        if ( projeto == null ) {
            return null;
        }
        return projeto.getId();
    }

    private Long bancaAlunoId(BancaDefesa bancaDefesa) {
        Aluno aluno = bancaDefesa.getAluno();
        if ( aluno == null ) {
            return null;
        }
        return aluno.getId();
    }

    private Long bancaOrientadorId(BancaDefesa bancaDefesa) {
        Docente orientador = bancaDefesa.getOrientador();
        if ( orientador == null ) {
            return null;
        }
        return orientador.getId();
    }
}
