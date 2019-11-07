package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;
import com.montaury.citadelles.personnage.Personnage;

public class ActionVoler implements Action {
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        Personnage personnage = associationJoueurPersonnage.joueur().controlleur.choisirParmi(associations.volables());
        associations.voler(associationJoueurPersonnage.joueur(), personnage);
    }
}
