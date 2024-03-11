package bah.tahi.crossword;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainCrossword extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("crosswordScene.fxml"));
			Scene scene = new Scene(root);

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
