package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.score.Classement;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.TourDeJeu;
import io.vavr.collection.List;
import io.vavr.control.Option;

public class Jeu {

    public Jeu(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    void jouer() {
        Pioche pioche = Pioche.completeMelangee();
        Joueur joueurAvecCouronne = new MiseEnPlace().commencerPartie(pioche, joueurs);

        List<AssociationJoueurPersonnage> associationsDuTour;
        do {
            associationsDuTour = new TourDeJeu().jouer(joueurs, joueurAvecCouronne, pioche);
            joueurAvecCouronne = joueurAyantRoiParmi(associationsDuTour).getOrElse(joueurAvecCouronne);
        } while (!estFini());

        System.out.println("Classement: " + classement.classer(associationsDuTour));
    }

    private Option<Joueur> joueurAyantRoiParmi(List<AssociationJoueurPersonnage> associations) {
        return associations.find(a -> a.personnage == Personnage.ROI).map(AssociationJoueurPersonnage::joueur);
    }

    boolean estFini() {
        return joueurs.map(Joueur::cité).exists(Cité::estComplete);
    }

    private final List<Joueur> joueurs;
    private final Classement classement = new Classement();

}
