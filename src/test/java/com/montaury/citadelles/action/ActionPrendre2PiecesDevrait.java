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

public class ActionPrendre2PiecesDevrait {

    @Test
    public void ajouter_2_pieces_au_tresor_du_joueur() {
        Joueur joueur = new Joueur("Toto", 12, citéVide(), new FauxControlleur());

        ActionPrendre2Pieces action = new ActionPrendre2Pieces();

        action.réaliser(new AssociationJoueurPersonnage(joueur, Personnage.EVEQUE), associationsDeTour(), Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(2);
    }
}
