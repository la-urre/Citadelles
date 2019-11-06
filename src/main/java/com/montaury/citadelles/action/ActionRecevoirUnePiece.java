package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.TourDeJeu;

public class ActionRecevoirUnePiece implements Action {
    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        associationJoueurPersonnage.joueur().ajouterPieces(1);
    }
}
