package lab.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import lab.model.Curso;
import lab.model.CursoService;

@ManagedBean
@RequestScoped
public class CadastroCurso {
	
	@ManagedProperty(value="#{cursoService}")
	private CursoService cursoService;
	
	private Curso curso = new Curso();
	
	//Para fazer "cache" e ganhar desempenho.
	private List<Curso> cursos;
	
	
	public void setCursoService(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public List<Curso> getCursos() {
		//Implementação de "cache" da lista de alunos.
		if (cursos == null) {
			cursos = cursoService.obterCursos();
		}
		return cursos;
	}
	
	public String salvar() {
		cursoService.salvar(curso);
		return "listagemCurso?faces-redirect=true";
	}
	
	public String excluir(Curso curso) {
		cursoService.excluir(curso);
		return "listagemCurso?faces-redirect=true";
	}

}
