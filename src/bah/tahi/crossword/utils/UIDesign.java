package bah.tahi.crossword.utils;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;

public abstract class UIDesign {
	public final static Background whiteBg = new Background(new BackgroundFill(Color.WHITE, null, null)); // Le fond des
																											// cases
	// vides
	// en
	// cours de partie
	public final static Background blackBg = new Background(new BackgroundFill(Color.BLACK, null, null)); // Le fond des
																											// cases
	// vides
	// en
	// cours de partie
	public final static Background greenBg = new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)); // Le
																												// fond
																												// des
	// cases
	// vides en cours de
	// partie
	public final static Background greyBg = new Background(new BackgroundFill(Color.LIGHTGREY, null, null)); // Le fond
																												// des
	// cases
// vides en cours de
// partie
	public final static Background redBg = new Background(new BackgroundFill(Color.RED, null, null)); // Le fond des
																										// cases
	// vides à la
// fin d'une partie
	public final static Border border = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(0.5))); // Bordure
	public final static Border focusedBorder = new Border(
			new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))); // Bordure
	// par
	// défaut
	// des
	// cases
}
