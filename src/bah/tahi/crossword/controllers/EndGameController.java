package bah.tahi.crossword.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import bah.tahi.crossword.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Controleur de l'écran de fin de partie.
 */
public class EndGameController implements Initializable {

	/**
	 * Bouton pour recommencer une partie.
	 */
	@FXML
	private Button replayBtn;

	/**
	 * Label du temps écoulé.
	 */
	@FXML
	private Label gameTime;

	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		replayBtn.setOnAction(event -> replay());

		// Calcul du temps écoulé
		Duration duration = Main.getGameDuration();
		String durationString = duration.toSeconds() + "s";

		if (duration.toMinutes() != 0) {
			durationString = duration.toMinutes() + "m" + durationString;
		}

		if (duration.toHours() != 0) {
			durationString = duration.toHours() + "h" + durationString;
		}

		gameTime.setText("Temps écoulé : " + durationString);
	}

	/**
	 * Retroune sur le menu principal pour commencer une nouvelle partie.
	 */
	public void replay() {
		try {
			Pane view = (Pane) FXMLLoader.load(getClass().getResource("/bah/tahi/crossword/views/mainMenuScene.fxml"));
			Main.setView(view);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
