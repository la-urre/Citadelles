package com.montaury.citadelles;

import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.joueur.Main;
import com.montaury.citadelles.joueur.Trésor;
import com.montaury.citadelles.quartier.Quartier;
import com.montaury.citadelles.quartier.QuartierDestructible;
import com.montaury.citadelles.score.Score;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

public class CitéDevrait {

    @Before
    public void setUp() {
        citésComplètes = new CitésComplètes();
        cité = new Cité(citésComplètes);
    }

    @Test
    public void scorer_zero_si_aucun_quartier() {
        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.nul());
    }

    @Test
    public void scorer_le_cout_de_construction_total_des_quartiers() {
        cité.batirQuartier(Carte.TAVERNE_1);
        cité.batirQuartier(Carte.LABORATOIRE);
        cité.batirQuartier(Carte.TOUR_DE_GUET_1);
        cité.batirQuartier(Carte.MANOIR_1);

        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(10));
    }

    @Test
    public void scorer_un_bonus_de_3_si_5_types_de_quartiers_batis() {
        cité.batirQuartier(Carte.TAVERNE_1);
        cité.batirQuartier(Carte.LABORATOIRE);
        cité.batirQuartier(Carte.TOUR_DE_GUET_1);
        cité.batirQuartier(Carte.MANOIR_1);
        cité.batirQuartier(Carte.TEMPLE_1);

        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(14));
    }

    @Test
    public void scorer_un_bonus_de_3_si_4_types_de_quartiers_representes_et_cour_des_miracles() {
        cité.batirQuartier(Carte.TAVERNE_1);
        cité.batirQuartier(Carte.LABORATOIRE);
        cité.batirQuartier(Carte.TOUR_DE_GUET_1);
        cité.batirQuartier(Carte.MANOIR_1);
        cité.batirQuartier(Carte.COUR_DES_MIRACLES);

        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(15));
    }

    @Test
    public void ne_pas_scorer_un_bonus_de_3_si_4_types_de_quartiers_batis_et_cour_des_miracles_est_l_unique_merveille() {
        cité.batirQuartier(Carte.TAVERNE_1);
        cité.batirQuartier(Carte.TOUR_DE_GUET_1);
        cité.batirQuartier(Carte.MANOIR_1);
        cité.batirQuartier(Carte.COUR_DES_MIRACLES);

        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(7));
    }

    @Test
    public void scorer_8_pour_le_dracoport() {
        cité.batirQuartier(Carte.DRACOPORT);

        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(8));
    }

    @Test
    public void scorer_8_pour_l_universite() {
        cité.batirQuartier(Carte.UNIVERSITE);

        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(8));
    }

    @Test
    public void scorer_un_bonus_de_4_si_elle_est_la_premiere_complete() {
        completerCité(cité);

        Score score = cité.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(12));
    }

    @Test
    public void scorer_un_bonus_de_2_si_elle_est_complete_mais_pas_en_premier() {
        Cité cité2 = new Cité(citésComplètes);
        completerCité(cité);
        completerCité(cité2);

        Score score = cité2.score(possessionVide());

        assertThat(score).isEqualTo(Score.de(10));
    }

    @Test
    public void scorer_un_bonus_egal_au_nombre_de_pieces_si_tresor_imperial_bati() {
        cité.batirQuartier(Carte.TRESOR_IMPERIAL);

        Score score = cité.score(new Possession(trésorAvec(2), Main.vide()));

        assertThat(score).isEqualTo(Score.de(7));
    }

    @Test
    public void scorer_un_bonus_egal_au_nombre_de_cartes_en_main_si_salle_des_cartes_bati() {
        cité.batirQuartier(Carte.SALLE_DES_CARTES);

        Score score = cité.score(new Possession(Trésor.vide(), Main.avec(Carte.CHATEAU_1, Carte.CHATEAU_2, Carte.CHATEAU_3, Carte.CHATEAU_4)));

        assertThat(score).isEqualTo(Score.de(9));
    }

    @Test
    public void compter_le_nombre_de_quartiers_d_un_meme_type() {
        cité.batirQuartier(Carte.CASERNE_1);
        cité.batirQuartier(Carte.TOUR_DE_GUET_1);
        cité.batirQuartier(Carte.FORTERESSE_1);

        int nombreDeQuartiersMilitaires = cité.nombreDeQuartiersDeType(TypeQuartier.MILITAIRE);

        assertThat(nombreDeQuartiersMilitaires).isEqualTo(3);
    }

    @Test
    public void detruire_un_quartier() {
        cité.batirQuartier(Carte.MONASTERE_1);

        cité.detruireQuartier(Carte.MONASTERE_1);

        assertThat(cité.estBati(Quartier.MONASTERE)).isFalse();
    }

    @Test
    public void ne_pas_avoir_de_quartier_destructible_si_elle_est_vide() {
        Joueur joueur = new Joueur("Toto", 12, citéVide(), new FauxControlleur());

        List<QuartierDestructible> quartiersDestructibles = cité.quartiersDestructiblesPar(joueur);

        assertThat(quartiersDestructibles).isEmpty();
    }

    @Test
    public void ne_pas_avoir_de_quartier_destructible_si_elle_est_complete() {
        Joueur joueur = unJoueur();
        completerCité(cité);

        List<QuartierDestructible> quartiersDestructibles = cité.quartiersDestructiblesPar(joueur);

        assertThat(quartiersDestructibles).isEmpty();
    }

    @Test
    public void ne_pas_avoir_de_quartier_destructible_si_elle_contient_un_quartier_dont_le_cout_de_destruction_n_est_pas_payable_par_le_joueur() {
        Joueur joueur = unJoueur();
        joueur.ajouterPieces(1);
        cité.batirQuartier(Carte.MONASTERE_1);

        List<QuartierDestructible> quartiersDestructibles = cité.quartiersDestructiblesPar(joueur);

        assertThat(quartiersDestructibles).isEmpty();
    }


    @Test
    public void avoir_un_quartier_destructible_si_elle_contient_un_quartier_dont_le_cout_de_destruction_est_payable_par_le_joueur() {
        Joueur joueur = unJoueur();
        joueur.ajouterPieces(2);
        cité.batirQuartier(Carte.MONASTERE_1);

        List<QuartierDestructible> quartiersDestructibles = cité.quartiersDestructiblesPar(joueur);

        assertThat(quartiersDestructibles).extracting("quartier").containsExactly(Carte.MONASTERE_1);
    }

    @Test
    public void ne_pas_avoir_de_quartier_destructible_si_elle_contient_un_quartier_dont_le_cout_de_destruction_n_est_pas_payable_par_le_joueur_a_cause_de_la_grande_muraille() {
        Joueur joueur = unJoueur();
        joueur.ajouterPieces(2);
        cité.batirQuartier(Carte.MONASTERE_1);
        cité.batirQuartier(Carte.GRANDE_MURAILLE);

        List<QuartierDestructible> quartiersDestructibles = cité.quartiersDestructiblesPar(joueur);

        assertThat(quartiersDestructibles).isEmpty();
    }

    @Test
    public void avoir_la_grande_muraille_comme_quartier_destructible_si_le_joueur_peut_payer_la_destruction() {
        Joueur joueur = unJoueur();
        joueur.ajouterPieces(5);
        cité.batirQuartier(Carte.GRANDE_MURAILLE);

        List<QuartierDestructible> quartiersDestructibles = cité.quartiersDestructiblesPar(joueur);

        assertThat(quartiersDestructibles).extracting("quartier").contains(Carte.GRANDE_MURAILLE);
    }

    private void completerCité(Cité cité) {
        List.range(0, 8).forEach(i -> cité.batirQuartier(Carte.TAVERNE_1));
    }

    private static Possession possessionVide() {
        return new Possession(Trésor.vide(), Main.vide());
    }

    private static Trésor trésorAvec(int pieces) {
        Trésor trésor = Trésor.vide();
        trésor.ajouter(2);
        return trésor;
    }

    private Cité cité;
    private CitésComplètes citésComplètes;
}