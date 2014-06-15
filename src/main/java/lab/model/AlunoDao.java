package lab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import lab.utils.JdbcUtils;

@ManagedBean
@ApplicationScoped
public class AlunoDao {
	
	public List<Aluno> obterAlunos() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			List<Aluno> alunos = new ArrayList<Aluno>();
			// Obtém uma conexão com o banco de dados.
			conn = JdbcUtils.createConnection();
			
			//Obtém uma sentença SQL.
			stmt = conn.createStatement();
			//Executa a instrução SQL.
			rs = stmt
					.executeQuery("select * from aluno");
			
			while(rs.next()) {
				String matricula = rs.getString("matricula");
				String nome = rs.getString("nome");
				int curso = rs.getInt("curso");
				String sexo = rs.getString("sexo");
				int idade = rs.getInt("idade");
				
				Aluno aluno = new Aluno();
				aluno.setMatricula(matricula);
				aluno.setNome(nome);
				aluno.setCurso(curso);
				aluno.setSexo(sexo);
				aluno.setIdade(idade);
				alunos.add(aluno);
			}
			
			return alunos;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void incluirAluno(Aluno aluno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			//Cria a conexão com o Banco de Dados
			conn = JdbcUtils.createConnection();
			//Inicia a transação
			conn.setAutoCommit(false);
			//Obtém uma sentença SQL.
			stmt = conn.prepareStatement("insert into aluno (matricula, nome, sexo, idade, curso) values (?, ?, ?, ?, ?)");
			stmt.setString(1, aluno.getMatricula());
			stmt.setString(2, aluno.getNome());
			stmt.setString(3, aluno.getSexo());
			stmt.setInt(4, aluno.getIdade());
			stmt.setInt(5, aluno.getCurso());
			//Executa a instrução SQL.
			stmt.executeUpdate();
			conn.commit(); //Commita a modificação
		} catch (Exception e) {
			
			try {
				conn.rollback();
				throw new RuntimeException(e);
			} catch (Exception el) {
				//Não tem como fazer nada!
			}
			
			
		} finally {
			JdbcUtils.close(conn, stmt);
		}
	}
	
	public void excluirAluno(Aluno aluno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = JdbcUtils.createConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("delete from aluno where matricula = ?");
			stmt.setString(1, aluno.getMatricula());
			
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			
			try {
				conn.rollback();
				throw new RuntimeException(e);
			} catch (Exception el) {
				//Não consigo fazer mais nada
			}
		} finally {
			JdbcUtils.close(conn, stmt);
		}
	}
	
//	public int contagem(String valor) {
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		int cont = 0;
//		
//		try {
//			conn = JdbcUtils.createConnection();
//			
//			//Obtém uma sentença SQL.
//			stmt = conn.createStatement();
//			
//			if(valor.equals("Homens")){
//				rs = stmt.executeQuery("select count(*) as quantidade from aluno where sexo = 'M'");
//				cont = rs.getInt("quantidade");
//			}
//			else if(valor.equals("Mulheres")) {
//				rs = stmt.executeQuery("select count(*) as quantidade from aluno where sexo = 'F'");
//				cont = rs.getInt("quantidade");
//			}
//			else if(valor.equals("Maior")) {
//				rs = stmt.executeQuery("select count(*) as quantidade from aluno where idade >= 18");
//				cont = rs.getInt("quantidade");
//			}
//			else if(valor.equals("Menor")) {
//				rs = stmt.executeQuery("select count(*) as quantidade from aluno where idade < 18");
//				cont = rs.getInt("quantidade");
//			}
//			
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			JdbcUtils.close(conn, stmt, rs);
//		}
//		
//		return cont;
//	}
	
	public Estatistica contagem() {
		
		Estatistica result = new Estatistica();
		int homem = 0;
		int mulher = 0;
		int maior = 0;
		int menor = 0;
		
		List<Aluno> alunos = obterAlunos();
		
		for(Aluno x : alunos) {
			if(x.getSexo().equals("M")) homem++;
			else mulher++;
			
			if(x.getIdade() > 18) maior++;
			else menor++;
		}
		
		result.setHomem(homem);
		result.setMulher(mulher);
		result.setMaior(maior);
		result.setMenor(menor);
		
		return result;
	}
}
