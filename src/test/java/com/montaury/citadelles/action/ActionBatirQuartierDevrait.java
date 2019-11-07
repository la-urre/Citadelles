package com.montaury.citadelles.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.quartier.Quartier;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import io.vavr.collection.HashSet;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionBatirQuartierDevrait {

    @Before
    public void setUp() {
        action = new ActionBatirQuartier();
        controlleur = new FauxControlleur();
        controlleur.prechoisirCarte(Carte.TEMPLE_1);
        joueur = new Joueur("Toto", 12, citéVide(), controlleur);
        association = new AssociationJoueurPersonnage(joueur, Personnage.EVEQUE);
        piocheVide = Pioche.vide();
    }

    @Test
    public void etre_executable_si_le_joueur_a_au_moins_un_quartier_constructible_en_main() {
        joueur.ajouterPieces(2);
        joueur.ajouterCartesALaMain(HashSet.of(Carte.TEMPLE_1));

        boolean executable = action.estRéalisablePar(joueur, associationsDeTour(), piocheVide);

        assertThat(executable).isTrue();
    }

    @Test
    public void ne_pas_etre_executable_si_le_joueur_n_a_pas_de_quartier_constructible_en_main() {
        joueur.ajouterCartesALaMain(HashSet.of(Carte.TEMPLE_1));

        boolean executable = action.estRéalisablePar(joueur, associationsDeTour(), piocheVide);

        assertThat(executable).isFalse();
    }

    @Test
    public void proposer_au_joueur_les_quartiers_construisibles_seulement() {
        joueur.ajouterPieces(2);
        joueur.ajouterCartesALaMain(HashSet.of(Carte.CHATEAU_1, Carte.TEMPLE_1, Carte.CASERNE_1));

        action.réaliser(association, associationsDeTour(), piocheVide);

        assertThat(controlleur.cartesDisponibles).containsExactly(Carte.TEMPLE_1);
    }

    @Test
    public void depenser_le_nombre_de_pieces_correspondant_au_cout_de_construction_du_quartier_choisi() {
        joueur.ajouterPieces(2);
        joueur.ajouterCartesALaMain(HashSet.of(Carte.CHATEAU_1, Carte.TEMPLE_1, Carte.CASERNE_1));

        action.réaliser(association, associationsDeTour(), piocheVide);

        assertThat(joueur.trésor().pieces()).isEqualTo(1);
    }

    @Test
    public void batir_le_quartier_dans_la_cité_du_joueur() {
        joueur.ajouterPieces(2);
        joueur.ajouterCartesALaMain(HashSet.of(Carte.CHATEAU_1, Carte.TEMPLE_1, Carte.CASERNE_1));

        action.réaliser(association, associationsDeTour(), piocheVide);

        assertThat(joueur.cité().estBati(Quartier.TEMPLE)).isTrue();
    }

    @Test
    public void retirer_le_quartier_de_la_main() {
        joueur.ajouterPieces(2);
        joueur.ajouterCartesALaMain(HashSet.of(Carte.CHATEAU_1, Carte.TEMPLE_1, Carte.CASERNE_1));

        action.réaliser(association, associationsDeTour(), piocheVide);

        assertThat(joueur.main().cartes()).doesNotContain(Carte.TEMPLE_1);
    }

    private FauxControlleur controlleur;
    private Joueur joueur;
    private AssociationJoueurPersonnage association;
    private Pioche piocheVide;
    private ActionBatirQuartier action;
}
