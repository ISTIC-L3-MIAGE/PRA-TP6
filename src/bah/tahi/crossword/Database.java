package bah.tahi.crossword;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Database {
	private Connection conn;

	public Database() {
		try {
			conn = connectToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection connectToDB() throws SQLException {
		String url = "jdbc:mysql://localhost/base_pra_tp6";
		return DriverManager.getConnection(url, "root", "");
	}

	public boolean connected() {
		return conn != null;
	}

	public Map<Integer, String> availableGrids() {
		if (!connected()) {
			return null;
		}

		try {
			String gridSQL = "SELECT * FROM Grid";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(gridSQL);

			Map<Integer, String> grids = new HashMap<>();
			while (rs.next()) {
				int numeroGrille = rs.getInt("numero_grille");
				int largeur = rs.getInt("largeur");
				int hauteur = rs.getInt("hauteur");
				String nomGrille = rs.getString("nom_grille") + " (" + hauteur + "x" + largeur + ")";
				grids.put(numeroGrille, nomGrille);
			}

			return grids;
		} catch (SQLException e) {
			System.out.println("[ERROR] Error in Database.availableGrids()");
			return new HashMap<>();
		}
	}

	public Crossword extractGrid(int puzzleNumber) {
		return null;
	}
}
