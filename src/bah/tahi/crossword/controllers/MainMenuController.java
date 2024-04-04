package bah.tahi.crossword.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import bah.tahi.crossword.Database;
import bah.tahi.crossword.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

/**
 * Controleur du menu principal.
 */
public class MainMenuController implements Initializable {

	/**
	 * Liste déroulante des grilles disponibles.
	 */
	@FXML
	private ChoiceBox<String> gridChoices;

	/**
	 * Bouton pour commencer une partie.
	 */
	@FXML
	private Button playBtn;

	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Chargement des grilles disponibles dans notre liste déroulante
		Database db = new Database();
		Map<Integer, String> grids = db.availableGrids();
		gridChoices.setItems(FXCollections.observableArrayList(grids.values()));

		// Ecouteur du changement de l'élément sélectionné dans la liste déroulante
		gridChoices.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				Main.setPuzzleNumber(gridChoices.getSelectionModel().getSelectedIndex() + 1);
				playBtn.setDisable(false);
			}
		});

		// Action au clic du bouton JOUER
		playBtn.setOnAction(event -> {
			if (Main.getPuzzleNumber() != 0) {
				play();
			}
		});
	}

	/**
	 * Démarrer une partie.
	 * 
	 * @pre puzzleNumber != 0 (Il faut avoir choisit une grille avant de lancer une
	 *      partie)
	 */
	private void play() {
		try {
			Pane view = (Pane) FXMLLoader.load(getClass().getResource("/bah/tahi/crossword/crosswordScene.fxml"));
			Main.setView(view);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
