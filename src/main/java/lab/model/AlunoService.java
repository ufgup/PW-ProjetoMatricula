package lab.model;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
@ApplicationScoped
public class AlunoService {

	@ManagedProperty(value="#{alunoDao}")
	private AlunoDao alunoDao;

	public void setAlunoDao(AlunoDao alunoDao) {
		this.alunoDao = alunoDao;
	}

	public void salvar(Aluno aluno) {
		alunoDao.incluirAluno(aluno);
	}

	public List<Aluno> obterAlunos() {
		return alunoDao.obterAlunos();
	}

	public void excluir(Aluno aluno) {
		alunoDao.excluirAluno(aluno);
	}
	
	public Estatistica gerarEstatistica() {
		
		Estatistica result = new Estatistica();
		
		result.setHomem(alunoDao.contagem("Homens"));
		result.setMulher(alunoDao.contagem("Mulheres"));
		result.setMaior(alunoDao.contagem("Maior"));
		result.setMenor(alunoDao.contagem("Menor"));
		
		return result;
	}
}
