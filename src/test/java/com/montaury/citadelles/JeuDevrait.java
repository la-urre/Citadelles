package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unAutreJoueur;
import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

public class JeuDevrait {

    private Joueur joueur;
    private Jeu jeu;

    @Before
    public void setUp() {
        joueur = unJoueur();
        jeu = new Jeu(List.of(joueur, unAutreJoueur()));
    }

    @Test
    public void se_terminer_si_un_joueur_a_exactement_huit_quartiers() {
        batirQuartiers(8);

        boolean fini = jeu.estFini();

        assertThat(fini).isTrue();
    }

    @Test
    public void se_terminer_si_un_joueur_a_plus_de_huit_quartiers() {
        batirQuartiers(9);

        boolean fini = jeu.estFini();

        assertThat(fini).isTrue();
    }

    @Test
    public void continuer_tant_qu_un_joueur_n_a_pas_huit_quartiers() {
        batirQuartiers(7);

        boolean fini = jeu.estFini();

        assertThat(fini).isFalse();
    }

    private void batirQuartiers(int nombre) {
        List.range(0, nombre).forEach(i -> joueur.cité().batirQuartier(Carte.TEMPLE_1));
    }

}