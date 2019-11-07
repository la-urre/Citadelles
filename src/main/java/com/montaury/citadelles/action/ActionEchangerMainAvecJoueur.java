package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;
import com.montaury.citadelles.joueur.Joueur;

public class ActionEchangerMainAvecJoueur implements Action {
    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        Joueur joueurPourEchange = associationJoueurPersonnage.joueur().controlleur.choisirParmiJoueurs(associations.joueursSans(associationJoueurPersonnage.joueur()));
        associationJoueurPersonnage.joueur().echangerMainAvec(joueurPourEchange);
    }
}
