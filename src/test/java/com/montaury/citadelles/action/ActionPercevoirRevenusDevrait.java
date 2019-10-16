package com.montaury.citadelles.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionPercevoirRevenusDevrait {
    @Test
    public void percevoir_les_revenus_des_quartiers_nobles_pour_le_roi() {
        Cité cité = citéVide();
        cité.batirQuartier(Carte.MANOIR_1);
        cité.batirQuartier(Carte.PALAIS_1);
        Joueur joueur = new Joueur("Toto", 12, cité, new FauxControlleur());
        ActionPercevoirRevenus action = new ActionPercevoirRevenus();
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ROI, joueur);

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), tourDeJeu, Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(2);
    }

    @Test
    public void percevoir_les_revenus_de_l_ecole_de_magie_quand_personnage_correspond() {
        Cité cité = citéVide();
        cité.batirQuartier(Carte.ECOLE_DE_MAGIE);
        Joueur joueur = new Joueur("Toto", 12, cité, new FauxControlleur());
        ActionPercevoirRevenus action = new ActionPercevoirRevenus();
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ROI, joueur);

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), tourDeJeu, Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(1);
    }

    @Test
    public void ne_pas_percevoir_les_revenus_de_l_ecole_de_magie_quand_personnage_ne_correspond_pas() {
        Cité cité = citéVide();
        cité.batirQuartier(Carte.ECOLE_DE_MAGIE);
        Joueur joueur = new Joueur("Toto", 12, cité, new FauxControlleur());
        ActionPercevoirRevenus action = new ActionPercevoirRevenus();
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.VOLEUR, joueur);

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.VOLEUR), tourDeJeu, Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(0);
    }

}