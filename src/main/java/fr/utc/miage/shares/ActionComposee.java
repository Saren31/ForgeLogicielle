/*
 * Copyright 2025 Mengyi YANG & Yan.Liang

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

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;

public class ActionComposee extends Action {

    private final Map<ActionSimple, Float> composants;

    public ActionComposee(String libelle) {
        super(libelle);
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("libelle invalide");
        }
        this.composants = new HashMap<>();
    }

    public void ajouterComposant(ActionSimple action, float proportion) {
        if (action == null) {
            throw new IllegalArgumentException("action composant ne peut pas être null");
        }
        if (proportion <= 0.0f || proportion > 1.0f) {
            throw new IllegalArgumentException("proportion doit être entre 0 et 1");
        }
        composants.put(action, proportion);
    }

    @Override
    public float valeur(Jour j) {
        float somme = 0.0f;
        for (Map.Entry<ActionSimple, Float> entry : composants.entrySet()) {
            Action action = entry.getKey();
            float proportion = entry.getValue();
            somme += proportion * action.valeur(j);
        }
        return somme;
    }

    public Map<ActionSimple, Float> getComposants() {
        return composants;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Action composée : " + getLibelle() + "\nComposition :\n");
        for (Map.Entry<ActionSimple, Float> entry : composants.entrySet()) {
            sb.append("- ").append(entry.getKey().getLibelle())
              .append(" : ").append(entry.getValue() * 100).append("%\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionComposee)) return false;
        if (!super.equals(o)) return false;
        ActionComposee that = (ActionComposee) o;
        return composants.equals(that.composants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), composants);
    }
}