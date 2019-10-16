package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Quartier;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéAvec;
import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionDetruireQuartierDevrait {

    @Before
    public void setUp() {
        action = new ActionDetruireQuartier();
    }

    @Test
    public void ne_pas_etre_executable_s_il_n_y_a_aucun_quartier_destructible_par_le_joueur() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        Joueur joueur2 = new Joueur("Tata", 12, citéVide(), new FauxControlleur());
        Joueur joueur3 = new Joueur("Titi", 12, citéVide(), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.MAGICIEN, joueur2);
        tourDeJeu.associer(Personnage.ARCHITECTE, joueur3);
        boolean executable = action.estRéalisablePar(joueur1, tourDeJeu, Pioche.vide());

        assertThat(executable).isFalse();
    }

    @Test
    public void etre_executable_s_il_y_a_au_moins_un_quartier_destructible_par_le_joueur() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        joueur1.ajouterPieces(3);
        Joueur joueur2 = new Joueur("Tata", 12, citéVide(), new FauxControlleur());
        Joueur joueur3 = new Joueur("Titi", 12, citéAvec(Carte.CHATEAU_1), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.MAGICIEN, joueur2);
        tourDeJeu.associer(Personnage.ARCHITECTE, joueur3);
        boolean executable = action.estRéalisablePar(joueur1, tourDeJeu, Pioche.vide());

        assertThat(executable).isTrue();
    }

    @Test
    public void demander_au_joueur_de_choisir_parmi_les_joueurs_ayant_des_quartiers_destructibles_par_lui() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.TAVERNE_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        Joueur joueur2 = new Joueur("Tata", 12, citéVide(), new FauxControlleur());
        Joueur joueur3 = new Joueur("Titi", 12, citéAvec(Carte.TAVERNE_1), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.MAGICIEN, joueur2);
        tourDeJeu.associer(Personnage.ARCHITECTE, joueur3);
        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(controlleurJoueur1.quartiersDestructibles.get(joueur1)).contains(List.empty());
        assertThat(controlleurJoueur1.quartiersDestructibles.get(joueur2)).contains(List.empty());
        assertThat(controlleurJoueur1.quartiersDestructibles.get(joueur3).get()).extracting("quartier").contains(Carte.TAVERNE_1);
    }

    @Test
    public void ne_pas_inclure_l_eveque_dans_la_liste_des_cibles_s_il_est_vivant() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.TAVERNE_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        Joueur joueur2 = new Joueur("Tata", 12, citéVide(), new FauxControlleur());
        Joueur joueur3 = new Joueur("Titi", 12, citéAvec(Carte.TAVERNE_1), new FauxControlleur());
        Joueur joueur4 = new Joueur("Tutu", 12, citéAvec(Carte.TAVERNE_2), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.MAGICIEN, joueur2);
        tourDeJeu.associer(Personnage.ARCHITECTE, joueur3);
        tourDeJeu.associer(Personnage.EVEQUE, joueur4);
        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(controlleurJoueur1.quartiersDestructibles.get(joueur4)).isEmpty();
    }

    @Test
    public void inclure_l_eveque_dans_la_liste_des_cibles_s_il_est_assassine() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.TAVERNE_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        Joueur joueur2 = new Joueur("Tata", 12, citéVide(), new FauxControlleur());
        Joueur joueur3 = new Joueur("Titi", 12, citéVide(), new FauxControlleur());
        Joueur joueur4 = new Joueur("Tutu", 12, citéAvec(Carte.TAVERNE_1), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);
        tourDeJeu.associer(Personnage.ARCHITECTE, joueur3);
        tourDeJeu.associer(Personnage.EVEQUE, joueur4);
        tourDeJeu.assassiner(Personnage.EVEQUE);

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        Seq<Carte> quartiersDestructibles = controlleurJoueur1.quartiersDestructibles.flatMap(Tuple2::_2).map(QuartierDestructible::quartier);
        assertThat(quartiersDestructibles).contains(Carte.TAVERNE_1);
    }

    @Test
    public void detruire_le_quartier_choisi() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.CHATEAU_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        joueur1.ajouterPieces(3);
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.CHATEAU_1), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(joueur2.cité().estBati(Quartier.CHATEAU)).isFalse();
    }

    @Test
    public void retirer_le_cout_de_construction_moins_1_au_tresor_du_joueur() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.CHATEAU_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        joueur1.ajouterPieces(3);
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.CHATEAU_1), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(joueur1.trésor().pieces()).isEqualTo(0);
    }

    @Test
    public void remettre_le_quartier_detruit_sous_la_pioche_si_personne_ne_le_recupere() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.CHATEAU_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        joueur1.ajouterPieces(3);
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.CHATEAU_1), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);

        Pioche pioche = Pioche.vide();
        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, pioche);

        assertThat(pioche.tirerCarte()).containsExactly(Carte.CHATEAU_1);
    }

    @Test
    public void proposer_le_quartier_detruit_au_joueur_ayant_le_cimetiere_s_il_peut_payer_1_piece() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.CHATEAU_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        joueur1.ajouterPieces(3);
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.CHATEAU_1, Carte.CASERNE_1), new FauxControlleur());
        FauxControlleur controlleurJoueur3 = new FauxControlleur();
        Joueur joueur3 = new Joueur("Titi", 12, citéAvec(Carte.CIMETIERE), controlleurJoueur3);
        joueur3.ajouterPieces(1);

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);
        tourDeJeu.associer(Personnage.MARCHAND, joueur3);

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(controlleurJoueur3.carteProposee).contains(Carte.CHATEAU_1);
    }

    @Test
    public void ne_pas_proposer_le_quartier_detruit_au_joueur_ayant_le_cimetiere_s_il_ne_peut_pas_payer_1_piece() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.CHATEAU_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        joueur1.ajouterPieces(3);
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.CHATEAU_1, Carte.CASERNE_1), new FauxControlleur());
        FauxControlleur controlleurJoueur3 = new FauxControlleur();
        Joueur joueur3 = new Joueur("Titi", 12, citéAvec(Carte.CIMETIERE), controlleurJoueur3);

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);
        tourDeJeu.associer(Personnage.MARCHAND, joueur3);

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(controlleurJoueur3.carteProposee).isEmpty();
    }

    @Test
    public void ne_pas_proposer_le_quartier_detruit_au_condottiere_meme_s_il_a_le_cimetiere_et_peut_payer() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.CHATEAU_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéAvec(Carte.CIMETIERE), controlleurJoueur1);
        joueur1.ajouterPieces(4);
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.CHATEAU_1, Carte.CASERNE_1), new FauxControlleur());

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(controlleurJoueur1.carteProposee).isEmpty();
    }

    @Test
    public void ajouter_le_quartier_detruit_a_la_main_du_joueur_ayant_le_cimetiere_s_il_accepte() {
        FauxControlleur controlleurJoueur1 = new FauxControlleur();
        controlleurJoueur1.prechoisirCarte(Carte.CHATEAU_1);
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleurJoueur1);
        joueur1.ajouterPieces(3);
        Joueur joueur2 = new Joueur("Tata", 12, citéAvec(Carte.CHATEAU_1, Carte.CASERNE_1), new FauxControlleur());
        FauxControlleur controlleurJoueur3 = new FauxControlleur();
        controlleurJoueur3.preAccepterCarte();
        Joueur joueur3 = new Joueur("Titi", 12, citéAvec(Carte.CIMETIERE), controlleurJoueur3);
        joueur3.ajouterPieces(1);

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.CONDOTTIERE, joueur1);
        tourDeJeu.associer(Personnage.ASSASSIN, joueur2);
        tourDeJeu.associer(Personnage.MARCHAND, joueur3);

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.CONDOTTIERE), tourDeJeu, Pioche.vide());

        assertThat(joueur3.trésor().pieces()).isEqualTo(0);
        assertThat(joueur3.main().cartes()).contains(Carte.CHATEAU_1);
    }

    private ActionDetruireQuartier action;
}