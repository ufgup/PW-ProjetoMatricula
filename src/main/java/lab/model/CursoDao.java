package lab.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class CursoDao {

public CursoDao() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Curso> obterCursos() {
			//Cria uma lista de cursos.
			List<Curso> cursos = new ArrayList<Curso>();
			try {
				// URL de conexão JDBC com o banco de dados.
				String url = "jdbc:derby://localhost/db;create=true";
				// Obtém uma conexão com o banco de dados.
				Connection conn = DriverManager
						.getConnection(url, "app", "123");
				//Obtém uma sentença SQL.
				Statement stmt = conn.createStatement();
				//Executa a instrução SQL.
				ResultSet rs = stmt
						.executeQuery("select codigo, nome from curso");
				//Percorre todos os registros.
				while (rs.next()) {
					int codigo = Integer.parseInt(rs.getString("codigo"));
					String nome = rs.getString("nome");

					Curso curso = new Curso();
					curso.setCodigo(codigo);
					curso.setNome(nome);
					cursos.add(curso);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return cursos;
	}
	
	public void incluirCurso(Curso curso) {
		try {
			String url = "jdbc:derby://localhost/db;create=true";
			Connection conn = DriverManager
					.getConnection(url, "app", "123");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("insert into curso (codigo, nome) values (" + curso.getCodigo() + ", '" + curso.getNome() + "' )");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void excluirCurso(Curso curso) {
		try {
			String url = "jdbc:derby://localhost/db;create=true";
			Connection conn = DriverManager
					.getConnection(url, "app", "123");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("delete from curso where codigo = " +  curso.getCodigo());
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
