package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionDefausser1CartePourRecevoir2PiecesDevrait {

    @Before
    public void setUp() {
        action = new ActionDefausser1CartePourRecevoir2Pieces();
        controlleur = new FauxControlleur();
        joueur = unJoueur(controlleur);
        piocheVide = Pioche.vide();
    }

    @Test
    public void etre_executable_si_la_main_du_joueur_n_est_pas_vide() {
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);

        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), piocheVide);

        assertThat(executable).isTrue();
    }

    @Test
    public void ne_pas_etre_executable_si_la_main_du_joueur_est_vide() {
        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), piocheVide);

        assertThat(executable).isFalse();
    }

    @Test
    public void demander_au_joueur_quelle_carte_defausser() {
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheVide);

        assertThat(controlleur.cartesDisponibles).containsExactly(Carte.CHATEAU_1);
    }

    @Test
    public void retirer_la_carte_choisie_par_le_joueur_de_sa_main() {
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);
        controlleur.prechoisirCarte(Carte.CHATEAU_1);

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheVide);

        assertThat(joueur.main().nombreDeCartes()).isEqualTo(0);
    }

    @Test
    public void mettre_la_carte_choisie_sous_la_pioche() {
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);
        controlleur.prechoisirCarte(Carte.CHATEAU_1);

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheVide);

        assertThat(piocheVide.tirerCarte()).contains(Carte.CHATEAU_1);
    }

    @Test
    public void ajouter_2_pieces_au_tresor_du_joueur() {
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);
        controlleur.prechoisirCarte(Carte.CHATEAU_1);

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheVide);

        assertThat(joueur.trésor().pieces()).isEqualTo(2);
    }

    private ActionDefausser1CartePourRecevoir2Pieces action;
    private FauxControlleur controlleur;
    private Joueur joueur;
    private Pioche piocheVide;
}