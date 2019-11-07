package com.montaury.citadelles.tour;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.PersonnageAleatoire;
import io.vavr.collection.List;

import static java.util.function.Predicate.not;

public class TourDeJeu {

    public List<AssociationJoueurPersonnage> jouer(List<Joueur> joueurs, Joueur joueurAvecCouronne, Pioche pioche) {
        List<AssociationJoueurPersonnage> associations = faireChoisirPersonnages(joueurs, joueurAvecCouronne);
        AssociationsDeTour associationsDeTour = new AssociationsDeTour(associations);
        faireJouerPersonnagesDansLOrdre(associationsDeTour, pioche);
        return associations;
    }

    void faireJouerPersonnagesDansLOrdre(AssociationsDeTour associationsDeTour, Pioche pioche) {
        associationsDeTour.associations
                .filter(not(AssociationJoueurPersonnage::estAssassiné))
                .sortBy(associationJoueurPersonnage -> associationJoueurPersonnage.personnage.getOrdre())
                .map(TourDuJoueur::new)
                .forEach(tourDuJoueur -> tourDuJoueur.deroulerTour(associationsDeTour, pioche));
    }

    private List<AssociationJoueurPersonnage> faireChoisirPersonnages(List<Joueur> joueurs, Joueur joueurAvecCouronne) {
        List<Joueur> joueursDansLOrdre = ordonnanceur.ordonnerJoueurs(joueurs, joueurAvecCouronne);
        return phaseDeSelectionDesPersonnages.faireChoisirPersonnages(joueursDansLOrdre);
    }

    private final Ordonnanceur ordonnanceur = new Ordonnanceur();
    private final PhaseDeSelectionDesPersonnages phaseDeSelectionDesPersonnages = new PhaseDeSelectionDesPersonnages(new PersonnageAleatoire());

}
