package fr.utc.miage.shares;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Portefeuille {
    
    private List<Action> actions;
    private double solde;

    public Portefeuille() {
        this.actions = new ArrayList<>();
        this.solde = 0.0;
    }

    public void ajouterAction(Action action) {
        this.actions.add(action);
    }

    public void retirerAction(Action action) {
        this.actions.remove(action);
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public double valeurTotale() {
        return 0.0; 
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Portefeuille :\n");
        sb.append("Solde : ").append(solde).append("\n");
        sb.append("Actions :\n");
        for (Action action : actions) {
            sb.append(action.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portefeuille that = (Portefeuille) o;
        return Double.compare(that.solde, solde) == 0 && Objects.equals(actions, that.actions);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(actions, solde);
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
    int supprimées = 0;
    List<Action> nouvellesActions = new ArrayList<>();
    for (Action a : actions) {
        if (a.equals(action) && supprimées < quantite) {
            supprimées++;
        } else {
            nouvellesActions.add(a);
        }
    }
    this.actions = nouvellesActions;

    //créditer le solde
    solde += prixAction * quantite;
}
}

  

