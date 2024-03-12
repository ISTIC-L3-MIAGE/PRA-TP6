package bah.tahi.crossword;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Crossword extends Grid<CrosswordSquare> {

    private ObservableList<Clue> verticalClues;
    private ObservableList<Clue> horizontalClues;

    private Crossword(int height, int width) {
        super(height, width);
    }

    public static Crossword createPuzzle(Database database, int puzzleNumber) {
        return null;
    }

    public StringProperty propositionProperty(int row, int column) {
        return null;
    }

    public boolean isBlackSquare(int row, int column) {
        return false;
    }

    public void setBlackSquare(int row, int column, boolean black) {
        return;
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

}
