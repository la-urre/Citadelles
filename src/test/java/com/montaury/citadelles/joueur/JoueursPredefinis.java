package com.montaury.citadelles.joueur;

import com.montaury.citadelles.quartier.Carte;

import static com.montaury.citadelles.CitésPredefinies.citéAvec;
import static com.montaury.citadelles.CitésPredefinies.citéVide;

public class JoueursPredefinis {
    public static Joueur unJoueur() {
        return unJoueurAvec(Trésor.vide());
    }

    public static Joueur unJoueurAvecAge(int age) {
        return new Joueur("Nom", age, citéVide(), Trésor.vide(), new FauxControlleur());
    }
    public static Joueur unJoueurAvec(Trésor trésor) {
        return unJoueur("nom", trésor);
    }

    public static Joueur unJoueurAyantBati(Carte... cartes) {
        return new Joueur("nom", 20, citéAvec(cartes), new FauxControlleur());
    }

    public static Joueur unJoueur(String nom, Trésor trésor) {
        return new Joueur(nom, 20, citéVide(), trésor, new FauxControlleur());
    }

    public static Joueur unJoueur(String nom, ControlleurDeJoueur controlleur) {
        return new Joueur(nom, 20, citéVide(), controlleur);
    }

    public static Joueur unJoueur(ControlleurDeJoueur controlleur) {
        return unJoueur("Nom", controlleur);
    }
}
