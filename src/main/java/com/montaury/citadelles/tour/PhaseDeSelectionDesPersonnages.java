package com.montaury.citadelles.tour;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.personnage.PersonnageAleatoire;
import io.vavr.collection.List;

public class PhaseDeSelectionDesPersonnages {
    private final PersonnageAleatoire personnageAleatoire;

    public PhaseDeSelectionDesPersonnages(PersonnageAleatoire personnageAleatoire) {
        this.personnageAleatoire = personnageAleatoire;
    }

    public TourDeJeu faireChoisirPersonnages(List<Joueur> joueursDansLOrdre) {
        List<Personnage> personnagesDisponibles = Personnage.dansLOrdre();

        Personnage personnageEcarteFaceCachee = ecarterPersonnageFaceCachee(personnagesDisponibles);
        personnagesDisponibles = personnagesDisponibles.remove(personnageEcarteFaceCachee);

        List<Personnage> personnagesEcartesFaceVisible = ecarterPersonnagesFaceVisible(joueursDansLOrdre.size(), personnagesDisponibles);
        personnagesDisponibles = personnagesDisponibles.removeAll(personnagesEcartesFaceVisible);

        TourDeJeu tourDeJeu = new TourDeJeu();
        for (Joueur joueur : joueursDansLOrdre) {
            System.out.println(joueur.nom() + " doit choisir un personnage");
            personnagesDisponibles = personnagesDisponibles.size() == 1 && joueursDansLOrdre.size() == 7 ? personnagesDisponibles.append(personnageEcarteFaceCachee) : personnagesDisponibles;
            Personnage personnageChoisi = joueur.controlleur.choisirSonPersonnage(personnagesDisponibles, personnagesEcartesFaceVisible);
            personnagesDisponibles = personnagesDisponibles.remove(personnageChoisi);
            tourDeJeu.associer(personnageChoisi, joueur);
        }
        return tourDeJeu;
    }

    private Personnage ecarterPersonnageFaceCachee(List<Personnage> personnagesDisponibles) {
        return ecarterPersonnages(personnagesDisponibles, 1).head();
    }

    private List<Personnage> ecarterPersonnagesFaceVisible(int nombreDeJoueurs, List<Personnage> personnagesDisponibles) {
        return ecarterPersonnages(personnagesDisponibles.remove(Personnage.ROI), 7 - nombreDeJoueurs - 1);
    }

    private List<Personnage> ecarterPersonnages(List<Personnage> personnagesDisponibles, int nombreDePersonnagesAEcarter) {
        return List.range(0, nombreDePersonnagesAEcarter)
                .map(i -> personnageAleatoire.parmi(personnagesDisponibles));
    }

}
