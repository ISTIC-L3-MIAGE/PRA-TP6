package bah.tahi.crossword.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bah.tahi.crossword.Database;
import bah.tahi.crossword.Main;
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

/**
 * Controleur de la vue du plateau de jeu.
 */
public class CrosswordController implements Initializable {

	/**
	 * Instance du modèle de jeu.
	 */
	private static Crossword model;

	/**
	 * Taille du plateau.
	 */
	private int BOARD_HEIGHT, BOARD_WIDTH;

	/**
	 * Conteneur du plateau de jeu sur la vue.
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

		// Chargement de la grille choisie par le joueur
		model = Crossword.createPuzzle(db, Main.getPuzzleNumber());
		BOARD_HEIGHT = model.getHeight();
		BOARD_WIDTH = model.getWidth();

		// Gestion des évènements en rapport avec la grille de jeu
		model.directionProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == Direction.VERTICAL) {
				verticalIndexes.getStyleClass().add("current-direction");
				horizontalIndexes.getStyleClass().remove("current-direction");
			} else {
				horizontalIndexes.getStyleClass().add("current-direction");
				verticalIndexes.getStyleClass().remove("current-direction");
			}
		});

		model.winProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				try {
					Pane view = (Pane) FXMLLoader
							.load(getClass().getResource("/bah/tahi/crossword/views/endGameScene.fxml"));
					Main.setView(view);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// Initialistaion des indices
		horizontalIndexes.setItems(model.getHorizontalClues());
		verticalIndexes.setItems(model.getVerticalClues());

		horizontalIndexes.getStyleClass().add("current-direction");

		horizontalIndexes.setOnMouseClicked(event -> {
			Clue selectedItem = horizontalIndexes.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				CrosswordSquare square = model.getCell(selectedItem.getRow(), selectedItem.getColumn());
				square.setAsCurrentSquare();
				model.directionProperty().set(Direction.HORIZONTAL);
			}
		});

		verticalIndexes.setOnMouseClicked(event -> {
			Clue selectedItem = verticalIndexes.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				CrosswordSquare square = model.getCell(selectedItem.getRow(), selectedItem.getColumn());
				square.setAsCurrentSquare();
				model.directionProperty().set(Direction.VERTICAL);
			}
		});

		// Initialisation de la grille et de son conteneur sur la vue
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

				// On rajoute des écouteurs pour changer l'élément sélectionné dans les
				// ListViews
				square.focusedProperty().addListener((observable, oldValue, newValue) -> {
					if (newValue) {
						Clue horizontal = square.getDefinition(true);
						Clue vertical = square.getDefinition(false);

						if (horizontal != null) {
							horizontalIndexes.getSelectionModel().select(horizontal);
						} else {
							horizontalIndexes.getSelectionModel().clearSelection();
						}

						if (vertical != null) {
							verticalIndexes.getSelectionModel().select(vertical);
						} else {
							horizontalIndexes.getSelectionModel().clearSelection();
						}
					}
				});

				grid.add(square, j, i);
			}
		}

		// Ajout de la grille à son conteneur
		gridContainer.getChildren().add(grid);
	}

}
