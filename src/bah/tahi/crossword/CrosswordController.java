package bah.tahi.crossword;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class CrosswordController {

	/**
	 * Instance du modèle de jeu
	 */
	private static final Crossword model = Crossword.getInstance();
	private final int BOARD_HEIGHT = model.getHeight();
	private final int BOARD_WIDTH = model.getWidth();

	/**
	 * Conteneur du plateau de jeu sur la vue
	 */
	@FXML
	private AnchorPane gridContainer;

	/**
	 * Les indices
	 */
	@FXML
	private ScrollPane horizontalIndexes;
	@FXML
	private ScrollPane verticalIndexes;

	@FXML
	public void initialize() {

		// Initialisation de la grille et de son conteneur
		int i, j;

		double gridContainerHeight = gridContainer.getPrefHeight();
		double gridContainerWidth = gridContainer.getPrefWidth();

		double gridRowHeight = gridContainerHeight / BOARD_HEIGHT;
		double gridRowWidth = gridContainerWidth / BOARD_WIDTH;

		GridPane grid = new GridPane();
		grid.setMaxSize(gridContainerWidth, gridContainerHeight);

		for (i = 0; i < BOARD_HEIGHT; i++) {
			grid.getRowConstraints().add(new RowConstraints(gridRowHeight));
		}

		for (j = 0; j < BOARD_WIDTH; j++) {
			grid.getColumnConstraints().add(new ColumnConstraints(gridRowWidth));
		}

		model.setBlackSquare(5, 5, true); // test

		// Ajout des cases à la grille
		for (i = 0; i < BOARD_HEIGHT; i++) {
			for (j = 0; j < BOARD_WIDTH; j++) {
				CrosswordSquare square = model.getCell(i, j);
				grid.add(square, j, i);
			}
		}

		// Ajout de la grille à son conteneur
		gridContainer.getChildren().add(grid);

		// Évènement du bouton RESTART
		/*
		 * restartButton.setOnAction(event -> model.restart());
		 * 
		 * 
		 * // Binding des labels
		 * gameOverLabel.textProperty().bind(model.getEndOfGameMessage());
		 * 
		 * freeCasesLabel.visibleProperty().bind(model.gameOver().not());
		 * freeCasesLabel.textProperty().bind(model.getScore(Owner.NONE).asString().
		 * concat(" case(s) libre(s)"));
		 * 
		 * firstCasesLabel.styleProperty().bind(model.getOwnerLabelStyle(Owner.FIRST));
		 * firstCasesLabel.textProperty().bind(model.getScore(Owner.FIRST).asString().
		 * concat(" case(s) pour X"));
		 * 
		 * secondCasesLabel.styleProperty().bind(model.getOwnerLabelStyle(Owner.SECOND))
		 * ;
		 * secondCasesLabel.textProperty().bind(model.getScore(Owner.SECOND).asString().
		 * concat(" case(s) pour O"));
		 */
	}

}
