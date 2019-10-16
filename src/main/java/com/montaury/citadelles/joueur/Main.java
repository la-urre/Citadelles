package com.montaury.citadelles.joueur;

import com.montaury.citadelles.quartier.Carte;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class Main {
    public static Main vide() {
        return new Main(HashSet.empty());
    }

    static Main avec(Set<Carte> cartes) {
        return new Main(cartes);
    }

    public static Main avec(Carte... cartes) {
        return avec(HashSet.of(cartes));
    }

    private Main(Set<Carte> cartes) {
        this.cartes = cartes;
    }

    public void ajouter(Carte carte) {
        cartes = cartes.add(carte);
    }

    public void ajouter(Set<Carte> cartes) {
        this.cartes = this.cartes.addAll(cartes);
    }

    public void retirer(Carte carteARetirer) {
        cartes = cartes.remove(carteARetirer);
    }

    public void retirer(Iterable<Carte> cartesARetirer) {
        cartes = cartes.removeAll(cartesARetirer);
    }

    public int nombreDeCartes() {
        return cartes.size();
    }

    public Set<Carte> cartes() {
        return cartes;
    }

    private Set<Carte> cartes;

    public boolean estVide() {
        return cartes.isEmpty();
    }
}
