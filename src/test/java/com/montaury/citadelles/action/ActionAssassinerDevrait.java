package com.montaury.citadelles.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.FauxControlleur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.AssociationsDeTour;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static com.montaury.citadelles.tour.AssociationJoueurPersonnage.associationEntre;
import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionAssassinerDevrait {

    @Before
    public void setUp() {
        controlleur = new FauxControlleur();
        actionAssassiner = new ActionAssassiner();
    }

    @Test
    public void demander_au_joueur_quel_personnage_assassiner() {
        actionAssassiner.réaliser(associationEntre(unJoueur(controlleur), Personnage.ROI), associationsDeTour(), Pioche.vide());

        assertThat(controlleur.personnagesDisponibles).isNotNull();
    }

    @Test
    public void pouvoir_assassiner_tous_les_personnages_sauf_l_assassin() {
        actionAssassiner.réaliser(associationEntre(unJoueur(controlleur), Personnage.ROI), associationsDeTour(), Pioche.vide());

        assertThat(controlleur.personnagesDisponibles)
                .containsExactly(Personnage.VOLEUR, Personnage.MAGICIEN, Personnage.ROI, Personnage.EVEQUE, Personnage.MARCHAND, Personnage.ARCHITECTE, Personnage.CONDOTTIERE);
    }

    @Test
    public void assassiner_le_personnage_choisi() {
        controlleur.prechoisirPersonnage(Personnage.MARCHAND);
        AssociationJoueurPersonnage associationAAssassiner = associationEntre(unJoueur(), Personnage.MARCHAND);
        AssociationsDeTour associations = associationsDeTour(associationAAssassiner);

        actionAssassiner.réaliser(associationEntre(unJoueur(controlleur), Personnage.ROI), associations, Pioche.vide());

        assertThat(associationAAssassiner.estAssassiné()).isTrue();
    }

    private FauxControlleur controlleur;
    private ActionAssassiner actionAssassiner;
}
