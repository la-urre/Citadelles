package com.montaury.citadelles.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.quartier.Quartier;
import io.vavr.collection.HashSet;
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
        if (!associationJoueurPersonnage.joueur().cité().estBati(Quartier.BIBLIOTHEQUE)) {
            Carte carteConservee = associationJoueurPersonnage.joueur().controlleur.choisirParmi(choixDeCartes);
            pioche.mettreDessous(choixDeCartes.remove(carteConservee).toList());
            choixDeCartes = HashSet.of(carteConservee);
        }
        associationJoueurPersonnage.joueur().ajouterCartesALaMain(choixDeCartes);
    }

    private final int nombreDeCartesAPiocher;
}
