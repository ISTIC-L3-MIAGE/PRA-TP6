package bah.tahi.crossword;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CrosswordSquare extends Label {

	/**
	 * Instance du modèle de jeu
	 */
	private static final Crossword model = Crossword.getInstance();

	private String solution;
	private String proposition;
	private String horizontal;
	private String vertical;
	private final boolean isBlack;

	public CrosswordSquare(final int row, final int column) {
		// Initialisation
		isBlack = model.isBlackSquare(row, column);

		// Styles
		Font normalFont = new Font((double) 100 / model.getHeight()); // Police normale
		Font bigFont = new Font((double) 150 / model.getHeight()); // Police en cas de victoire
		Background whiteBg = new Background(new BackgroundFill(Color.WHITE, null, null)); // Le fond des cases vides en
																							// cours de partie
		Background blackBg = new Background(new BackgroundFill(Color.BLACK, null, null)); // Le fond des cases vides en
																							// cours de partie
		Background greenBg = new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)); // Le fond des cases
																								// vides en cours de
																								// partie
		Background redBg = new Background(new BackgroundFill(Color.RED, null, null)); // Le fond des cases vides à la
																						// fin d'une partie
		Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(0.5))); // Bordure
																															// par
																															// défaut
																															// des
																															// cases
		setText("A");
		// setEditable(false); // Les cases ne sont pas modifiables au clavier
		setFocusTraversable(false); // Inutile de changer le focus des cases
		setFont(normalFont); // On applique la police normale à l'initialisation
		setBorder(border); // On applique les bordures à l'initialisation
		setAlignment(Pos.CENTER); // Centrer le texte dans une case
		setBackground(whiteBg); // Couleur par défaut d'une case
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Taille pour que la case occupe tout l'espace disponible dans

		// Bindings
		// ownerProperty().bind(model.getSquare(row, column));
		if (isBlack) {
			setText("");
			setBackground(blackBg);
		} else {
			setBackground(whiteBg);
		}

	}
}
