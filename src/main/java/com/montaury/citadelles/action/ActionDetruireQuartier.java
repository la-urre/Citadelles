package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.collection.List;
import io.vavr.collection.Map;

public class ActionDetruireQuartier implements Action {

    @Override
    public boolean estRéalisablePar(Joueur joueur, TourDeJeu tourDeJeu, Pioche pioche) {
        return tourDeJeu.quartiersDestructiblesPar(joueur).exists(joueurListTuple2 -> !joueurListTuple2._2().isEmpty());
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Map<Joueur, List<QuartierDestructible>> quartiersDestructiblesParJoueur = tourDeJeu.quartiersDestructiblesPar(associationJoueurPersonnage.joueur());
        QuartierDestructible quartierADetruire = associationJoueurPersonnage.joueur().controlleur.choisirQuartierADetruireParmi(quartiersDestructiblesParJoueur);
        Joueur joueurAAttaquer = quartiersDestructiblesParJoueur.find(joueurListTuple2 -> joueurListTuple2._2.contains(quartierADetruire)).get()._1;
        associationJoueurPersonnage.joueur().payer(quartierADetruire.coutDeDestruction());
        Carte quartierDetruit = quartierADetruire.quartier();
        joueurAAttaquer.cité().detruireQuartier(quartierDetruit);
        tourDeJeu.joueurPrenantQuartierDetruit(quartierDetruit)
                .onEmpty(() -> pioche.mettreDessous(quartierDetruit));
    }
}
