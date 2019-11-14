package com.montaury.citadelles.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueur;
import static com.montaury.citadelles.tour.AssociationJoueurPersonnage.associationEntre;
import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionPrendre2PiecesDevrait {

    @Before
    public void setUp() {
        joueur = unJoueur();
        action = new ActionPrendre2Pieces();
    }

    @Test
    public void ajouter_2_pieces_au_tresor_du_joueur() {
        action.réaliser(associationEntre(joueur, Personnage.EVEQUE), associationsDeTour(), Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(2);
    }

    private Joueur joueur;
    private ActionPrendre2Pieces action;
}
