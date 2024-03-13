package bah.tahi.crossword;

import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class CrosswordSquare extends Label {

	private final String solution = "";
	private final SimpleStringProperty proposition = new SimpleStringProperty("");
	private final String horizontal = "";
	private final String vertical = "";
	private final BooleanProperty black = new SimpleBooleanProperty(false);

	public final SimpleStringProperty propositionProperty() {
		return proposition;
	}

	public final BooleanProperty blackProperty() {
		return black;
	}

	public CrosswordSquare(final Crossword crossword, final int row, final int column) {
		// Styles
		Font normalFont = new Font((double) 100 / crossword.getHeight()); // Police normale
		Font bigFont = new Font((double) 150 / crossword.getHeight()); // Police en cas de victoire
		Background whiteBg = new Background(new BackgroundFill(Color.WHITE, null, null)); // Le fond des cases vides en
																							// cours de partie
		Background blackBg = new Background(new BackgroundFill(Color.BLACK, null, null)); // Le fond des cases vides en
																							// cours de partie
		Background greenBg = new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)); // Le fond des cases
																								// vides en cours de
																								// partie
		Background greyBg = new Background(new BackgroundFill(Color.LIGHTGREY, null, null)); // Le fond des cases
		// vides en cours de
		// partie
		Background redBg = new Background(new BackgroundFill(Color.RED, null, null)); // Le fond des cases vides à la
																						// fin d'une partie
		Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(0.5))); // Bordure
		Border focusedBorder = new Border(
				new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))); // Bordure
																									// par
																									// défaut
																									// des
																									// cases
		// Animations
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), this);
		scaleTransition.setFromX(0); // Échelle de départ (rétrécie)
		scaleTransition.setFromY(0);
		scaleTransition.setToX(1.0); // Échelle d'arrivée (taile normale)
		scaleTransition.setToY(1.0);

		setFont(normalFont); // On applique la police normale à l'initialisation
		setBorder(border); // On applique les bordures à l'initialisation
		setAlignment(Pos.CENTER); // Centrer le texte dans une case
		setBackground(whiteBg); // Couleur par défaut d'une case
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Taille pour que la case occupe tout l'espace disponible dans

		// Bindings
		textProperty().bind(propositionProperty());

		// Les observateurs
		focusedProperty().addListener((observable, oldValue, newValue) -> {
			setBorder(newValue ? focusedBorder : border);
		});

		blackProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				propositionProperty().set(null);
				setBackground(blackBg);
			} else {
				propositionProperty().set(null);
				setBackground(whiteBg);
			}
		});

		textProperty().addListener((observable, oldValue, newValue) -> {
			// Si la nouvelle valeur n'est pas vide (une lettre a été tapée)
			if (newValue != null && !newValue.isEmpty() && Character.isLetter(newValue.charAt(0))) {
				// Arrêter et remettre à zéro la transition
				scaleTransition.stop();
				scaleTransition.setRate(1.0);
				// Jouer la transition
				scaleTransition.playFromStart();
			}
		});

		// Les évènements liés à la souris
		setOnMouseEntered(event -> {
			if (!blackProperty().get()) {
				setBackground(greyBg);
				setCursor(Cursor.HAND); // On change le curseur au survol de la souris d'une case qui n'est pas noire.
			}
		});

		setOnMouseExited(event -> {
			if (!blackProperty().get()) {
				setBackground(whiteBg);
				setCursor(Cursor.DEFAULT); // On remet le curseur par défaut de la souris.
			}
		});

		setOnMouseClicked(event -> {
			if (!blackProperty().get()) {
				requestFocus(); // Donne le focus à la case cliquée
			}
		});

		// Détection des touches du clavier
		setOnKeyPressed(event -> {
			// Changement de la valeur du champ
			if (isFocused()) {
				String keyPressed = event.getText().toUpperCase();

				if (event.getCode() == KeyCode.BACK_SPACE) {
					propositionProperty().set("");
				} else if (!keyPressed.isEmpty() && Character.isLetter(keyPressed.charAt(0))) {
					propositionProperty().set(keyPressed);
				}
			}

			// Changement de focus
			// Déterminer la direction opposée
			int oppositeRow = row;
			int oppositeColumn = column;

			System.out.println(event.getCode());

			switch (event.getCode()) {
			case UP:
				oppositeRow--;
				break;
			case DOWN:
				oppositeRow++;
				break;
			case LEFT:
				oppositeColumn--;
				break;
			case RIGHT:
				oppositeColumn++;
				break;
			// Ajoute d'autres cas pour les autres directions si nécessaire
			}

			// Vérifier si la case opposée est valide
			if (crossword.correctCoords(oppositeRow, oppositeColumn)) {
				// Déplacer le focus vers la case opposée
				crossword.getCell(oppositeRow, oppositeColumn).requestFocus();
			}
		});
	}
}
