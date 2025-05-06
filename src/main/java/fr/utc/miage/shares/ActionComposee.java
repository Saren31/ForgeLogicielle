/*
 * Copyright 2025 Mengyi YANG.
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

public class ActionComposee extends Action {

    public static class Composant {
        private final Action action;
        private final float proportion; 

        public Composant(Action action, float proportion) {
            if (proportion < 0 || proportion > 1) {
                throw new IllegalArgumentException("La proportion doit être entre 0 et 1.");
            }
            this.action = action;
            this.proportion = proportion;
        }

        public Action getAction() {
            return action;
        }

        public float getProportion() {
            return proportion;
        }
    }

    private final List<Composant> composants = new ArrayList<>();

    public ActionComposee(String libelle) {
        super(libelle);
    }


    public void ajouterComposant(Action action, float proportion) {
        composants.add(new Composant(action, proportion));
    }


    @Override
    public float valeur(Jour j) {
        float total = 0;
        for (Composant c : composants) {
            total += c.getProportion() * c.getAction().valeur(j);
        }
        return total;
    }

}
