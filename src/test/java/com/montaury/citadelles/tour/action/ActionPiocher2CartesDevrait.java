package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.PiochePrédéfinie.piocheAvec;
import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static com.montaury.citadelles.tour.AssociationJoueurPersonnage.associationEntre;
import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionPiocher2CartesDevrait {

    @Before
    public void setUp() {
        joueur = unJoueur();
        action = new ActionPiocher2Cartes();
        piocheAvec2Cartes = piocheAvec(Carte.CHATEAU_1, Carte.CASERNE_1);
    }

    @Test
    public void etre_executable_si_la_pioche_contient_assez_de_cartes() {
        boolean executable = action.estRéalisablePar(joueur, associationsDeTour(), piocheAvec2Cartes);

        assertThat(executable).isTrue();
    }

    @Test
    public void ne_pas_etre_executable_si_la_pioche_ne_contient_pas_assez_de_cartes() {
        boolean executable = action.estRéalisablePar(joueur, associationsDeTour(), piocheAvec(Carte.CATHEDRALE_1));

        assertThat(executable).isFalse();
    }

    @Test
    public void donner_2_cartes_au_joueur() {
        action.réaliser(associationEntre(joueur, Personnage.ROI), associationsDeTour(), piocheAvec2Cartes);

        assertThat(joueur.main().cartes()).containsExactlyInAnyOrder(Carte.CHATEAU_1, Carte.CASERNE_1);
    }

    @Test
    public void retirer_2_cartes_de_la_pioche() {
        action.réaliser(associationEntre(joueur, Personnage.ROI), associationsDeTour(), piocheAvec2Cartes);

        assertThat(piocheAvec2Cartes.tirerCarte()).isEmpty();
    }

    private Joueur joueur;
    private ActionPiocher2Cartes action;
    private Pioche piocheAvec2Cartes;
}