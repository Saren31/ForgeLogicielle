/*
 * Copyright 2025 Mengyi YANG et Yan Liang
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
 import java.util.HashMap;
 
 public class ActionComposee extends Action {
 
     private final Map<Action, Float> composants;
 
     @Deprecated
     public ActionComposee(String libelle) {
        super(libelle);
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("libelle invalide");
        }
        this.composants = new HashMap<>();
    }

    public ActionComposee(String libelle, Map<Action, Float> composants) {
        super(libelle);
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("libelle invalide");
        }
        this.composants = new HashMap<>();
        this.ajouterComposants(composants);
    }
 
     public void ajouterComposant(Action action, float proportion) {
        if (action == null) {
            throw new IllegalArgumentException("action composant ne peut pas être null");
        }
        if (proportion <= 0.0f || proportion > 1.0f) {
            throw new IllegalArgumentException("proportion doit être entre 0 et 1");
        }
        if (composants.values().stream().mapToDouble(Float::doubleValue).sum() + proportion > 1) {
            throw new IllegalArgumentException("La somme des proportions doit etre egale a 1 ou inferieure a 1");
        }
        composants.put(action, proportion);
     }

     public void ajouterComposants(Map<Action, Float> composants) {
        if (composants == null) {
            throw new IllegalArgumentException("composants ne peut pas être null");
        }

        if (composants.values().stream().mapToDouble(Float::doubleValue).sum() != 1) {
            throw new IllegalArgumentException("La somme des proportions doit etre egale a 1");
        }
        
         for (Map.Entry<Action, Float> entry : composants.entrySet()) {
             Action action = entry.getKey();
             float proportion = entry.getValue();
             ajouterComposant(action, proportion);
         }
     }
 
     @Override
     public float valeur(Jour j) {
         float somme = 0.0f;
         for (Map.Entry<Action, Float> entry : composants.entrySet()) {
             Action action = entry.getKey();
             float proportion = entry.getValue();
             somme += proportion * action.valeur(j);
         }
         return somme;
     }
 
     public Map<Action, Float> getComposants() {
         return composants;
     }
 }