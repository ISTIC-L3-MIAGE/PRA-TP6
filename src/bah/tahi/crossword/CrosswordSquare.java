package bah.tahi.crossword;

import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Classe représentant les cases d'une grille.
 */
public class CrosswordSquare extends Label {

	/**
	 * Le caractère d'une case vide.
	 */
	public static final String BLANK_CHAR = " ";

	/**
	 * Solution de la case.
	 */
	private char solution;

	/**
	 * Proposition faite par le joueur.
	 */
	private final StringProperty proposition = new SimpleStringProperty(BLANK_CHAR);

	/**
	 * La définition du mot horizontal sur cette case.
	 */
	private Clue horizontal;

	/**
	 * La définition du mot vertical sur cette case.
	 */
	private Clue vertical;

	/**
	 * Noircissement de la case.
	 */
	private final BooleanProperty black = new SimpleBooleanProperty(true);

	/**
	 * @return l'observateur de la proposition faite par le joueur.
	 */
	public final StringProperty propositionProperty() {
		return proposition;
	}

	/**
	 * @return l'observateur du noiricissement de la case.
	 */
	public final BooleanProperty blackProperty() {
		return black;
	}

	/**
	 * Permet d'affecter une solution à la case.
	 * 
	 * @param solution la solution à affecter.
	 */
	public final void setSolution(char solution) {
		this.solution = solution;
	}

	/**
	 * @return la solution de la case.
	 */
	public final char getSolution() {
		return solution;
	}

	/**
	 * Permet d'affecter une définition à la case.
	 * 
	 * @param definition la definition à affecter.
	 */
	public final void setDefinition(Clue definition, boolean horizontal) {
		if (horizontal) {
			this.horizontal = definition;
		} else {
			this.vertical = definition;
		}
	}

	/**
	 * @param horizontal true pour avoir la définition du mot horizontal, false pour
	 *                   la définition du mot vertical.
	 * @return la définition de la case.
	 */
	public final String getDefinition(boolean horizontal) {
		return horizontal ? this.horizontal.getClue() : vertical.getClue();
	}

	/**
	 * Définir this comme case courante.
	 */
	public final void setAsCurrentSquare() {
		if (!blackProperty().get()) {
			requestFocus();
		}
	}

	/**
	 * Vérifie si la proposition faite par le joueur dans cette case correspond à la
	 * solution.
	 * 
	 * @return true si la proposition faite par le joueur correspond à la solution
	 *         de la case, false sinon.
	 */
	public final boolean checkProposition() {
		if (!blackProperty().get()) {
			if (propositionProperty().get().charAt(0) == getSolution()) {
				setBackground(UIDesign.greenBg);
				return true;
			} else {
				setBackground(UIDesign.whiteBg);
				return false;
			}
		}

		return true;
	}

	/**
	 * Constructeur.
	 */
	public CrosswordSquare(final Crossword crossword, final int row, final int column) {
		// Animations
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), this);
		scaleTransition.setFromX(0); // Échelle de départ (rétrécie)
		scaleTransition.setFromY(0);
		scaleTransition.setToX(1.0); // Échelle d'arrivée (taile normale)
		scaleTransition.setToY(1.0);

		// Styles
		Font normalFont = new Font((double) 150 / crossword.getHeight()); // Police

		setFont(normalFont);
		setBorder(UIDesign.border);
		setAlignment(Pos.CENTER);
		setBackground(UIDesign.blackBg);
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		// Bindings
		textProperty().bind(propositionProperty());

		// Les observateurs
		blackProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				propositionProperty().set(null);
				setBackground(UIDesign.blackBg);
			} else {
				propositionProperty().set(BLANK_CHAR);
				setBackground(UIDesign.whiteBg);
			}
		});

		focusedProperty().addListener((observable, oldValue, newValue) -> {
			setBorder(newValue ? UIDesign.focusedBorder : UIDesign.border);
			if (horizontal != null) {
				// crossword.getHorizontalClues()
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

				if (!newValue.equals(oldValue)) {
					setBackground(UIDesign.whiteBg);
				}
			}
		});

		// Les évènements liés à la souris
		setOnMouseEntered(event -> {
			if (!blackProperty().get()) {
				setCursor(Cursor.HAND); // On change le curseur au survol de la souris d'une case qui n'est pas noire.
			}
		});

		setOnMouseExited(event -> {
			if (!blackProperty().get()) {
				setCursor(Cursor.DEFAULT); // On remet le curseur par défaut de la souris.
			}
		});

		setOnMouseClicked(event -> setAsCurrentSquare());

		// Détection des touches du clavier
		setOnKeyPressed(event -> {
			if (isFocused()) {
				int nextRow = row, nextColumn = column;

				switch (event.getCode()) {

				case UP: // Touche directionnelle HAUT
					crossword.directionProperty().set(Direction.VERTICAL);
					nextRow--;
					break;

				case DOWN: // Touche directionnelle BAS
					crossword.directionProperty().set(Direction.VERTICAL);
					nextRow++;
					break;

				case LEFT: // Touche directionnelle GAUCHE
					crossword.directionProperty().set(Direction.HORIZONTAL);
					nextColumn--;
					break;

				case RIGHT: // Touche directionnelle DROITE
					crossword.directionProperty().set(Direction.HORIZONTAL);
					nextColumn++;
					break;

				case BACK_SPACE: // Touche d'effacement
					propositionProperty().set(BLANK_CHAR);
					if (crossword.directionProperty().get().equals(Direction.HORIZONTAL)) {
						nextColumn--;
					} else {
						nextRow--;
					}
					break;

				case ENTER: // Touche entrée
					crossword.checkWin();
					break;

				default: // Les autres touches
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
