package lab.model;

import java.sql.Connection;
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
					.executeQuery("select matricula, nome, curso from aluno");
			
			while(rs.next()) {
				String matricula = rs.getString("matricula");
				String nome = rs.getString("nome");
				int curso = rs.getInt("curso");
				
				Aluno aluno = new Aluno();
				aluno.setMatricula(matricula);
				aluno.setNome(nome);
				aluno.setCurso(curso);
				alunos.add(aluno);
			}
			
			return alunos;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void incluirAluno(Aluno aluno) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			//Cria a conexão com o Banco de Dados
			conn = JdbcUtils.createConnection();
			//Inicia a transação
			conn.setAutoCommit(false);
			//Obtém uma sentença SQL.
			stmt = conn.createStatement();
			//Executa a instrução SQL.
			stmt.executeUpdate("insert into aluno (matricula, nome, sexo, idade, curso) values (" 
							+ " '" + aluno.getMatricula() + "', "
							+ "'" + aluno.getNome() + "', "
							+ "'" + aluno.getSexo() + "', "
							+ aluno.getIdade() + ", "
							+ aluno.getCurso() + " )"
							);
			conn.commit();
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
		Statement stmt = null;
		
		try {
			conn = JdbcUtils.createConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			stmt.execute("delete from aluno where matricula = '" + aluno.getMatricula() + "'");
			
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
	
	public int contagem(String valor) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int cont = 0;
		
		try {
			conn = JdbcUtils.createConnection();
			
			//Obtém uma sentença SQL.
			stmt = conn.createStatement();
			
			if(valor.equals("Homens")){
				rs = stmt.executeQuery("select count(*) as quantidade from aluno where sexo = 'M'");
				cont = rs.getInt("quantidade");
			}
			else if(valor.equals("Mulheres")) {
				rs = stmt.executeQuery("select count(*) as quantidade from aluno where sexo = 'F'");
				cont = rs.getInt("quantidade");
			}
			else if(valor.equals("Maior")) {
				rs = stmt.executeQuery("select count(*) as quantidade from aluno where idade >= 18");
				cont = rs.getInt("quantidade");
			}
			else if(valor.equals("Menor")) {
				rs = stmt.executeQuery("select count(*) as quantidade from aluno where idade < 18");
				cont = rs.getInt("quantidade");
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(conn, stmt, rs);
		}
		
		return cont;
	}
}
