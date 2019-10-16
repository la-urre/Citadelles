package com.montaury.citadelles.quartier;

public final class Cout {
    public static Cout nul() {
        return new Cout(0);
    }

    public static Cout de(int montant) {
        return new Cout(montant);
    }

    private Cout(int montant) {
        this.montant = montant;
    }

    public Cout moins(Cout cout) {
        return new Cout(montant - cout.montant);
    }

    public Cout plus(Cout cout) {
        return new Cout(montant + cout.montant);
    }

    public int montant() {
        return montant;
    }

    private final int montant;
}
