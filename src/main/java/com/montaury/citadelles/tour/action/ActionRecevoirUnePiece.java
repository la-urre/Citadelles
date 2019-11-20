package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;

public class ActionRecevoirUnePiece implements Action {
    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        associationJoueurPersonnage.joueur().ajouterPieces(1);
    }
}
