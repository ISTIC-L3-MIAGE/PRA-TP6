package bah.tahi.crossword;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bah.tahi.crossword.models.Clue;
import bah.tahi.crossword.models.Crossword;
import bah.tahi.crossword.models.CrosswordSquare;
import bah.tahi.crossword.models.Direction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class CrosswordController implements Initializable {

	/**
	 * Instance du modèle de jeu
	 */
	private static Crossword model;
	private int BOARD_HEIGHT;
	private int BOARD_WIDTH;

	/**
	 * Conteneur du plateau de jeu sur la vue
	 */
	@FXML
	private AnchorPane gridContainer;

	/**
	 * Liste des indices horizontaux.
	 */
	@FXML
	private ListView<Clue> horizontalIndexes;

	/**
	 * Liste des indices verticaux.
	 */
	@FXML
	private ListView<Clue> verticalIndexes;

	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		Database db = new Database();

		// Instanciation du modèle de jeu

		model = Crossword.createPuzzle(db, Main.getPuzzleNumber());
		BOARD_HEIGHT = model.getHeight();
		BOARD_WIDTH = model.getWidth();

		// Initialistaion des indices
		horizontalIndexes.setItems(model.getHorizontalClues());
		verticalIndexes.setItems(model.getVerticalClues());

		// horizontalIndexes.getSelectionModel().selectedItemProperty();

		horizontalIndexes.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				CrosswordSquare square = model.getCell(newValue.getRow(), newValue.getColumn());
				square.setAsCurrentSquare();
				model.directionProperty().set(Direction.HORIZONTAL);
			}
		});

		verticalIndexes.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				CrosswordSquare square = model.getCell(newValue.getRow(), newValue.getColumn());
				square.setAsCurrentSquare();
				model.directionProperty().set(Direction.VERTICAL);
			}
		});

		model.winProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				try {
					Pane view = (Pane) FXMLLoader.load(getClass().getResource("endGameScene.fxml"));
					Main.setView(view);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

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

		// Ajout des cases à la grille
		for (i = 0; i < BOARD_HEIGHT; i++) {
			for (j = 0; j < BOARD_WIDTH; j++) {
				CrosswordSquare square = model.getCell(i, j);
				grid.add(square, j, i);
			}
		}

		// Ajout de la grille à son conteneur
		gridContainer.getChildren().add(grid);
	}

}
