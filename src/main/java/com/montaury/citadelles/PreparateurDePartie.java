package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;

public class PreparateurDePartie {
    private List<Joueur> joueurs = List.empty();
    public static final int MINIMUM_DE_JOUEURS = 2;
    public static final int MAXIMUM_DE_JOUEURS = 8;

    public void ajouterJoueur(Joueur joueur) {
        joueurs = joueurs.append(joueur);
    }

    public Jeu preparer() {
        return new Jeu(joueurs);
    }
}
