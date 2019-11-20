package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.FauxControlleur;
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

public class ActionPiocher1CarteParmiDevrait {

    @Before
    public void setUp() {
        controlleur = new FauxControlleur();
        joueur = unJoueur(controlleur);
        action = new ActionPiocher1CarteParmi(2);
        piocheAvec2Cartes = piocheAvec(Carte.MANOIR_1, Carte.PALAIS_2);
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
    public void proposer_au_joueur_les_2_cartes_au_dessus_de_la_pioche() {
        action.réaliser(associationEntre(joueur, Personnage.ROI), associationsDeTour(), piocheAvec2Cartes);

        assertThat(controlleur.cartesDisponibles).containsExactlyInAnyOrder(Carte.MANOIR_1, Carte.PALAIS_2);
    }

    @Test
    public void ajouter_la_carte_choisie_a_la_main_du_joueur() {
        controlleur.prechoisirCarte(Carte.PALAIS_2);

        action.réaliser(associationEntre(joueur, Personnage.ROI), associationsDeTour(), piocheAvec2Cartes);

        assertThat(joueur.main().cartes()).containsExactly(Carte.PALAIS_2);
    }

    @Test
    public void remettre_la_carte_non_choisie_sous_la_pioche() {
        controlleur.prechoisirCarte(Carte.PALAIS_2);

        action.réaliser(associationEntre(joueur, Personnage.ROI), associationsDeTour(), piocheAvec2Cartes);

        assertThat(piocheAvec2Cartes.tirerCarte()).containsExactly(Carte.MANOIR_1);
    }

    @Test
    public void conserver_toutes_les_cartes_si_le_joueur_detient_la_bibliotheque() {
        joueur.cité().batirQuartier(Carte.BIBLIOTHEQUE);

        action.réaliser(associationEntre(joueur, Personnage.ROI), associationsDeTour(), piocheAvec2Cartes);

        assertThat(controlleur.cartesDisponibles).isEmpty();
        assertThat(piocheAvec2Cartes.tirerCarte()).isEmpty();
        assertThat(joueur.main().cartes()).contains(Carte.PALAIS_2, Carte.MANOIR_1);
    }

    private FauxControlleur controlleur;
    private Joueur joueur;
    private ActionPiocher1CarteParmi action;
    private Pioche piocheAvec2Cartes;
}
