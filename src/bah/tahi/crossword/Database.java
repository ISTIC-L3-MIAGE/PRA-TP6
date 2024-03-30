package bah.tahi.crossword;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant d'établir une connexion avec la base de données et charger
 * les grilles de jeu disponibles.
 */
public class Database {

	/**
	 * Objet de connexion à la base de données.
	 */
	private Connection conn;

	public Database() {
		try {
			conn = connectToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre une nouvelle connexion à la BD.
	 *
	 * @return un objet de connexion à la BD
	 * @throws SQLException en cas d'échec de connexion
	 */
	private Connection connectToDB() throws SQLException {
		// Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost/base_pra_tp6";
		return DriverManager.getConnection(url, "root", "root");
	}

	/**
	 * Permet de savoir si la liaison à la BD a bien été établie.
	 *
	 * @return true si la connexion est établie, false sinon.
	 */
	public boolean connected() {
		return conn != null;
	}

	/**
	 * Retourne la liste des grilles disponibles dans la BD. Chaque grille est
	 * décrite par la concaténation des valeurs respectives des colonnes nom_grille,
	 * hauteur et largeur. L’élément de liste ainsi obtenu est indexé par le numéro
	 * de la grille (colonne num_grille).
	 *
	 * @return une table où les clés sont les numéros de grille et les valeurs sont
	 *         les noms des grilles.
	 */
	public Map<Integer, String> availableGrids() {
		if (!connected()) { // Rien à faire s'il n'y a pas de lien à la BD
			return null;
		}

		try {
			String sql = "SELECT * FROM Grid";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

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

	/**
	 * Extrait les données d'une grille à partir de son numéro.
	 * 
	 * @param puzzleNumber le numéro de la grille
	 * @return une grille de mots croisés
	 */
	public Crossword extractGrid(int puzzleNumber) {
		if (!connected()) { // Rien à faire s'il n'y a pas de lien à la BD
			return null;
		}

		Map<Integer, String> grids = availableGrids();
		if (!grids.containsKey(puzzleNumber)) {
			return null;
		}

		try {
			String sql = "SELECT * FROM Crossword JOIN Grid WHERE Crossword.numero_grille = " + puzzleNumber;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) { // TODO: Continue here
				String definition = rs.getString("definition");
				int ligne = rs.getInt("ligne");
				int colonne = rs.getInt("colonne");
				boolean horizontal = rs.getBoolean("horizontal");

				Clue clue = new Clue(definition, ligne, colonne, horizontal);

				System.out.println(clue.toString()); // test
			}

			return null;
		} catch (SQLException e) {
			System.out.println("[ERROR] Error in Database.extractGrid()");
			e.printStackTrace();
			return null;
		}
	}
}
