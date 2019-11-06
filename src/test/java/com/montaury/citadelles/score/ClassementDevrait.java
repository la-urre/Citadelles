package com.montaury.citadelles.score;

import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.tour.TourDeJeu;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéAvec;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassementDevrait {

    @Before
    public void setUp() {
        classement = new Classement();
    }

    @Test
    public void classer_les_joueurs_du_plus_grand_nombre_de_points_au_plus_petit() {
        Joueur joueur1 = new Joueur("Toto", 12, citéAvec(Carte.TAVERNE_1));
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.EGLISE_1));
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ROI, joueur2);

        List<Joueur> joueursClasses = classement.classer(tourDeJeu);

        assertThat(joueursClasses)
                .hasSize(2)
                .containsExactly(joueur2, joueur1);
    }

    @Test
    public void designer_vainqueur_le_joueur_n_etant_pas_assassine_en_cas_d_egalite() {
        Joueur joueur1 = new Joueur("Toto", 12, citéAvec(Carte.TAVERNE_1));
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.TEMPLE_1));
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ROI, joueur1);
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur2);
        tourDeJeu.assassiner(Personnage.CONDOTTIERE);

        List<Joueur> joueursClasses = classement.classer(tourDeJeu);

        assertThat(joueursClasses)
                .hasSize(2)
                .containsExactly(joueur1, joueur2);
    }

    @Test
    public void designer_vainqueur_le_joueur_ayant_le_numero_d_ordre_le_plus_eleve_du_dernier_tour_en_cas_d_egalite() {
        Joueur joueur1 = new Joueur("Toto", 12, citéAvec(Carte.TAVERNE_1));
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.TEMPLE_1));
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ROI, joueur1);
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur2);

        List<Joueur> joueursClasses = classement.classer(tourDeJeu);

        assertThat(joueursClasses)
                .hasSize(2)
                .containsExactly(joueur2, joueur1);
    }

    private Classement classement;
}