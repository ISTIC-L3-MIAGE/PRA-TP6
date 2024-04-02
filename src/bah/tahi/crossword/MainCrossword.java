package bah.tahi.crossword;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainCrossword extends Application {

	private static Stage stage;
	private static int puzzleNumber;

	public static Stage getStage() {
		return stage;
	}

	public static int getPuzzleNumber() {
		return puzzleNumber;
	}

	public static void setPuzzleNumber(int number) {
		puzzleNumber = number;
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			// TODO: Fix this
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("mainMenuScene.fxml"));
			Scene scene = new Scene(root);

			this.stage = stage;
			stage.setTitle("Crossword puzzle");
			stage.setScene(scene);
			stage.sizeToScene();
			stage.setResizable(false);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
