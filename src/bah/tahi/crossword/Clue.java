package bah.tahi.crossword;

public class Clue {
	private final String clue;
	private final int row;
	private final int column;

	private final boolean horizontal;

	public Clue(String clue, int row, int column, boolean horizontal) {
		this.clue = clue;
		this.row = row;
		this.column = column;
		this.horizontal = horizontal;
	}

	public final String getClue() {
		return this.clue;
	}

	public final int getColumn() {
		return this.column;
	}

	public final int getRow() {
		return this.row;
	}

	@Override
	public String toString() {
		return clue + " (" + row + "," + column + ") - " + (horizontal ? "horizontal" : "vertical");
	}
}
