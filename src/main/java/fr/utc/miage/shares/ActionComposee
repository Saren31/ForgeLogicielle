/*
 * Copyright 2025 Yan.Liang.
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

public class ActionComposee extends Action {

    private final Map<ActionSimple, Float> composition;

    public ActionComposee(String libelle, Map<ActionSimple, Float> composition) {
        super(libelle); 
        this.composition = composition;
    }

    public Map<ActionSimple, Float> getComposition() {
        return composition;
    }

    @Override
    public float valeur(Jour j) {
        float total = 0f;
        for (Map.Entry<ActionSimple, Float> entry : composition.entrySet()) {
            total += entry.getKey().valeur(j) * entry.getValue();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Action composée : " + getLibelle() + "\nComposition :\n");
        for (Map.Entry<ActionSimple, Float> entry : composition.entrySet()) {
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
        return composition.equals(that.composition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), composition);
    }
}
