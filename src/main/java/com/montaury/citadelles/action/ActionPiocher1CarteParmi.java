package com.montaury.citadelles.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.Set;

public class ActionPiocher1CarteParmi implements Action {
    public ActionPiocher1CarteParmi(int nombreDeCartesAPiocher) {
        this.nombreDeCartesAPiocher = nombreDeCartesAPiocher;
    }

    @Override
    public boolean estRéalisablePar(Joueur joueur, TourDeJeu tourDeJeu, Pioche pioche) {
        return pioche.peutFournirCartes(nombreDeCartesAPiocher);
    }

    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Set<Carte> choixDeCartes = pioche.tirerCartes(nombreDeCartesAPiocher);
        Carte carteConservee = associationJoueurPersonnage.joueur().controlleur.choisirParmi(choixDeCartes);
        associationJoueurPersonnage.joueur().ajouterCarteALaMain(carteConservee);
        pioche.mettreDessous(choixDeCartes.remove(carteConservee).toList());
    }

    private final int nombreDeCartesAPiocher;
}
