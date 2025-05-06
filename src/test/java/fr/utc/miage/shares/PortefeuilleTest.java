package fr.utc.miage.shares;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PortefeuilleTest {

    @Test
    public void testUS6_1_achatReussi() {
        // Arrange
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setSolde(1000.0);
        ActionSimple action = new ActionSimple("TestAction");
        Jour jour = new Jour(2025, 126);
        action.enrgCours(jour, 50.0f); // Enregistrer un prix pour le jour

        // Act
        portefeuille.acheterAction(action, 3, jour);

        // Assert
        assertEquals(850.0, portefeuille.getSolde(), 0.001);
        assertEquals(3, portefeuille.getActions().size());
        assertTrue(portefeuille.getActions().stream().allMatch(a -> a.equals(action)));
    }

    @Test
    public void testUS6_2_quantiteInvalide() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setSolde(1000.0);
        ActionSimple action = new ActionSimple("TestAction");
        Jour jour = new Jour(2025, 126);
        action.enrgCours(jour, 50.0f);

        // Quantité 0
        assertThrows(IllegalArgumentException.class, () -> {
            portefeuille.acheterAction(action, 0, jour);
        });

        // Quantité négative
        assertThrows(IllegalArgumentException.class, () -> {
            portefeuille.acheterAction(action, -2, jour);
        });
    }

    @Test
    public void testUS6_2_soldeInsuffisant() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setSolde(20.0);
        ActionSimple action = new ActionSimple("ActionChère");
        Jour jour = new Jour(2025, 126);
        action.enrgCours(jour, 100.0f);

        assertThrows(IllegalStateException.class, () -> {
            portefeuille.acheterAction(action, 1, jour);
        });
    }

    @Test
    public void testUS6_2_actionSansValeurPourLeJour() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setSolde(1000.0);
        ActionSimple action = new ActionSimple("ActionSansCours");
        Jour jour = new Jour(2025, 126);
        // Aucun cours enregistré

        assertThrows(IllegalArgumentException.class, () -> {
            portefeuille.acheterAction(action, 1, jour);
        });
    }

    @Test
public void testUS6_3_actionNulle() {
    Portefeuille portefeuille = new Portefeuille();
    portefeuille.setSolde(1000.0);
    Jour jour = new Jour(2025, 126);

    assertThrows(IllegalArgumentException.class, () -> {
        portefeuille.acheterAction(null, 1, jour);
    });
}

}
