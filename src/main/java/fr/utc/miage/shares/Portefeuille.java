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
  
/**
 * Permet à l'utilisateur d'acheter une ou plusieurs actions simples pour un jour donné
 * 
 * Si le prix de l'action est connu pour ce jour et que le solde est suffisant alors
 * les actions sont ajoutées au portefeuille et le solde est débité du coût total.
 * 
 * @param action l'action simple à acheter
 * @param quantite la quantite d'action à acheter
 * @param jour le jour auquel on achete l'action et qui determine donc la valeur
 * 
 * @throws IllegalArgumentException si action null, si quantite <= 0,
 *                                  ou si l'action n'a pas de prix pour ce jour
 * @throws IllegalStateException    si le solde est insuffisant pour l'achat
 */    
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

/**
 * Permet à l'utilisateur de vendre une ou plusieurs actions simples pour un jour donné.
 * 
 * Si le prix de l'action est connu pour ce jour et que le portefeuille contient
 * suffisamment d'actions correspondantes alors elles sont retirées du portefeuille
 * et le solde est crédité du montant total de la vente.
 * 
 *
 * @param action l'action simple à vendre
 * @param quantite la quantite d'action à vendre
 * @param jour le jour auquel on vend l'action et qui determine donc la valeur
 *
 * @throws IllegalArgumentException si action null, si quantite <= 0,
 *                                  ou si l'action n'a pas de prix pour ce jour
 * @throws IllegalStateException    si le solde est insuffisant pour la vente
 * 
 */
public void vendreAction(ActionSimple action, int quantite, Jour jour) {
    if (action == null || quantite <= 0) {
        throw new IllegalArgumentException("Action invalide ou quantité invalide");
    }

    float prixAction = action.valeur(jour);
    if (prixAction == 0) {
        throw new IllegalArgumentException("L'action n'a pas de prix pour ce jour");
    }

    //compter le nombre d'occurrences de cette action dans le portefeuille
    int count = 0;
    for (Action a : actions) {
        if (a.equals(action)) {
            count++;
        }
    }
    if (count < quantite) {
        throw new IllegalStateException("Pas assez d'actions à vendre dans le portefeuille");
    }

    //retirer les actions
    int supprimees = 0;
    List<Action> nouvellesActions = new ArrayList<>();
    for (Action a : actions) {
        if (a.equals(action) && supprimees < quantite) {
            supprimees++;
        } else {
            nouvellesActions.add(a);
        }
    }
    this.actions = nouvellesActions;

    //créditer le solde
    solde += prixAction * quantite;
}

}

