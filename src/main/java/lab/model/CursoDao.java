package lab.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import lab.utils.JdbcUtils;

@ManagedBean
@ApplicationScoped
public class CursoDao {
	
	public List<Curso> obterCursos() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<Curso> cursos = new ArrayList<Curso>();
		try {
			// Obtém uma conexão com o banco de dados.
			conn = JdbcUtils.createConnection();
			
			//Obtém uma sentença SQL.
			stmt = conn.createStatement();
			
			//Executa a instrução SQL.
			rs = stmt.executeQuery("select codigo, nome from curso");
			
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
		} finally {
			JdbcUtils.close(conn, stmt, rs);
		}
		return cursos;
	}
	
	public void incluirCurso(Curso curso) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = JdbcUtils.createConnection();
			conn.setAutoCommit(false); //Inicia a transação
			
			stmt = conn.prepareStatement("insert into curso (codigo, nome) values (? , ? )");
			stmt.setInt(1, curso.getCodigo());
			stmt.setString(2, curso.getNome());
			stmt.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				throw new RuntimeException(e);
			} catch (Exception el) {
				//Ai não dá para fazer mais nada meu jovem!
			}
			
		} finally {
			JdbcUtils.close(conn, stmt);
		}
	}
	
	public void excluirCurso(Curso curso) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = JdbcUtils.createConnection();
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement("delete from curso where codigo = ?");
			stmt.setInt(1, curso.getCodigo());
			stmt.executeUpdate();
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
				throw new RuntimeException(e);
			} catch (Exception el) {
				// Ai não dá para fazer mais nada meu jovem!
			}
		} finally {
			JdbcUtils.close(conn, stmt);
		}
		
	}
}
