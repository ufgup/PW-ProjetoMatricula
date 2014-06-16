package lab.utils;

import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;

public class JdbcUtils {
	
	public static Connection createConnection() {
		Connection conn = null;
		
		try {
			// URL de conexão JDBC com o banco de dados.
			String url = "jdbc:derby://localhost/db;create=true";
			// Obtém uma conexão com o banco de dados.
			conn = DriverManager
					.getConnection(url, "app", "123");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return conn;
	}
	
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			conn.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			//Aqui já não dá para fazer nada
		}
	}
	
	public static void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}
	
	public static Timestamp agora() {
		return new Timestamp(new Date().getTime());
	}
}
