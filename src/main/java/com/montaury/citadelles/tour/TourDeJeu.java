package com.montaury.citadelles.tour;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.PersonnageAleatoire;
import io.vavr.collection.List;

import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;

public class TourDeJeu {

    public List<AssociationJoueurPersonnage> jouer(List<Joueur> joueurs, Joueur joueurAvecCouronne, Pioche pioche) {
        List<AssociationJoueurPersonnage> associations = faireChoisirPersonnages(joueurs, joueurAvecCouronne);
        associationsDeTour(associations).faireJouerPersonnagesDansLOrdre(pioche);
        return associations;
    }

    private List<AssociationJoueurPersonnage> faireChoisirPersonnages(List<Joueur> joueurs, Joueur joueurAvecCouronne) {
        List<Joueur> joueursDansLOrdre = ordonnanceur.ordonnerJoueurs(joueurs, joueurAvecCouronne);
        return phaseDeSelectionDesPersonnages.faireChoisirPersonnages(joueursDansLOrdre);
    }

    private final Ordonnanceur ordonnanceur = new Ordonnanceur();
    private final PhaseDeSelectionDesPersonnages phaseDeSelectionDesPersonnages = new PhaseDeSelectionDesPersonnages(new PersonnageAleatoire());

}
