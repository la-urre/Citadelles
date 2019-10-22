package com.montaury.citadelles.joueur;

import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.quartier.Quartier;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.PiochePrédéfinie.piocheAvec;
import static org.assertj.core.api.Assertions.assertThat;

public class JoueurDevrait {

    @Before
    public void setUp() {
        joueur = new Joueur("Toto", 12, citéVide());
        joueur.ajouterPieces(2);
    }

    @Test
    public void pouvoir_batir_un_quartier_s_il_a_assez_de_pieces() {
        joueur.ajouterPieces(5);
        joueur.ajouterCarteALaMain(Carte.PALAIS_1);

        Set<Carte> quartierBatissables = joueur.quartierBatissablesEnMain();

        assertThat(quartierBatissables).containsExactly(Carte.PALAIS_1);
    }

    @Test
    public void ne_pas_pouvoir_batir_un_quartier_s_il_n_a_pas_assez_de_pieces() {
        joueur.ajouterPieces(2);
        joueur.ajouterCarteALaMain(Carte.PALAIS_1);

        Set<Carte> quartierBatissables = joueur.quartierBatissablesEnMain();

        assertThat(quartierBatissables).isEmpty();
    }

    @Test
    public void ne_pas_pouvoir_batir_un_quartier_s_il_est_deja_bati() {
        joueur.ajouterPieces(5);
        joueur.ajouterCartesALaMain(HashSet.of(Carte.PALAIS_1, Carte.PALAIS_2));
        joueur.batirQuartier(Carte.PALAIS_1);

        Set<Carte> quartierBatissables = joueur.quartierBatissablesEnMain();

        assertThat(quartierBatissables).isEmpty();
    }

    @Test
    public void batir_un_quartier_avec_une_carte_de_sa_main() {
        joueur.ajouterCarteALaMain(Carte.EGLISE_1);

        joueur.batirQuartier(Carte.EGLISE_1);

        assertThat(joueur.main().cartes()).isEmpty();
        assertThat(joueur.trésor().pieces()).isEqualTo(0);
        assertThat(joueur.cité().quartiers())
                .hasSize(1)
                .first()
                .isEqualTo(Quartier.EGLISE);
    }

    @Test
    public void echanger_la_main_avec_un_autre_joueur() {
        Joueur joueur2 = new Joueur("Tutu", 13, citéVide());
        joueur.ajouterCarteALaMain(Carte.CHATEAU_1);
        joueur2.ajouterCarteALaMain(Carte.CASERNE_1);
        joueur2.ajouterCarteALaMain(Carte.CIMETIERE);

        joueur.echangerMainAvec(joueur2);

        assertThat(joueur.main().cartes())
                .hasSize(2)
                .containsExactlyInAnyOrder(Carte.CASERNE_1, Carte.CIMETIERE);
        assertThat((joueur2.main().cartes()))
                .hasSize(1)
                .containsExactlyInAnyOrder(Carte.CHATEAU_1);
    }

    @Test
    public void recuperer_les_pieces_d_un_autre_joueur_en_le_volant() {
        Joueur joueur2 = new Joueur("Tutu", 13, citéVide());
        joueur2.ajouterPieces(10);

        joueur.voler(joueur2);

        assertThat(joueur.trésor().pieces()).isEqualTo(12);
        assertThat(joueur2.trésor().pieces()).isEqualTo(0);
    }

    @Test
    public void echanger_cartes_avec_la_pioche() {
        joueur.ajouterCartesALaMain(HashSet.of(Carte.CATHEDRALE_1, Carte.TEMPLE_1, Carte.CASERNE_2));
        Pioche pioche = piocheAvec(Carte.CHATEAU_1, Carte.COMPTOIR_1);

        joueur.echangerCartes(List.of(Carte.TEMPLE_1, Carte.CATHEDRALE_1), pioche);

        assertThat(joueur.main().cartes()).containsExactlyInAnyOrder(Carte.CASERNE_2, Carte.CHATEAU_1, Carte.COMPTOIR_1);
        assertThat(pioche.tirerCartes(2)).containsExactlyInAnyOrder(Carte.TEMPLE_1, Carte.CATHEDRALE_1);
    }

    private Joueur joueur;
}