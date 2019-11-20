package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.collection.List;
import io.vavr.collection.Map;

public class ActionDetruireQuartier implements Action {

    @Override
    public boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return associations.quartiersDestructiblesPar(joueur).exists(joueurListTuple2 -> !joueurListTuple2._2().isEmpty());
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        Map<Joueur, List<QuartierDestructible>> quartiersDestructiblesParJoueur = associations.quartiersDestructiblesPar(associationJoueurPersonnage.joueur());
        QuartierDestructible quartierADetruire = associationJoueurPersonnage.joueur().controlleur.choisirQuartierADetruireParmi(quartiersDestructiblesParJoueur);
        Joueur joueurAAttaquer = quartiersDestructiblesParJoueur.find(joueurListTuple2 -> joueurListTuple2._2.contains(quartierADetruire)).get()._1;
        associationJoueurPersonnage.joueur().payer(quartierADetruire.coutDeDestruction());
        Carte quartierDetruit = quartierADetruire.quartier();
        joueurAAttaquer.cité().detruireQuartier(quartierDetruit);
        associations.joueurPrenantQuartierDetruit(quartierDetruit)
                .onEmpty(() -> pioche.mettreDessous(quartierDetruit));
    }
}
