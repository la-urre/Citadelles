package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.Cout;

public class ActionPiocher3CartesContre2Pieces implements Action {

    @Override
    public boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return pioche.peutFournirCartes(3) && joueur.peutPayer(COUT_POUR_PIOCHER);
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        associationJoueurPersonnage.joueur().ajouterCartesALaMain(pioche.tirerCartes(3));
        associationJoueurPersonnage.joueur().payer(COUT_POUR_PIOCHER);
    }

    private static final Cout COUT_POUR_PIOCHER = Cout.de(2);
}
