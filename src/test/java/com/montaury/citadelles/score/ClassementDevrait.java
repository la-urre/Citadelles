package com.montaury.citadelles.score;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueurAyantBati;
import static com.montaury.citadelles.tour.AssociationJoueurPersonnage.associationEntre;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassementDevrait {

    @Before
    public void setUp() {
        classement = new Classement();
    }

    @Test
    public void classer_les_joueurs_du_plus_grand_nombre_de_points_au_plus_petit() {
        Joueur joueur1 = unJoueurAyantBati(Carte.TAVERNE_1);
        Joueur joueur2 = unJoueurAyantBati(Carte.EGLISE_1);
        List<AssociationJoueurPersonnage> associations = List.of(
                associationEntre(joueur1, Personnage.CONDOTTIERE),
                associationEntre(joueur2, Personnage.ROI)
        );

        List<Joueur> joueursClasses = classement.classer(associations);

        assertThat(joueursClasses)
                .hasSize(2)
                .containsExactly(joueur2, joueur1);
    }

    @Test
    public void designer_vainqueur_le_joueur_n_etant_pas_assassine_en_cas_d_egalite() {
        Joueur joueur1 = unJoueurAyantBati(Carte.TAVERNE_1);
        Joueur joueur2 = unJoueurAyantBati(Carte.TEMPLE_1);
        AssociationJoueurPersonnage associationJoueur1Roi = associationEntre(joueur1, Personnage.ROI);
        AssociationJoueurPersonnage associationJoueur2Condottiere = associationEntre(joueur2, Personnage.CONDOTTIERE);
        associationJoueur2Condottiere.assassiner();

        List<Joueur> joueursClasses = classement.classer(List.of(associationJoueur1Roi, associationJoueur2Condottiere));

        assertThat(joueursClasses)
                .hasSize(2)
                .containsExactly(joueur1, joueur2);
    }

    @Test
    public void designer_vainqueur_le_joueur_ayant_le_numero_d_ordre_le_plus_eleve_du_dernier_tour_en_cas_d_egalite() {
        Joueur joueur1 = unJoueurAyantBati(Carte.TAVERNE_1);
        Joueur joueur2 = unJoueurAyantBati(Carte.TEMPLE_1);
        List<AssociationJoueurPersonnage> associations = List.of(
                associationEntre(joueur1, Personnage.ROI),
                associationEntre(joueur2, Personnage.CONDOTTIERE)
        );

        List<Joueur> joueursClasses = classement.classer(associations);

        assertThat(joueursClasses)
                .hasSize(2)
                .containsExactly(joueur2, joueur1);
    }

    private Classement classement;
}