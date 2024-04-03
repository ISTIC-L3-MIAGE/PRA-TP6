package bah.tahi.crossword.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bah.tahi.crossword.models.Grid;

import static org.junit.jupiter.api.Assertions.*;

public class TestGrid {

    private Grid<String> g1;
    private Grid<String> g2;
    private int height;
    private int largeur;

    @BeforeEach
    public void setUp() throws Exception {
        height = 333;
        largeur = 555;
        g1 = new Grid<>(height, largeur);
        g2 = new Grid<>(height + 1, largeur + 1);
        System.out.println("Test de " + g1.getClass().getName());
    }

    @Test
    public void testHeightWidth() {
        assertEquals(height, g1.getHeight(), "La hauteur est égale au 1er paramètre du constructeur");
        assertEquals(largeur, g1.getWidth(),"La largeur est égale au 2e paramètre du constructeur");
    }

    @Test
    public void testCellule() {
        try {
            for (int l = 0; l < g1.getHeight(); l++) {
                String ch1 = Integer.toString(l);
                for (int c = 0; c < g1.getWidth(); c++) {
                    String ch2 = ch1 + ',' + Integer.toString(c);
                    g1.setCell(l, c, ch2);
                    g2.setCell(l, c, ch2 + "x");
                    assertEquals(ch2, g1.getCell(l, c),"La valeur restituée doit être la valeur enregistrée");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("L'indice de tableau ne doit pas dépasser height-1 (resp. largeur-1");
        }
    }

    @Test
    public void testPreconditions() {
        boolean testOK = false;
        // Précondition de Height
        try {
            new Grid(-4, 5);
            testOK = false;
        } catch (RuntimeException e) {
            testOK = true;
        }

        assertTrue(testOK, "Une Height négative doit déclencher une RuntimeException");

        // Précondition de largeur
        try {
            new Grid(4, -5);
            testOK = false;
        } catch (RuntimeException e) {
            testOK = true;
        }
        assertTrue(testOK, "Une largeur négative doit déclencher une RuntimeException");

        // Précondition de 1ère coordonnée de cellule
        try {
            g1.setCell(height + 1, 1, "hgjhgjhg");
            testOK = false;
        } catch (RuntimeException e) {
            testOK = true;
        }
        assertTrue(testOK, "Tout non-respect des bornes doit causer une RuntimeException");

        // Précondition de 2ème coordonnée de cellule
        try {
            g1.setCell(1, -1, "hgjhgjhg");
            testOK = false;
        } catch (RuntimeException e) {
            testOK = true;
        }
        assertTrue(testOK, "Tout non-respect des bornes doit causer une RuntimeException");
    }
}