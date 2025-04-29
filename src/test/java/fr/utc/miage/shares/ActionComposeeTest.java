package fr.utc.miage.shares;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionComposeeTest {

    private static class FakeAction extends Action {
        private final float valeur;

        public FakeAction(String libelle, float valeur) {
            super(libelle);
            this.valeur = valeur;
        }

        @Override
        public float valeur(Jour j) {
            return valeur;
        }
    }

    @Test
    void testCreationEtValeurActionComposee() {
        Action a1 = new FakeAction("A1", 100f);
        Action a2 = new FakeAction("A2", 50f);

        ActionComposee ac = new ActionComposee("CompoA1A2");
        ac.ajouterComposant(a1, 0.6f);
        ac.ajouterComposant(a2, 0.4f);

        assertEquals("CompoA1A2", ac.getLibelle());

        float expected = 80f;
        float actual = ac.valeur(null);
        assertEquals(expected, actual, 0.0001f);
    }


    @Test
    void testEchecCreationSiLibelleInvalide() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new FakeAction(null, 100f);
        });

        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new FakeAction("", 100f);
        });

        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> {
            new FakeAction("   ", 100f);
        });

        Exception ex4 = assertThrows(IllegalArgumentException.class, () -> {
            new ActionComposee("  ");
        });

        assertTrue(ex1.getMessage().contains("libelle"));
        assertTrue(ex2.getMessage().contains("libelle"));
        assertTrue(ex3.getMessage().contains("libelle"));
        assertTrue(ex4.getMessage().contains("libelle"));
    }
}
