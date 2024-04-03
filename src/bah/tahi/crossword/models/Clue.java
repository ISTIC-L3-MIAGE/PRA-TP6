package bah.tahi.crossword.models;

/**
 * Classe représentant l'indice d'une solution.
 */
public class Clue {
	/**
	 * Le texte de l'indice.
	 */
	private final String clue;

	/**
	 * Le numéro de la ligne de début.
	 */
	private final int row;

	/**
	 * Le numéro de la colonne de début.
	 */
	private final int column;

	/**
	 * Précise si c'est un indice sur un mot horizontal ou vertical
	 */
	private final boolean horizontal;

	/**
	 * Constructeur.
	 */
	public Clue(String clue, int row, int column, boolean horizontal) {
		this.clue = clue;
		this.row = row;
		this.column = column;
		this.horizontal = horizontal;
	}

	/**
	 * @return le texte de l'indice.
	 */
	public final String getClue() {
		return this.clue;
	}

	/**
	 * @return le numéro de la colonne de début.
	 */
	public final int getColumn() {
		return this.column;
	}

	/**
	 * @return le numéro de la ligne de début.
	 */
	public final int getRow() {
		return this.row;
	}

	@Override
	public String toString() {
		return clue + " (" + (row + 1) + "," + (column + 1) + ")";
	}
}
