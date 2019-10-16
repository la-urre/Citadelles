package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PreparateurDePartieDevrait {

    @Test
    public void ne_pas_pouvoir_commencer_si_aucun_joueur() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();

        boolean peutCommencer = preparateurDePartie.peutCommencer();

        assertThat(peutCommencer).isFalse();
    }

    @Test
    public void ne_pas_pouvoir_commencer_si_un_seul_joueur() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();
        preparateurDePartie.ajouterJoueur("Toto");

        boolean peutCommencer = preparateurDePartie.peutCommencer();

        assertThat(peutCommencer).isFalse();
    }

    @Test
    public void pouvoir_commencer_si_deux_joueurs() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();
        preparateurDePartie.ajouterJoueur("Toto");
        preparateurDePartie.ajouterJoueur("Tata");

        boolean peutCommencer = preparateurDePartie.peutCommencer();

        assertThat(peutCommencer).isTrue();
    }

    @Test
    public void pouvoir_ajouter_un_joueur_si_nombre_maximum_pas_atteint() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();
        for (int i = 0; i < 7; i++) {
            preparateurDePartie.ajouterJoueur("Toto " + i);
        }

        boolean peutAjouter = preparateurDePartie.peutAjouterJoueur();

        assertThat(peutAjouter).isTrue();
    }

    @Test
    public void ne_pas_pouvoir_ajouter_un_joueur_si_nombre_maximum_atteint() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();
        for (int i = 0; i < 8; i++) {
            preparateurDePartie.ajouterJoueur("Toto " + i);
        }

        boolean peutAjouter = preparateurDePartie.peutAjouterJoueur();

        assertThat(peutAjouter).isFalse();
    }

    @Test
    public void dire_si_un_joueur_a_ete_ajoute() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();
        preparateurDePartie.ajouterJoueur("Toto");

        boolean existe = preparateurDePartie.joueurExiste("Toto");

        assertThat(existe).isTrue();
    }

    @Test
    public void dire_si_un_joueur_n_a_pas_ete_ajoute() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();

        boolean existe = preparateurDePartie.joueurExiste("Toto");

        assertThat(existe).isFalse();
    }

    @Test
    public void preparer_un_jeu_avec_deux_joueurs() {
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();
        preparateurDePartie.ajouterJoueur("Toto");
        preparateurDePartie.ajouterJoueur("Tata");
        Jeu jeu = preparateurDePartie.preparer();


        List<Joueur> joueurs = jeu.joueurs();
        assertThat(joueurs.map(Joueur::nom))
                .hasSize(2)
                .containsExactlyInAnyOrder("Toto", "Tata");
    }

}