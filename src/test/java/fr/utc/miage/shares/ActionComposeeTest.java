package fr.utc.miage.shares;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionComposeeTest {

    // Classe factice pour simuler des actions simples
    private static class FakeAction extends Action {
        private final float valeur;
    
    
        public FakeAction(String libelle, float valeur) {
            super(libelle);
            if (libelle == null || libelle.isBlank()) {
                throw new IllegalArgumentException("libelle invalide");
            }
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
        assertEquals(80f, ac.valeur(null), 0.0001f);
    }

    @Test
    void testEchecCreationSiLibelleInvalide() {
        assertThrows(IllegalArgumentException.class, () -> new FakeAction(null, 100f));
        assertThrows(IllegalArgumentException.class, () -> new FakeAction("", 100f));
        assertThrows(IllegalArgumentException.class, () -> new FakeAction("   ", 100f));
        assertThrows(IllegalArgumentException.class, () -> new ActionComposee("   "));
    }

    @Test
    void testAjouterComposantAvecPoidsInvalide() {
        Action a = new FakeAction("A", 100f);
        ActionComposee ac = new ActionComposee("Test");

        Exception ex4 = assertThrows(IllegalArgumentException.class, () -> {
            new ActionComposee("  ");
        });

        assertTrue(ex1.getMessage().contains("libelle"));
        assertTrue(ex2.getMessage().contains("libelle"));
        assertTrue(ex3.getMessage().contains("libelle"));
        assertTrue(ex4.getMessage().contains("libelle"));
    }
}