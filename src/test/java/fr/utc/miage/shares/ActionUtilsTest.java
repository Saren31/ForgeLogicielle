/*
* Copyright 2025 Mengyi YANG & Yan LIANG;,
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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionUtilsTest {

    @Test
    void testAfficherDetailsActionSimple() {
        ActionSimple action = new ActionSimple("TestSimple");
        Jour jour1 = new Jour(2025, 100);
        Jour jour2 = new Jour(2025, 101);
        action.enrgCours(jour1, 10.5f);
        action.enrgCours(jour2, 12.3f);

        String details = ActionUtils.afficherDetails(action);
        System.out.println(details); 

        assertTrue(details.contains("TestSimple"));
        assertTrue(details.contains("Type : Action simple"));
        assertTrue(details.contains(jour1.toString()));
        assertTrue(details.contains("10.5"));
        assertTrue(details.contains(jour2.toString()));
        assertTrue(details.contains("12.3"));
    }

    @Test
    void testAfficherDetailsActionComposee() {
        ActionSimple a1 = new ActionSimple("A1");
        ActionSimple a2 = new ActionSimple("A2");
        ActionComposee ac = new ActionComposee("Composée");
        ac.ajouterComposant(a1, 0.4f);
        ac.ajouterComposant(a2, 0.6f);

        String details = ActionUtils.afficherDetails(ac);
        System.out.println(details); 

        assertTrue(details.contains("Composée"));
        assertTrue(details.contains("Type : Action composée"));
        assertTrue(details.contains("A1"));
        assertTrue(details.contains("40.0%"));
        assertTrue(details.contains("A2"));
        assertTrue(details.contains("60.0%"));
    }
}