package bah.tahi.crossword.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bah.tahi.crossword.Database;

public class TestDatabase {

	private final Database db = new Database();

	@BeforeEach
	public void testConnection() {
		assertTrue(db.connected());
	}

	@Test
	public void testAvailableGrids() {
		Map<Integer, String> grids = db.availableGrids();
		String expectedGridName = "Français débutants (7x6)";
		String actualGridName = grids.get(10);

		assertEquals(expectedGridName, actualGridName);
	}
}