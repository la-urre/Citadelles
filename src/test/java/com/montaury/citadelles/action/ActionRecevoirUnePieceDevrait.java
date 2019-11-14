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

public class ActionRecevoirUnePieceDevrait {

    @Before
    public void setUp() {
        joueur = unJoueur();
        action = new ActionRecevoirUnePiece();
    }

    @Test
    public void donner_une_piece_au_joueur() {
        action.réaliser(associationEntre(joueur, Personnage.VOLEUR), associationsDeTour(), Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(1);
    }

    private Joueur joueur;
    private ActionRecevoirUnePiece action;
}