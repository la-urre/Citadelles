package com.montaury.citadelles.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.tour.AssociationsDeTour;
import io.vavr.collection.HashSet;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static com.montaury.citadelles.tour.AssociationJoueurPersonnage.associationEntre;
import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionEchangerMainAvecJoueurDevrait {

    private ActionEchangerMainAvecJoueur action;

    @Before
    public void setUp() {
        action = new ActionEchangerMainAvecJoueur();
    }

    @Test
    public void demander_au_joueur_le_joueur_avec_qui_echanger() {
        FauxControlleur controlleur = new FauxControlleur();
        Joueur joueur1 = unJoueur(controlleur);
        Joueur joueur2 = unJoueur();
        controlleur.prechoisirJoueur(joueur2);
        AssociationsDeTour associations = associationsDeTour(
                associationEntre(joueur1, Personnage.MAGICIEN),
                associationEntre(joueur2, Personnage.ROI)
        );

        action.réaliser(associationEntre(joueur1, Personnage.ROI), associations, Pioche.vide());

        assertThat(controlleur.joueursPourEchange).containsExactly(joueur2);
    }
    @Test
    public void echanger_les_mains_de_2_joueurs() {
        FauxControlleur controlleur = new FauxControlleur();
        Joueur joueur1 = unJoueur(controlleur);
        Joueur joueur2 = unJoueur();
        controlleur.prechoisirJoueur(joueur2);

        joueur1.ajouterCartesALaMain(HashSet.of(Carte.CATHEDRALE_1, Carte.CASERNE_1));
        joueur2.ajouterCartesALaMain(HashSet.of(Carte.TEMPLE_1, Carte.COMPTOIR_1));

        action.réaliser(associationEntre(joueur1, Personnage.ROI), associationsDeTour(), Pioche.vide());

        assertThat(joueur1.main().cartes()).containsExactlyInAnyOrder(Carte.TEMPLE_1, Carte.COMPTOIR_1);
        assertThat(joueur2.main().cartes()).containsExactlyInAnyOrder(Carte.CATHEDRALE_1, Carte.CASERNE_1);
    }
}