package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import io.vavr.collection.HashSet;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.PiochePrédéfinie.piocheAvec;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionEchangerCartesAvecPiocheDevrait {

    @Before
    public void setUp() {
        controlleur = new FauxControlleur();
        joueur = new Joueur("Toto", 12, citéVide(), controlleur);
        action = new ActionEchangerCartesAvecPioche();
        piocheAvec3Cartes = piocheAvec(Carte.CASERNE_1, Carte.PALAIS_2, Carte.MANOIR_1);
    }

    @Test
    public void etre_executable_si_la_main_du_joueur_n_est_pas_vide() {
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);

        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), piocheAvec3Cartes);

        assertThat(executable).isTrue();
    }

    @Test
    public void ne_pas_etre_executable_si_la_main_du_joueur_est_vide() {
        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), piocheAvec3Cartes);

        assertThat(executable).isFalse();
    }

    @Test
    public void ne_pas_etre_executable_si_la_pioche_est_vide() {
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);

        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), Pioche.vide());

        assertThat(executable).isFalse();
    }

    @Test
    public void demander_au_joueur_quelles_cartes_echanger() {
        controlleur.prechoisirCartes(HashSet.of(Carte.CATHEDRALE_1));

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheAvec3Cartes);

        assertThat(controlleur.cartesAEchanger).containsExactly();
    }

    @Test
    public void echanger_un_choix_de_cartes_avec_la_pioche() {
        joueur.ajouterCartesALaMain(HashSet.of(Carte.CATHEDRALE_1, Carte.CHATEAU_1));
        controlleur.prechoisirCartes(HashSet.of(Carte.CATHEDRALE_1));

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.ROI), new TourDeJeu(), piocheAvec3Cartes);

        assertThat(joueur.main().cartes()).containsExactlyInAnyOrder(Carte.CHATEAU_1, Carte.CASERNE_1);
        assertThat(piocheAvec3Cartes.tirerCartes(3)).containsExactlyInAnyOrder(Carte.PALAIS_2, Carte.MANOIR_1, Carte.CATHEDRALE_1);
    }

    private FauxControlleur controlleur;
    private Joueur joueur;
    private ActionEchangerCartesAvecPioche action;
    private Pioche piocheAvec3Cartes;
}