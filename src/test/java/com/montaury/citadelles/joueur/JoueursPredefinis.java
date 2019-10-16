package com.montaury.citadelles.joueur;

import com.montaury.citadelles.faux.FauxControlleur;

import static com.montaury.citadelles.CitésPredefinies.citéVide;

public class JoueursPredefinis {
    public static Joueur unJoueur() {
        return unJoueur("nom");
    }

    public static Joueur unJoueur(String nom) {
        return new Joueur(nom, 20, citéVide(), new FauxControlleur());
    }

    public static Joueur unJoueur(String nom, ControlleurDeJoueur controlleur) {
        return new Joueur(nom, 20, citéVide(), controlleur);
    }

    public static Joueur unAutreJoueur() {
        return new Joueur("Nom2", 20, citéVide(), new FauxControlleur());
    }

    public static Joueur unJoueur(ControlleurDeJoueur controlleur) {
        return new Joueur("Nom", 20, citéVide(), controlleur);
    }
}
