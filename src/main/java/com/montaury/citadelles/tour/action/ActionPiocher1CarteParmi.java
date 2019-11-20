package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.quartier.Quartier;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.AssociationsDeTour;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class ActionPiocher1CarteParmi implements Action {
    public ActionPiocher1CarteParmi(int nombreDeCartesAPiocher) {
        this.nombreDeCartesAPiocher = nombreDeCartesAPiocher;
    }

    @Override
    public boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return pioche.peutFournirCartes(nombreDeCartesAPiocher);
    }

    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        Set<Carte> choixDeCartes = pioche.tirerCartes(nombreDeCartesAPiocher);
        if (!associationJoueurPersonnage.joueur().cité().estBati(Quartier.BIBLIOTHEQUE)) {
            Carte carteConservee = associationJoueurPersonnage.joueur().controlleur.choisirParmi(choixDeCartes);
            pioche.mettreDessous(choixDeCartes.remove(carteConservee).toList());
            choixDeCartes = HashSet.of(carteConservee);
        }
        associationJoueurPersonnage.joueur().ajouterCartesALaMain(choixDeCartes);
    }

    private final int nombreDeCartesAPiocher;
}
