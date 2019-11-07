package com.montaury.citadelles.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.faux.FauxControlleur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import org.junit.Test;

import static com.montaury.citadelles.CitésPredefinies.citéVide;
import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionRecevoirUnePieceDevrait {
    @Test
    public void donner_une_piece_au_joueur() {
        Joueur joueur = new Joueur("Toto", 12, citéVide(), new FauxControlleur());
        ActionRecevoirUnePiece action = new ActionRecevoirUnePiece();

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.VOLEUR), associationsDeTour(), Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(1);
    }

}