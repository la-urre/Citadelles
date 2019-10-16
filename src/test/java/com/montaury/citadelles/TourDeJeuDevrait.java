package com.montaury.citadelles;

import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import io.vavr.collection.List;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéAvec;
import static com.montaury.citadelles.PiochePrédéfinie.piocheAvec;
import static com.montaury.citadelles.joueur.JoueursPredefinis.unAutreJoueur;
import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

public class TourDeJeuDevrait {

    @Test
    public void appeler_les_joueurs_dans_l_ordre_des_personnages() {
        FauxControlleur controlleur = new FauxControlleur();
        controlleur.prechoisirActions(List.of(TypeAction.PRENDRE_2_PIECES, TypeAction.TERMINER_TOUR, TypeAction.PRENDRE_2_PIECES, TypeAction.TERMINER_TOUR));
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, unJoueur(controlleur));
        tourDeJeu.associer(Personnage.ASSASSIN, unJoueur(controlleur));

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(controlleur.actionsPossibles.get(1))
                .containsExactlyInAnyOrder(TypeAction.ASSASSINER, TypeAction.TERMINER_TOUR);

        assertThat(controlleur.actionsPossibles.get(3))
                .containsExactlyInAnyOrder(TypeAction.PERCEVOIR_REVENUS, TypeAction.TERMINER_TOUR);
    }

    @Test
    public void commencer_par_demander_au_joueur_l_action_obligatoire_de_debut_de_tour() {
        FauxControlleur fauxControlleur = new FauxControlleur();
        fauxControlleur.prechoisirAction(TypeAction.PIOCHER_1_CARTE_PARMI_2);
        fauxControlleur.prechoisirCarte(Carte.CHATEAU_1);
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.VOLEUR, unJoueur(fauxControlleur));

        tourDeJeu.deroulerTour(piocheAvec(Carte.CHATEAU_1, Carte.TOUR_DE_GUET_3));

        assertThat(fauxControlleur.actionsPossibles.get(0))
                .containsExactlyInAnyOrder(TypeAction.PIOCHER_1_CARTE_PARMI_2, TypeAction.PRENDRE_2_PIECES);
    }

    @Test
    public void ne_pas_proposer_de_piocher_1_carte_parmi_2_si_la_pioche_est_vide() {
        FauxControlleur fauxControlleur = new FauxControlleur();
        fauxControlleur.prechoisirActions(List.of(TypeAction.PRENDRE_2_PIECES, TypeAction.TERMINER_TOUR));
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.VOLEUR, unJoueur(fauxControlleur));

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(fauxControlleur.actionsPossibles.get(0)).doesNotContain(TypeAction.PIOCHER_1_CARTE_PARMI_2);
    }

    @Test
    public void permettre_a_un_joueur_de_terminer_son_tour() {
        FauxControlleur fauxControlleur = new FauxControlleur();
        fauxControlleur.prechoisirActions(List.of(TypeAction.PRENDRE_2_PIECES, TypeAction.TERMINER_TOUR));
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.VOLEUR, unJoueur(fauxControlleur));

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(fauxControlleur.actionsPossibles.size()).isEqualTo(2);
    }

    @Test
    public void demander_au_joueur_le_pouvoir_a_utiliser() {
        FauxControlleur fauxControlleur = new FauxControlleur();
        fauxControlleur.prechoisirActions(List.of(TypeAction.PRENDRE_2_PIECES));
        Joueur joueur = unJoueur(fauxControlleur);
        joueur.ajouterPieces(1);
        joueur.ajouterCarteALaMain(Carte.TOUR_DE_GUET_1);
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.VOLEUR, joueur);

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(fauxControlleur.actionsPossibles.get(1)).containsExactlyInAnyOrder(TypeAction.BATIR_QUARTIER, TypeAction.TERMINER_TOUR, TypeAction.VOLER);
    }

    @Test
    public void ne_plus_proposer_une_action_deja_choisie() {
        FauxControlleur fauxControlleur = new FauxControlleur();
        fauxControlleur.prechoisirActions(List.of(TypeAction.PRENDRE_2_PIECES, TypeAction.PERCEVOIR_REVENUS));

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ROI, unJoueur(fauxControlleur));

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(fauxControlleur.actionsPossibles.get(2)).doesNotContain(TypeAction.PERCEVOIR_REVENUS);
    }

    @Test
    public void ne_plus_proposer_d_action_obligatoire_au_2_eme_tour() {
        FauxControlleur fauxControlleur = new FauxControlleur();
        fauxControlleur.prechoisirAction(TypeAction.PRENDRE_2_PIECES);

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ROI, unJoueur(fauxControlleur));

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(fauxControlleur.actionsPossibles.get(1)).doesNotContain(TypeAction.PRENDRE_2_PIECES, TypeAction.PIOCHER_1_CARTE_PARMI_2);
    }

    @Test
    public void ne_pas_faire_jouer_l_assassine() {
        FauxControlleur controlleurJoueur2 = new FauxControlleur();

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ASSASSIN, unJoueur());
        tourDeJeu.associer(Personnage.EVEQUE, unJoueur("Joueur2", controlleurJoueur2));
        tourDeJeu.assassiner(Personnage.EVEQUE);

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(controlleurJoueur2.actionsPossibles).isEmpty();
    }

    @Test
    public void proposer_une_action_supplementaire_pour_un_joueur_ayant_le_quartier_forge() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        Joueur joueur1 = new Joueur("Toto", 12, citéAvec(Carte.FORGE), controlleurJoueur1);
        joueur1.ajouterPieces(2);

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.MAGICIEN, joueur1);
        tourDeJeu.associer(Personnage.EVEQUE, unJoueur());

        tourDeJeu.deroulerTour(piocheAvec(Carte.CATHEDRALE_1, Carte.CIMETIERE, Carte.CHATEAU_1));

        assertThat(controlleurJoueur1.actionsPossibles.get(1)).contains(TypeAction.PIOCHER_3_CARTES_CONTRE_2_PIECES);
    }

    @Test
    public void proposer_une_action_supplementaire_pour_un_joueur_ayant_le_quartier_laboratoire() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        Joueur joueur1 = new Joueur("Toto", 12, citéAvec(Carte.LABORATOIRE), controlleurJoueur1);
        joueur1.ajouterCarteALaMain(Carte.CHATEAU_1);

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.MAGICIEN, joueur1);
        tourDeJeu.associer(Personnage.EVEQUE, unJoueur());

        tourDeJeu.deroulerTour(Pioche.vide());

        assertThat(controlleurJoueur1.actionsPossibles.get(1)).contains(TypeAction.DEFAUSSER_1_CARTE_ET_RECEVOIR_2_PIECES);
    }

    @Test
    public void remplacer_l_action_piocher_1_carte_parmi_2_par_piocher_1_carte_parmi_3_pour_un_joueur_ayant_le_quartier_observatoire() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        Joueur joueur1 = new Joueur("Toto", 12, citéAvec(Carte.OBSERVATOIRE), controlleurJoueur1);

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.MAGICIEN, joueur1);
        tourDeJeu.associer(Personnage.EVEQUE, unJoueur());

        tourDeJeu.deroulerTour(piocheAvec(Carte.CATHEDRALE_1, Carte.CHATEAU_1, Carte.FORGE));

        assertThat(controlleurJoueur1.actionsPossibles.get(0)).contains(TypeAction.PIOCHER_1_CARTE_PARMI_3);
        assertThat(controlleurJoueur1.actionsPossibles.get(0)).doesNotContain(TypeAction.PIOCHER_1_CARTE_PARMI_2);
    }

    @Test
    public void transferer_les_pieces_d_un_joueur_vole_lorsque_son_tour_commence() {
        Joueur joueur1 = unJoueur();
        Joueur joueur2 = unAutreJoueur();
        joueur2.ajouterPieces(10);
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.VOLEUR, joueur1);
        tourDeJeu.associer(Personnage.EVEQUE, joueur2);
        tourDeJeu.voler(joueur1, Personnage.EVEQUE);

        tourDeJeu.deroulerTour(piocheAvec(Carte.CATHEDRALE_1, Carte.CHATEAU_1, Carte.FORGE));

        assertThat(joueur1.trésor().pieces()).isEqualTo(10);
        assertThat(joueur2.trésor().pieces()).isEqualTo(0);
    }

    @Test
    public void permettre_de_voler_tous_les_personnages_sauf_assassin_voleur_et_assassine() {
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ASSASSIN, unJoueur("Joueur1"));
        tourDeJeu.associer(Personnage.VOLEUR, unJoueur("Joueur2"));
        tourDeJeu.associer(Personnage.MAGICIEN, unJoueur("Joueur3"));
        tourDeJeu.associer(Personnage.ARCHITECTE, unJoueur("Joueur4"));
        tourDeJeu.assassiner(Personnage.MAGICIEN);

        List<Personnage> volables = tourDeJeu.volables();

        assertThat(volables)
                .containsExactly(Personnage.ROI, Personnage.EVEQUE, Personnage.MARCHAND, Personnage.ARCHITECTE, Personnage.CONDOTTIERE);
    }

}