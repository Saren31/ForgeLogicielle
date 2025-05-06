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

  

