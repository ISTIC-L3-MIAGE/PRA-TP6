package bah.tahi.crossword;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Database {
	private Connection connexion;

	public Database() {
		try {
			connexion = connectToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection connectToDB() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql:");
		return conn;
	}

	public Map<Integer, String> availableGrids() {
		return null;
	}

	public Crossword extractGrid(int puzzleNumber) {
		return null;
	}
}
