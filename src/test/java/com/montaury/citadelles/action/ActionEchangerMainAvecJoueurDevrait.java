package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.TourDeJeu;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import io.vavr.collection.HashSet;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionEchangerMainAvecJoueurDevrait {
    @Test
    public void demander_au_joueur_le_joueur_avec_qui_echanger() {
        FauxControlleur controlleur = new FauxControlleur();
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleur);
        Joueur joueur2 = new Joueur("Titi", 12, citéVide(), new FauxControlleur());
        controlleur.prechoisirJoueur(joueur2);

        ActionEchangerMainAvecJoueur action = new ActionEchangerMainAvecJoueur();

        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.MAGICIEN, joueur1);
        tourDeJeu.associer(Personnage.ROI, joueur2);
        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.ROI), tourDeJeu, Pioche.vide());

        assertThat(controlleur.joueursPourEchange).containsExactly(joueur2);
    }
    @Test
    public void echanger_les_mains_de_2_joueurs() {
        FauxControlleur controlleur = new FauxControlleur();
        Joueur joueur1 = new Joueur("Toto", 12, citéVide(), controlleur);
        Joueur joueur2 = new Joueur("Titi", 12, citéVide(), new FauxControlleur());
        controlleur.prechoisirJoueur(joueur2);

        joueur1.ajouterCartesALaMain(HashSet.of(Carte.CATHEDRALE_1, Carte.CASERNE_1));
        joueur2.ajouterCartesALaMain(HashSet.of(Carte.TEMPLE_1, Carte.COMPTOIR_1));

        ActionEchangerMainAvecJoueur action = new ActionEchangerMainAvecJoueur();

        action.réaliser(new AssociationJoueurPersonnage(joueur1, Personnage.ROI), new TourDeJeu(), Pioche.vide());

        assertThat(joueur1.main().cartes()).containsExactlyInAnyOrder(Carte.TEMPLE_1, Carte.COMPTOIR_1);
        assertThat(joueur2.main().cartes()).containsExactlyInAnyOrder(Carte.CATHEDRALE_1, Carte.CASERNE_1);
    }
}