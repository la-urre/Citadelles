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

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.PiochePrédéfinie.piocheAvec;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionPiocher3CartesContre2PiecesDevrait {

    @Before
    public void setUp() {
        joueur = new Joueur("Toto", 12, citéVide(), new FauxControlleur());
        association = new AssociationJoueurPersonnage(joueur, Personnage.EVEQUE);
        pioche = piocheAvec(Carte.CHATEAU_1, Carte.CATHEDRALE_1, Carte.CASERNE_1);
        action = new ActionPiocher3CartesContre2Pieces();
        tourDeJeu = new TourDeJeu();
    }

    @Test
    public void etre_executable_si_la_pioche_contient_assez_de_cartes_et_le_joueur_a_assez_de_pieces() {
        joueur.ajouterPieces(2);
        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), pioche);

        assertThat(executable).isTrue();
    }

    @Test
    public void ne_pas_etre_executable_si_la_pioche_ne_contient_pas_assez_de_cartes() {
        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), piocheAvec(Carte.CATHEDRALE_1, Carte.CIMETIERE));

        assertThat(executable).isFalse();
    }

    @Test
    public void ne_pas_etre_executable_si_le_joueur_n_a_pas_assez_de_pieces() {
        joueur.ajouterPieces(1);
        boolean executable = action.estRéalisablePar(joueur, new TourDeJeu(), pioche);

        assertThat(executable).isFalse();
    }

    @Test
    public void tirer_3_cartes_dans_la_pioche() {
        action.réaliser(association, tourDeJeu, pioche);

        assertThat(pioche.tirerCarte()).isEmpty();
    }

    @Test
    public void mettre_3_cartes_dans_la_main_du_joueur() {
        action.réaliser(association, tourDeJeu, pioche);

        assertThat(joueur.main().nombreDeCartes()).isEqualTo(3);
    }

    @Test
    public void enlever_2_pieces_du_tresor_du_joueur() {
        joueur.ajouterPieces(2);

        action.réaliser(association, tourDeJeu, pioche);

        assertThat(joueur.trésor().pieces()).isEqualTo(0);
    }

    private Joueur joueur;
    private AssociationJoueurPersonnage association;
    private Pioche pioche;
    private ActionPiocher3CartesContre2Pieces action;
    private TourDeJeu tourDeJeu;
}