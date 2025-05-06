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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the behavior of a Portefeuille
 *
 * @author Lucas Veslin, Wenfried ANIFRANI
 */
public class Portefeuille {
    
    // attribut comportant le nom du possesseur du portefeuille
    private final String nom;

    // attribut comportant la liste des actions
    private final List<Action> actions;

    // attribut comportant le solde du portefeuille
    private double solde;

    /**
     * Builds a Portefeuille object
     *
     * @param n the name of the action object
     */
    public Portefeuille(final String n) {
        if (n == null || n.isEmpty()) {
            throw new IllegalArgumentException("Le nom ne doit pas etre vide ou null");
        }

        this.nom = n;
        this.actions = new LinkedList<>();
        this.solde = 0.0;
    }


    /**
     * Ajouter une action a la liste
     * @param action action qui va etre ajoutee 
     */
    public void ajouterAction( final Action action) {
        this.actions.add(action);
    }

    /**
     * Retirer une action a la liste
     * @param action action qui va etre retiree
     */
    public void retirerAction(final Action action) {
        this.actions.remove(action);
    }

    /**
     * getteur de la valeur du nom
     * @return la valeur du nom 
     */
    public String getNom() {
        return nom;
    }

    /**
     * getteur de la valeur du solde
     * @return la valeur du nom
     */
    public double getSolde() {
        return solde;
    }

    /**
     * setteur de la valeur du solde
     * @param solde de la valeur du solde
     */
    public void setSolde(double solde) {
        this.solde = solde;
    }

    /**
     * getteur de la valeur de la liste d'actions
     * @return la valeur de la liste d'actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Calcule la valeur totale du portefeuille
     * @return la valeur toltale du portefeuille
     */
    public double valeurTotale() {
        return 0.0; 
    }

    @Override
    public String toString() {
        return "Portefeuille [getSolde()=" + getSolde() + ", getClass()=" + getClass() + ", getActions()="
                + getActions() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actions == null) ? 0 : actions.hashCode());
        long temp;
        temp = Double.doubleToLongBits(solde);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Portefeuille other = (Portefeuille) obj;
        if (actions == null) {
            if (other.actions != null)
                return false;
        } else if (!actions.equals(other.actions))
            return false;
        if (Double.doubleToLongBits(solde) != Double.doubleToLongBits(other.solde))
            return false;
        return true;
    }
    
    public void acheterAction(ActionSimple action, int quantite, Jour jour) {
      if (action == null || quantite <= 0) {
          throw new IllegalArgumentException("Action invalide ou quantité invalide");
      }

      float prixAction = action.valeur(jour);
      double coutTotal = prixAction * quantite;

      if (prixAction == 0) {
          throw new IllegalArgumentException("L'action n'a pas de prix pour ce jour");
      }

      if (coutTotal > solde) {
          throw new IllegalStateException("Solde insuffisant pour l'achat");
      }

      // Débiter le solde
      solde -= coutTotal;

      // Ajouter les actions au portefeuille
      for (int i = 0; i < quantite; i++) {
          this.actions.add(action);
      }
  }
  /**
 * Permet de vendre une ou plusieurs actions composées.
 *
 * @param actionComposee l'action composée à vendre
 * @param quantite       la quantité à vendre
 * @param jour           le jour de la vente
 *
 * @throws IllegalArgumentException si l'action est nulle alors la quantité est invalide ou l'action n'a pas de valeur pour ce jour
 * @throws IllegalStateException    si le portefeuille ne contient pas assez de cette action composée
 */
public void vendreActionComposee(ActionComposee actionComposee, int quantite, Jour jour) {
    if (actionComposee == null || quantite <= 0) {
        throw new IllegalArgumentException("Action invalide ou quantité invalide");
    }

    float prixUnitaire = actionComposee.valeur(jour);
    if (prixUnitaire == 0.0f) {
        throw new IllegalArgumentException("L'action composée n'a pas de prix pour ce jour");
    }

    // Compter combien d'actions identiques sont présentes
    int count = 0;
    for (Action a : actions) {
        if (a.equals(actionComposee)) {
            count++;
        }
    }

    if (count < quantite) {
        throw new IllegalStateException("Pas assez d'actions composées à vendre");
    }

    // Supprimer les bonnes actions
    int supprimees = 0;
    List<Action> nouvelles = new LinkedList<>();
    for (Action a : actions) {
        if (a.equals(actionComposee) && supprimees < quantite) {
            supprimees++;
        } else {
            nouvelles.add(a);
        }
    }
    this.actions.clear();
    this.actions.addAll(nouvelles);

    // Créditer le solde
    solde += prixUnitaire * quantite;
}
}  

