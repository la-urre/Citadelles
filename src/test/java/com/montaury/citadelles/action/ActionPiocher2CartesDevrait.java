package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.PiochePrédéfinie.piocheAvec;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionPiocher2CartesDevrait {

    @Before
    public void setUp() {
        joueur = new Joueur("Toto", 12, citéVide(), new FauxControlleur());
        action = new ActionPiocher2Cartes();
        piocheAvec2Cartes = piocheAvec(Carte.CHATEAU_1, Carte.CASERNE_1);
    }

    @Test
    public void etre_executable_si_la_pioche_contient_assez_de_cartes() {
        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), piocheAvec2Cartes);

        assertThat(executable).isTrue();
    }

    @Test
    public void ne_pas_etre_executable_si_la_pioche_ne_contient_pas_assez_de_cartes() {
        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), piocheAvec(Carte.CATHEDRALE_1));

        assertThat(executable).isFalse();
    }

    @Test
    public void donner_2_cartes_au_joueur() {
        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheAvec2Cartes);

        assertThat(joueur.main().cartes()).containsExactlyInAnyOrder(Carte.CHATEAU_1, Carte.CASERNE_1);
    }

    @Test
    public void retirer_2_cartes_de_la_pioche() {
        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheAvec2Cartes);

        assertThat(piocheAvec2Cartes.tirerCarte()).isEmpty();
    }

    private Joueur joueur;
    private ActionPiocher2Cartes action;
    private Pioche piocheAvec2Cartes;
}