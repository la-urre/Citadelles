package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;

public class MiseEnPlace {
    private static final int NOMBRE_DE_PIECES_INITIAL_PAR_JOUEUR = 2;
    private static final int NOMBRE_DE_CARTES_INITIAL_PAR_JOUEUR = 4;

    public Joueur commencerPartie(Pioche pioche, List<Joueur> joueurs) {
        joueurs.forEach(joueur -> {
            joueur.ajouterPieces(NOMBRE_DE_PIECES_INITIAL_PAR_JOUEUR);
            joueur.ajouterCartesALaMain(pioche.tirerCartes(NOMBRE_DE_CARTES_INITIAL_PAR_JOUEUR));
        });
        return joueurs.maxBy(Joueur::age).get();
    }
}
