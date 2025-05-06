package fr.utc.miage.shares;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class ActionSimpleTest {

    private static final String ACTION_NOM = " valeur_nom";
    private static final int ANNEE1 = 2003;
    private static final int ANNEE2 = 2001;
    private static final int JOUR1 = 1;
    private static final int JOUR2 = 2;
    private static final float COURS1 = 10.0f;
    private static final float COURS2 = 50.0f;
    

    @Test
    void testConstructorWithCorrectParameterSucces() {
        final ActionSimple action = new ActionSimple(ACTION_NOM);
        final String libelle = action.getLibelle();

        assertEquals(ACTION_NOM, libelle,
               "la valeur du libelle doit etre la meme que le parametre utilise pour la construction");
    }
    
    @Test
    void testCoursBienEnregistrePourUnJour() {
        final ActionSimple action = new ActionSimple(ACTION_NOM);
        final Jour jour = new Jour(ANNEE1,JOUR1);
        action.enrgCours(jour, COURS1);
        final float cours = action.valeur(jour);
        
        assertEquals(COURS1, cours,
                "le cours doit etre le meme que celui enregistre pour ce jour");

    }

    @Test
    void testCoursDejaEnregistrePourUnJourNeChangePas(){
        final ActionSimple action = new ActionSimple(ACTION_NOM);
        final Jour jour = new Jour(ANNEE1,JOUR1);
        action.enrgCours(jour, COURS1);
        action.enrgCours(jour, COURS2);
        final float cours = action.valeur(jour);
        
        assertEquals(COURS1, cours,
                "le cours doit etre celui enregistre en premier meme si on a essaye de le changer");
    }

    @Test
    void testCoursParDefautPourUnJourNonEnregistre() {
        final ActionSimple action = new ActionSimple(ACTION_NOM);
        final Jour jour = new Jour(ANNEE1,JOUR1);
        final float cours = action.valeur(jour);
        
        assertEquals(0.0f, cours,
                "le cours doit etre 0.0 car il n'y a pas de cours pour ce jour");
    }





}
