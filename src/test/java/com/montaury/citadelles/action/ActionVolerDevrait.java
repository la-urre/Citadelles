package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionVolerDevrait {

    @Before
    public void setUp() {
        controlleur = new FauxControlleur();
        joueur = new Joueur("Toto", 12, citéVide(), controlleur);
        actionVoler = new ActionVoler();
        association = new AssociationJoueurPersonnage(joueur, Personnage.VOLEUR);
    }

    @Test
    public void demander_au_joueur_quel_personnage_voler() {
        actionVoler.réaliser(association, new TourDeJeu(), Pioche.vide());

        assertThat(controlleur.personnagesDisponibles).isNotNull();
    }

    @Test
    public void pouvoir_voler_tous_les_personnages_sauf_l_assassin_le_voleur_et_l_assassine() {
        Joueur autreJoueur = new Joueur("Tutu", 15, citéVide(), new FauxControlleur());
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.ARCHITECTE, autreJoueur);
        tourDeJeu.assassiner(Personnage.ARCHITECTE);

        actionVoler.réaliser(association, tourDeJeu, Pioche.vide());

        assertThat(controlleur.personnagesDisponibles).containsExactly(Personnage.MAGICIEN, Personnage.ROI, Personnage.EVEQUE, Personnage.MARCHAND, Personnage.CONDOTTIERE);
    }

    @Test
    public void voler_le_personnage_choisi() {
        Joueur autreJoueur = new Joueur("Tutu", 15, citéVide(), new FauxControlleur());
        controlleur.prechoisirPersonnage(Personnage.MARCHAND);
        TourDeJeu tourDeJeu = new TourDeJeu();
        tourDeJeu.associer(Personnage.MARCHAND, autreJoueur);

        actionVoler.réaliser(association, tourDeJeu, Pioche.vide());

        AssociationJoueurPersonnage associationAuMarchand = tourDeJeu.associationAuPersonnage(Personnage.MARCHAND).get();
        assertThat(associationAuMarchand.voleur()).isEqualTo(Option.of(joueur));
    }

    private FauxControlleur controlleur;
    private Joueur joueur;
    private AssociationJoueurPersonnage association;
    private ActionVoler actionVoler;
}
