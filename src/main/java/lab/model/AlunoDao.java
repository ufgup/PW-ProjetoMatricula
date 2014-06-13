package lab.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class AlunoDao {
	
	public List<Aluno> obterAlunos() {
		try {
			
			List<Aluno> alunos = new ArrayList<Aluno>();
			// URL de conexão JDBC com o banco de dados.
			String url = "jdbc:derby://localhost/db;create=true";
			// Obtém uma conexão com o banco de dados.
			Connection conn = DriverManager
					.getConnection(url, "app", "123");
			//Obtém uma sentença SQL.
			Statement stmt = conn.createStatement();
			//Executa a instrução SQL.
			ResultSet rs = stmt
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
		try {
			// URL de conexão JDBC com o banco de dados.
			
			String url = "jdbc:derby://localhost/db;create=true";
			// Obtém uma conexão com o banco de dados.
			Connection conn = DriverManager
					.getConnection(url, "app", "123");
			//Obtém uma sentença SQL.
			Statement stmt = conn.createStatement();
			//Executa a instrução SQL.
			stmt.executeUpdate("insert into aluno (matricula, nome, sexo, idade, curso) values (" 
							+ " '" + aluno.getMatricula() + "', "
							+ "'" + aluno.getNome() + "', "
							+ "'" + aluno.getSexo() + "', "
							+ aluno.getIdade() + ", "
							+ aluno.getCurso() + " )"
							);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void excluirAluno(Aluno aluno) {
		try {
			String url = "jdbc:derby://localhost/db;create=true";
			// Obtém uma conexão com o banco de dados.
			Connection conn = DriverManager
					.getConnection(url, "app", "123");
			//Obtém uma sentença SQL.
			Statement stmt = conn.createStatement();
			//Executa a instrução SQL.
			stmt.execute("delete from aluno where matricula = '" + aluno.getMatricula() + "'");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public int contagem(String valor) {
		
		int cont = 0;
		
		try {
			String url = "jdbc:derby://localhost/db;create=true";
			// Obtém uma conexão com o banco de dados.
			Connection conn = DriverManager
					.getConnection(url, "app", "123");
			//Obtém uma sentença SQL.
			Statement stmt = conn.createStatement();
			
			if(valor.equals("Homens")){
				ResultSet rs = stmt.executeQuery("select count(*) as quantidade from aluno where sexo = 'M'");
				cont = rs.getInt("quantidade");
			}
			else if(valor.equals("Mulheres")) {
				ResultSet rs = stmt.executeQuery("select count(*) as quantidade from aluno where sexo = 'F'");
				cont = rs.getInt("quantidade");
			}
			else if(valor.equals("Maior")) {
				ResultSet rs = stmt.executeQuery("select count(*) as quantidade from aluno where idade >= 18");
				cont = rs.getInt("quantidade");
			}
			else if(valor.equals("Menor")) {
				ResultSet rs = stmt.executeQuery("select count(*) as quantidade from aluno where idade < 18");
				cont = rs.getInt("quantidade");
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return cont;
	}
}
