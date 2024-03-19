package bah.tahi.crossword;

import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
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
	// TODO: Voir si on peut rajouter clue ici

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
			if (isFocused()) {
				int nextRow = row, nextColumn = column;

				switch (event.getCode()) {

				case UP:
					crossword.directionProperty().set(Direction.VERTICAL);
					nextRow--;
					break;

				case DOWN:
					crossword.directionProperty().set(Direction.VERTICAL);
					nextRow++;
					break;

				case LEFT:
					crossword.directionProperty().set(Direction.HORIZONTAL);
					nextColumn--;
					break;

				case RIGHT:
					crossword.directionProperty().set(Direction.HORIZONTAL);
					nextColumn++;
					break;

				case BACK_SPACE:
					propositionProperty().set("");
					if (crossword.directionProperty().get().equals(Direction.HORIZONTAL)) {
						nextColumn--;
					} else {
						nextRow--;
					}
					break;

				default:
					String keyPressed = event.getText().toUpperCase();
					if (!keyPressed.isEmpty() && Character.isLetter(keyPressed.charAt(0))) {
						propositionProperty().set(keyPressed);
						if (crossword.directionProperty().get().equals(Direction.HORIZONTAL)) {
							nextColumn++;
						} else {
							nextRow++;
						}
					}
					break;
				}

				// Vérifier si la prochaine case est valide
				if (crossword.correctCoords(nextRow, nextColumn) && !crossword.isBlackSquare(nextRow, nextColumn)) {
					CrosswordSquare nextSquare = crossword.getCell(nextRow, nextColumn);
					nextSquare.requestFocus();
				}
			}
		});
	}
}
