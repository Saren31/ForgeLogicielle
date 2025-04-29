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


    

        
}
