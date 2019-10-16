package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import io.vavr.collection.List;

public class Jeu {

    private final List<Joueur> joueurs;

    public Jeu(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    TourDeJeu jouer(Joueur joueurAvecCouronne, Pioche pioche) {
        Ordonnanceur ordonnanceur = new Ordonnanceur();
        TourDeJeu tourDeJeu;
        do {
            PhaseDeSelectionDesPersonnages phaseDeSelectionDesPersonnages = new PhaseDeSelectionDesPersonnages(new PersonnageAleatoire());
            System.out.println("Nouveau tour de jeu");
            List<Joueur> joueursDansLOrdre = ordonnanceur.ordonnerJoueurs(joueurs, joueurAvecCouronne);
            tourDeJeu = phaseDeSelectionDesPersonnages.faireChoisirPersonnages(joueursDansLOrdre);

            tourDeJeu.deroulerTour(pioche);

            joueurAvecCouronne = tourDeJeu.joueurAssocieAuPersonnage(Personnage.ROI).getOrElse(joueurAvecCouronne);
        } while (!estFini());
        return tourDeJeu;
    }

    public List<Joueur> joueurs() {
        return joueurs;
    }

    public boolean estFini() {
        return joueurs().map(Joueur::cité).exists(Cité::estComplete);
    }
}
