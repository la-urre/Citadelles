package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

public class MiseEnPlaceDevrait {

    private MiseEnPlace miseEnPlace;

    @Before
    public void setUp() {
        joueur1 = unJoueur();
        joueur2 = new Joueur("Nom2", joueur1.age() + 1, citéVide());
        miseEnPlace = new MiseEnPlace();
    }

    @Test
    public void donner_2_pieces_a_chaque_joueur() {
        miseEnPlace.commencerPartie(Pioche.vide(), List.of(joueur1, joueur2));

        assertThat(joueur1.trésor().pieces()).isEqualTo(2);
        assertThat(joueur2.trésor().pieces()).isEqualTo(2);
    }

    @Test
    public void distribuer_4_cartes_a_chaque_joueur() {
        miseEnPlace.commencerPartie(Pioche.completeMelangee(), List.of(joueur1, joueur2));

        assertThat(joueur1.main().cartes()).hasSize(4);
        assertThat(joueur2.main().cartes()).hasSize(4);
    }

    @Test
    public void donner_la_couronne_au_joueur_le_plus_vieux() {
        Joueur joueurAyantLaCouronne = miseEnPlace.commencerPartie(Pioche.vide(), List.of(joueur1, joueur2));

        assertThat(joueurAyantLaCouronne).isEqualTo(joueur2);
    }

    @Test
    public void donner_la_couronne_au_premier_joueur_si_plusieurs_ont_le_meme_age() {
        Joueur joueur3 = new Joueur("Nom3", joueur1.age(), citéVide());
        Joueur joueurAyantLaCouronne = miseEnPlace.commencerPartie(Pioche.vide(), List.of(joueur3, joueur1));

        assertThat(joueurAyantLaCouronne).isEqualTo(joueur3);
    }

    private Joueur joueur1;
    private Joueur joueur2;
}
