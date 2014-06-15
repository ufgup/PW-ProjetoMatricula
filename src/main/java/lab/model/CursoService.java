package lab.model;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
@ApplicationScoped
public class CursoService {
	
	@ManagedProperty(value="#{cursoDao}")
	private CursoDao cursoDao;

	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}

	public void salvar(Curso curso) {
		cursoDao.incluirCurso(curso);
	}

	public List<Curso> obterCursos() {
		return cursoDao.obterCursos();
	}

	public void excluir(Curso curso) {
		cursoDao.excluirCurso(curso);
	}
	
}
