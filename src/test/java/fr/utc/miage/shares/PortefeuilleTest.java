/*
 * Copyright 2025 David Navarre &lt;David.Navarre at irit.fr&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PortefeuilleTest {
    
    private static final String VALEUR_NOM1 = "Valeur nom 1";
    private static final String VALEUR_NOM2 = "Valeur nom 2";

    @Test
    void testConstructorWithCorrectParameterSucces(){
        assertDoesNotThrow(
            () -> new Portefeuille(VALEUR_NOM1));
    }

    @Test
    void testConstructorWithIncorrectParametersIllegaleArgumentException(){
            assertAll("Constructor parameter",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Portefeuille(null)),
                    
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Portefeuille(""))
            
            );
                
    }


    @Test
     void testAllGettersShouldWork(){

        final Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
        final String resultNom = portefeuille.getNom();
         final double resultSolde = portefeuille.getSolde();
         final List<Action> resultActions = portefeuille.getActions();
 
         assertAll("Getters",
             () -> assertEquals(VALEUR_NOM1, resultNom,
                 "Le nom du portefeuille doit correspondre a celui passé en parametre"),
             () -> assertEquals(0.0, resultSolde,
                 "Le solde du portefeuille doit etre initialise a 0.0"),
             () -> assertEquals(new LinkedList<>(), resultActions,
                 "La liste d'actions du portefeuille doit etre vide")
         );
     }
 
     @Test
     void testAjouteUneActionDansLePortefeuilleAvecSuccès(){

        final Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
        final Action action = new ActionSimple(VALEUR_NOM2);
        portefeuille.ajouterAction(action);
        final Action actionPortefeuille = portefeuille.getActions().get(0);

        assertEquals(action, actionPortefeuille,
         "L'action doit etre la meme que celle ajoutee dans le portefeuille");  
        
     }
     @Test
     void testRetireUneActionDuPortefeuilleAvecSuccès(){

        final Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
        final Action action = new ActionSimple(VALEUR_NOM2);
        portefeuille.ajouterAction(action);
        portefeuille.retirerAction(action);
        final int nombreActionsRetirees = portefeuille.getActions().size();

        assertEquals(0,nombreActionsRetirees,   
         "Le portefeuille doit etre vide apres le retrait de l'action");   
         
     }

     @Test
     void testEqualsShouldWork(){

        final Portefeuille portefeuille1 = new Portefeuille(VALEUR_NOM1);
        final Portefeuille portefeuille2 = new Portefeuille(VALEUR_NOM1);
        final Portefeuille portefeuille3 = new Portefeuille(VALEUR_NOM2);

        assertAll("Equals",
            () -> assertEquals(portefeuille1, portefeuille2,
                "Deux portefeuilles avec le meme nom doivent etre egaux"),
            () -> assertEquals(portefeuille1, portefeuille1,
                "Un portefeuille doit etre egal a lui-meme")
        );
     }

     @Test
     void testEqualsShouldNotWork(){
        final Portefeuille portefeuille1 = new Portefeuille(VALEUR_NOM1);
        final Portefeuille portefeuille2 = new Portefeuille(VALEUR_NOM2);
        final Integer portefeuille3 = 1;
        final Portefeuille portefeuille4 = new Portefeuille(VALEUR_NOM1);

        final ActionSimple action = new ActionSimple(VALEUR_NOM1);
        portefeuille1.ajouterAction(action);

        assertAll("Equals",
            () -> assertNotEquals(portefeuille1, portefeuille2,
                "Deux portefeuilles avec des noms differents ne doivent pas etre egaux"),
            () -> assertNotEquals(portefeuille1, null, 
                "Un portefeuille ne peut pas etre egal a un objet d'une classe differente"),
            () -> assertNotEquals(portefeuille1, portefeuille3,
                "Un portefeuille ne doit pas etre egal a un objet d'un autre type"),
            () -> assertNotEquals(portefeuille1, portefeuille4,
                "Deux portefeuilles avec le meme nom mais des actions differentes ne doivent pas etre egaux")
        );
     }

    @Test
    void testUS6_1_achatReussi() {
        // Arrange
        Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
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
    void testUS6_2_quantiteInvalide() {
        Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
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
    void testUS6_2_soldeInsuffisant() {
        Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
        portefeuille.setSolde(20.0);
        ActionSimple action = new ActionSimple("ActionChère");
        Jour jour = new Jour(2025, 126);
        action.enrgCours(jour, 100.0f);

        assertThrows(IllegalStateException.class, () -> {
            portefeuille.acheterAction(action, 1, jour);
        });
    }

    @Test
    void testUS6_2_actionSansValeurPourLeJour() {
        Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
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
@Test
public void testUS8_1_venteReussie() {
    // Arrange
    Portefeuille portefeuille = new Portefeuille();
    portefeuille.setSolde(0.0);
    ActionSimple action = new ActionSimple("ActionTest");
    Jour jour = new Jour(2025, 150);
    action.enrgCours(jour, 100.0f);

    // Simuler l'achat préalable de 2 actions
    portefeuille.ajouterAction(action);
    portefeuille.ajouterAction(action);

    // Act
    portefeuille.vendreAction(action, 1, jour);

    // Assert
    assertEquals(100.0, portefeuille.getSolde(), 0.001);
    assertEquals(1, portefeuille.getActions().size());
    assertTrue(portefeuille.getActions().contains(action));
}
@Test
public void testUS8_2_quantiteExcessive() {
    // Arrange
    Portefeuille portefeuille = new Portefeuille();
    portefeuille.setSolde(0.0);
    ActionSimple action = new ActionSimple("ActionTest");
    Jour jour = new Jour(2025, 151);
    action.enrgCours(jour, 100.0f);

    // Simuler 1 action seulement
    portefeuille.ajouterAction(action);

    // Act & Assert
    assertThrows(IllegalStateException.class, () -> {
        portefeuille.vendreAction(action, 2, jour);
    });

    // Vérifier que rien n’a changé
    assertEquals(0.0, portefeuille.getSolde(), 0.001);
    assertEquals(1, portefeuille.getActions().size());
}
@Test
public void testUS8_3_actionNulleOuQuantiteInvalide() {
    Portefeuille portefeuille = new Portefeuille();
    portefeuille.setSolde(0.0);
    ActionSimple action = new ActionSimple("TestAction");
    Jour jour = new Jour(2025, 152);
    action.enrgCours(jour, 100.0f);

    // Vente avec action null
    assertThrows(IllegalArgumentException.class, () -> {
        portefeuille.vendreAction(null, 1, jour);
    });

    // Vente avec quantité nulle
    assertThrows(IllegalArgumentException.class, () -> {
        portefeuille.vendreAction(action, 0, jour);
    });

    // Vente avec quantité négative
    assertThrows(IllegalArgumentException.class, () -> {
        portefeuille.vendreAction(action, -2, jour);
    });
}
@Test
public void testUS8_4_actionSansCoursPourJour() {
    Portefeuille portefeuille = new Portefeuille();
    portefeuille.setSolde(0.0);
    ActionSimple action = new ActionSimple("TestAction");
    Jour jour = new Jour(2025, 153);

    // On ajoute une action dans le portefeuille sans enregistrer de cours
    portefeuille.ajouterAction(action);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
        portefeuille.vendreAction(action, 1, jour);
    });

    // Vérifie qu'aucune action n'a été retirée
    assertEquals(1, portefeuille.getActions().size());
    assertEquals(0.0, portefeuille.getSolde(), 0.001);
}
@Test
public void testUS8_5_ventePartielleConserveLeReste() {
    Portefeuille portefeuille = new Portefeuille();
    portefeuille.setSolde(0.0);
    ActionSimple action = new ActionSimple("TestAction");
    Jour jour = new Jour(2025, 154);
    action.enrgCours(jour, 100.0f);

    // Ajouter 5 actions
    for (int i = 0; i < 5; i++) {
        portefeuille.ajouterAction(action);
    }

    // Vendre 2 actions
    portefeuille.vendreAction(action, 2, jour);

    // Assert
    assertEquals(200.0, portefeuille.getSolde(), 0.001); 
    assertEquals(3, portefeuille.getActions().size());
    assertTrue(portefeuille.getActions().stream().allMatch(a -> a.equals(action)));
}
@Test
public void testUS8_6_venteNeRetireQueLesActionsIdentiques() {
    Portefeuille portefeuille = new Portefeuille();
    portefeuille.setSolde(0.0);

    ActionSimple action1 = new ActionSimple("ActionA"); // celle qu'on vend
    ActionSimple action2 = new ActionSimple("ActionB"); // doit rester
    Jour jour = new Jour(2025, 155);

    action1.enrgCours(jour, 100.0f);
    action2.enrgCours(jour, 200.0f);

    // Ajouter 2 × action1 et 1 × action2
    portefeuille.ajouterAction(action1);
    portefeuille.ajouterAction(action1);
    portefeuille.ajouterAction(action2);

    // Vendre 1 × action1
    portefeuille.vendreAction(action1, 1, jour);

    // Assert
    assertEquals(100.0, portefeuille.getSolde(), 0.001);
    assertEquals(2, portefeuille.getActions().size());
    
    // Vérifie qu’il reste 1 × action1 et 1 × action2
    long remainingAction1 = portefeuille.getActions().stream().filter(a -> a.equals(action1)).count();
    long remainingAction2 = portefeuille.getActions().stream().filter(a -> a.equals(action2)).count();

    assertEquals(1, remainingAction1);
    assertEquals(1, remainingAction2);
}

    void testUS6_3_actionNulle() {
      Portefeuille portefeuille = new Portefeuille(VALEUR_NOM1);
      portefeuille.setSolde(1000.0);
      Jour jour = new Jour(2025, 126);

      assertThrows(IllegalArgumentException.class, () -> {
          portefeuille.acheterAction(null, 1, jour);
      });
    }
}