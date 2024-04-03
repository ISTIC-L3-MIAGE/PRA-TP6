package bah.tahi.crossword.models;

/**
 * Classe représentant une grille d'éléments.
 * 
 * @param <T> type des éléments de la grille.
 */
public class Grid<T> {

	/**
	 * Taille de la grille.
	 */
	private int height, width;

	/**
	 * Représentation matricielle de la grille.
	 */
	private T[][] grid;

	/**
	 * Constructeur.
	 */
	public Grid(int height, int width) {
		this.height = height;
		this.width = width;
		this.grid = (T[][]) new Object[height][width];

		for (int row = 0; row < getHeight(); row++) {
			for (int column = 0; column < getWidth(); column++) {
				this.grid[row][column] = null;
			}
		}
	}

	/**
	 * @return La hauteur de la grille (le nombre de lignes).
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * @return La largeur de la grille (le nombre de colonnes).
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 * @return true si les coordonnées (row, column) pointent sur une case
	 *         existante, false sinon.
	 */
	public boolean correctCoords(int row, int column) {
		return 0 <= row && row < this.height && 0 <= column && column < this.width;
	}

	/**
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 * @return la valeur de la case (row, column).
	 */
	public T getCell(int row, int column) {
		return this.grid[row][column];
	}

	/**
	 * Permet de saisir la valeur de la case (row, column).
	 *
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 */
	public void setCell(int row, int column, T value) {
		this.grid[row][column] = value;
	}

	/**
	 * @return La grille sous forme de chaine de caractères.
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				result.append(this.grid[i][j].toString());
				if (j < this.width - 1) {
					result.append(" | ");
				}
			}
			result.append("\n");
		}

		return result.toString();
	}
}