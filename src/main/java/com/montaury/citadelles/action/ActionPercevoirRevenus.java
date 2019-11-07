package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;

public class ActionPercevoirRevenus implements Action {

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        associationJoueurPersonnage.joueur().percevoirRevenus(associationJoueurPersonnage.personnage);
    }
}
