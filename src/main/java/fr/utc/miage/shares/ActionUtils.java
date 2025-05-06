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

import java.util.Map;

public class ActionUtils {

    /**
     * Affiche les détails d'une action.
     * @param action l'action à examiner
     * @return une chaîne descriptive
     */
    public static String afficherDetails(Action action) {
        StringBuilder builder = new StringBuilder();
        builder.append("Nom : ").append(action.getLibelle()).append("\n");

        if (action instanceof ActionSimple) {
            builder.append("Type : Action simple\n");
            builder.append("Cours disponibles :\n");
            ActionSimple simple = (ActionSimple) action;
            for (Map.Entry<Jour, Float> entry : simple.getMapCours().entrySet()) {
                builder.append("- ").append(entry.getKey())
                        .append(" : ").append(entry.getValue()).append("\n");
            }

        } else if (action instanceof ActionComposee) {
            builder.append("Type : Action composée\n");
            builder.append("Composition :\n");
            ActionComposee composee = (ActionComposee) action;
            for (Map.Entry<Action, Float> entry : composee.getComposants().entrySet()) {
                String libelle = entry.getKey().getLibelle();
                float proportion = entry.getValue();
                builder.append("- ").append(libelle)
                        .append(" (").append(String.format("%.1f", proportion * 100)).append("%)\n");
            }
        } else {
            builder.append("Type : Inconnu\n");
        }
        return builder.toString();
    }
}