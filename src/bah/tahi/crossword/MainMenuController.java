package bah.tahi.crossword;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {
	/**
	 * Bouton pour commencer une partie.
	 */
	@FXML
	private Button playBtn;

	/**
	 * Bouton pour commencer une partie.
	 */
	@FXML
	private ChoiceBox<String> gridChoices;

	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Menu
		Database db = new Database();
		Map<Integer, String> grids = db.availableGrids();
		gridChoices.setItems(FXCollections.observableArrayList(grids.values()));// .getItems().addAll(availableGrids);
		gridChoices.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				MainCrossword.setPuzzleNumber(gridChoices.getSelectionModel().getSelectedIndex() + 1);
			}
		});

		playBtn.setOnAction(event -> {
			try {
				play();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	public void play() throws IOException {
		HBox root = (HBox) FXMLLoader.load(getClass().getResource("crosswordScene.fxml"));
		Scene scene = new Scene(root);

		Stage stage = MainCrossword.getStage();
		stage.setTitle("Crossword puzzle");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setResizable(false);
		stage.show();
	}
}
