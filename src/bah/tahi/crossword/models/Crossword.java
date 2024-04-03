package bah.tahi.crossword.models;

import bah.tahi.crossword.Database;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Modèle de jeu.
 */
public class Crossword extends Grid<CrosswordSquare> {

	/**
	 * Liste des indices verticaux.
	 */
	private ObservableList<Clue> verticalClues = FXCollections.observableArrayList();

	/**
	 * Liste des indices horizontaux.
	 */
	private ObservableList<Clue> horizontalClues = FXCollections.observableArrayList();

	/**
	 * Direction courante.
	 */
	private ObjectProperty<Direction> direction = new SimpleObjectProperty<>(Direction.HORIZONTAL);

	/**
	 * Direction courante.
	 */
	private BooleanProperty win = new SimpleBooleanProperty(false);

	/**
	 * Constructeur
	 * 
	 * @param height hauteur de la grille.
	 * @param width  largeur de la grille.
	 */
	public Crossword(int height, int width) {
		super(height, width);

		// Création des cases de la grille
		for (int row = 0; row < getHeight(); row++) {
			for (int column = 0; column < getWidth(); column++) {
				CrosswordSquare square = new CrosswordSquare(this, row, column);
				setCell(row, column, square);
			}
		}
	}

	/**
	 * @return l'observateur de la victoire.
	 */
	public BooleanProperty winProperty() {
		return win;
	}

	/**
	 * @return l'observateur de la direction courante.
	 */
	public ObjectProperty<Direction> directionProperty() {
		return direction;
	}

	/**
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 * @eturn l'observateur de la proposition sur la case (row,column).
	 */
	public StringProperty propositionProperty(int row, int column) {
		return getCell(row, column).propositionProperty();
	}

	/**
	 * @param database     le gestionnaire de BD.
	 * @param puzzleNumber le numéro de la grille à récupérer en BD.
	 * @return une grile de mots croisés chargée depuis la BD.
	 */
	public static Crossword createPuzzle(Database database, int puzzleNumber) {
		return database.extractGrid(puzzleNumber);
	}

	/**
	 * Permet de savoir si la case (row,column) est une case noire.
	 * 
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 * @return true si la case (row,column) est une case noire, false sinon.
	 */
	public boolean isBlackSquare(int row, int column) {
		if (correctCoords(row, column)) {
			return getCell(row, column).blackProperty().get();
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * Permet de noircir une case.
	 * 
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 */
	public void setBlackSquare(int row, int column, boolean black) {
		if (correctCoords(row, column)) {
			getCell(row, column).blackProperty().set(black);
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 * @return la lettre solution dans la case (row,column).
	 */
	public char getSolution(int row, int column) {
		CrosswordSquare square = getCell(row, column);
		return square.getSolution();
	}

	/**
	 * Permet d'affecter une solution à la case (row,column).
	 * 
	 * @param row      numéro de ligne.
	 * @param column   numéro de colonne.
	 * @param solution la solution à affecter.
	 */
	public void setSolution(int row, int column, char solution) {
		CrosswordSquare square = getCell(row, column);
		square.setSolution(solution);
	}

	/**
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 * @return la proposition faite par le joueur dans la case (row,column).
	 */
	public char getProposition(int row, int column) {
		return propositionProperty(row, column).get().charAt(0);
	}

	/**
	 * Permet d'affecter la proposition du joueur à la case (row,column).
	 * 
	 * @param row         numéro de ligne.
	 * @param column      numéro de colonne.
	 * @param proposition la proposition à affecter.
	 */
	public void setProposition(int row, int column, char proposition) {
		propositionProperty(row, column).set(Character.toString(proposition));
	}

	/**
	 * @param row    numéro de ligne.
	 * @param column numéro de colonne.
	 * @return la définition du mot composé de la cse (row,column).
	 */
	public String getDefinition(int row, int column, boolean horizontal) {
		CrosswordSquare square = getCell(row, column);
		return square.getDefinition(horizontal);
	}

	/**
	 * Permet d'affecter une définition à la case (row,column).
	 * 
	 * @param row        numéro de ligne.
	 * @param column     numéro de colonne.
	 * @param definition la definition à affecter.
	 */
	public void setDefinition(int row, int column, boolean horizontal, String definition) {
		Clue clue = new Clue(definition, row, column, horizontal);
		CrosswordSquare square = getCell(row, column);
		square.setDefinition(clue, horizontal);

		if (horizontal) {
			horizontalClues.add(clue);
		} else {
			verticalClues.add(clue);
		}
	}

	/**
	 * @return la liste des indices verticaux.
	 */
	public ObservableList<Clue> getVerticalClues() {
		return verticalClues;
	}

	/**
	 * @return la liste des indices horizontaux.
	 */
	public ObservableList<Clue> getHorizontalClues() {
		return horizontalClues;
	}

	public void printProposition() {
	}

	public void printSolution() {
	}

	/**
	 * Vérifie si toutes les propositions faites par le joueur correspondent aux
	 * solutions des cases.
	 */
	public void checkWin() {
		boolean win = true;

		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				CrosswordSquare square = getCell(i, j);
				win &= square.checkProposition();
			}
		}

		winProperty().set(win);
	}

}
