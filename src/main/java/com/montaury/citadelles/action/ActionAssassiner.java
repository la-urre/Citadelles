package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;
import com.montaury.citadelles.personnage.Personnage;

public class ActionAssassiner implements Action {

    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        Personnage personnageAAssassiner = associationJoueurPersonnage.joueur().controlleur.choisirParmi(associations.assassinables());
        associations.assassiner(personnageAAssassiner);
    }

}
