package com.montaury.citadelles.joueur;

import com.montaury.citadelles.quartier.Cout;

public class Trésor {
    public static Trésor vide() {
        return new Trésor();
    }

    private Trésor() {
    }

    public void ajouter(int pieces) {
        this.pieces += pieces;
    }

    public void ajouter(Trésor trésor) {
        this.pieces += trésor.pieces;
    }

    public void payer(Cout cout) {
        this.pieces -= cout.montant();
    }

    public boolean peutPayer(Cout cout) {
        return this.pieces >= cout.montant();
    }

    public int pieces() {
        return pieces;
    }

    private int pieces;
}
