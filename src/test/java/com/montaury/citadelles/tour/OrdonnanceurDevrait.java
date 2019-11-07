package com.montaury.citadelles.tour;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.tour.Ordonnanceur;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

public class OrdonnanceurDevrait {

    @Before
    public void setUp() {
        joueur1 = unJoueur("Joueur1");
        joueur2 = unJoueur("Joueur2");
        joueur3 = unJoueur("Joueur3");
        joueurs = List.of(joueur1, joueur2, joueur3);
    }

    @Test
    public void garder_le_meme_ordre_si_le_joueur_ayant_la_couronne_est_en_premiere_position() {
        List<Joueur> joueursOrdonnes = new Ordonnanceur().ordonnerJoueurs(joueurs, joueur1);

        assertThat(joueursOrdonnes).isEqualTo(joueurs);
    }

    @Test
    public void rotater_les_joueurs_en_amenant_celui_ayant_la_couronne_en_premiere_position() {
        List<Joueur> joueursOrdonnes = new Ordonnanceur().ordonnerJoueurs(joueurs, joueur2);

        assertThat(joueursOrdonnes).isEqualTo(List.of(joueur2, joueur3, joueur1));
    }

    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueur3;
    private List<Joueur> joueurs;
}
