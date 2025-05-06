package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ActionComposeeTest {
    private static final String VALEUR_LIBELLE = "Valeur libelle";
    private static final String VALEUR_LIBELLE2 = "Valeur libelle 2";
    private static final float VALEUR_COURS = 100.0f;
    private static final float VALEUR_COURS2 = 200.0f;

    /** 

    // Classe factice pour simuler des actions simples
    private static class FakeAction extends Action {
        private final float valeur;
    
        public FakeAction(String libelle, float valeur) {
            super(libelle);
            if (libelle == null || libelle.trim().isEmpty()) {
                throw new IllegalArgumentException("libelle ne doit pas être nul ou vide");
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

        assertThrows(IllegalArgumentException.class, () -> ac.ajouterComposant(a, 0f));
        assertThrows(IllegalArgumentException.class, () -> ac.ajouterComposant(a, -1f));
    }

    @Test
    void testAjouterComposantDeuxFois() {
        Action a = new FakeAction("A", 200f);
        ActionComposee ac = new ActionComposee("Double");

        ac.ajouterComposant(a, 0.5f);
        ac.ajouterComposant(a, 0.25f); // doit remplacer le précédent poids

        assertEquals(50f, ac.valeur(null), 0.0001f);
    }

    @Test
    void testToStringEtGetLibelle() {
        ActionComposee ac = new ActionComposee("TestLabel");
        assertEquals("TestLabel", ac.getLibelle());
        assertEquals("TestLabel", ac.toString());
    }

    @Test
    void testValeurAvecPlusieursComposants() {
        Action a1 = new FakeAction("A1", 10f);
        Action a2 = new FakeAction("A2", 20f);
        Action a3 = new FakeAction("A3", 30f);

        ActionComposee ac = new ActionComposee("Multi");
        ac.ajouterComposant(a1, 0.1f); // 1
        ac.ajouterComposant(a2, 0.3f); // 6
        ac.ajouterComposant(a3, 0.6f); // 18

        assertEquals(25f, ac.valeur(null), 0.0001f);
    }

    @Test
    void testGetComposants() {
    Action a = new FakeAction("A", 100f);
    ActionComposee ac = new ActionComposee("Test");
    ac.ajouterComposant(a, 0.7f);

    Map<Action, Float> composants = ac.getComposants();
    assertEquals(1, composants.size());
    assertEquals(0.7f, composants.get(a));
}*/
    @Test 
    void testValeurActionComposee() {
       final ActionComposee actionComposee = new ActionComposee("ActionComposeeTest");
         final ActionSimple action1 = new ActionSimple(VALEUR_LIBELLE);
         final ActionSimple action2 = new ActionSimple(VALEUR_LIBELLE2);
        actionComposee.ajouterComposant(action1, 0.5f);
        actionComposee.ajouterComposant(action2, 0.5f);
        final double valeurAttendue = (VALEUR_COURS + VALEUR_COURS2) / 2.0;
        final double valeurActionComposee = actionComposee.valeur(null);

        assertEquals(valeurAttendue, valeurActionComposee, 0.01,
         "La valeur de l'action composée est incorrecte");
         



        

        

        
   
    }

}
