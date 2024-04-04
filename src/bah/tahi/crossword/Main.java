package bah.tahi.crossword;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Classe de départ de l'application.
 */
public class Main extends Application {

	/**
	 * La fenêtre.
	 */
	private static Scene scene;

	/**
	 * Le numéro de la grille choisie par le joueur (0 si pas de choix fait).
	 */
	private static int puzzleNumber = 0;

	/**
	 * @return le numéro de la grille choisie par le joueur.
	 */
	public static int getPuzzleNumber() {
		return puzzleNumber;
	}

	/**
	 * Modifier le numéro de grille choisit par le joueur.
	 * 
	 * @param number le nouveau numéro de grille.
	 */
	public static void setPuzzleNumber(int puzzleNumber) {
		Main.puzzleNumber = puzzleNumber;
	}

	/**
	 * Changer la vue affichée à l'utilisateur.
	 * 
	 * @param view la nouvelle vue à afficher.
	 */
	public static void setView(Pane view) {
		scene.setRoot(view);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			// Chargement de la vue du menu principal
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("mainMenuScene.fxml"));
			Main.scene = new Scene(root);

			// Détection de CTRL + W pour fermer la fenêtre
			scene.setOnKeyPressed(event -> {
				if (event.isControlDown() && event.getCode() == KeyCode.W) {
					stage.close();
				}
			});

			// Préconfigurations de la fenêtre
			stage.setTitle("Crossword puzzle");
			stage.setScene(scene);
			stage.sizeToScene();
			stage.setResizable(false);
			scene.getStylesheets().add("/bah/tahi/crossword/style.css");

			// Affichage de la fenêtre
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
