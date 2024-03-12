package bah.tahi.crossword;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Crossword extends Grid<CrosswordSquare> {

	private ObservableList<Clue> verticalClues;
	private ObservableList<Clue> horizontalClues;

	private Crossword(int height, int width) {
		super(height, width);

		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				setCell(i, j, new CrosswordSquare(i, j));
			}
		}
	}

	public static Crossword createPuzzle(Database database, int puzzleNumber) {
		return null;
	}

	public StringProperty propositionProperty(int row, int column) {
		return null;
	}

	public boolean isBlackSquare(int row, int column) {
		if (correctCoords(row, column)) {
			return getCell(row, column) == null;
		} else {
			throw new RuntimeException();
		}
	}

	public void setBlackSquare(int row, int column, boolean black) {
		if (correctCoords(row, column)) {
			if (black) {
				setCell(row, column, null);
			} else {
				setCell(row, column, new CrosswordSquare(row, column));
			}
		} else {
			throw new RuntimeException();
		}
	}

	public char getSolution(int row, int column) {
		return 'a';
	}

	public void setSolution(int row, int column, char solution) {
	}

	public char getProposition(int row, int column) {
		return 'a';
	}

	public void setProposition(int row, int column, char proposition) {
	}

	public String getDefinition(int row, int column, boolean horizontal) {
		return "";
	}

	public void setDefinition(int row, int column, boolean horizontal, String definition) {
	}

	public ObservableList<Clue> getVerticalClues() {
		return null;
	}

	public ObservableList<Clue> getHorizontalClues() {
		return null;
	}

	public void printProposition() {
	}

	public void printSolution() {
	}

	/**
	 * @return la seule instance possible du jeu.
	 */
	public static Crossword getInstance() {
		return CrosswordHolder.INSTANCE;
	}

	/**
	 * Classe interne selon le pattern singleton.
	 */
	private static class CrosswordHolder {
		private static final Crossword INSTANCE = new Crossword(9, 9);
	}

}
