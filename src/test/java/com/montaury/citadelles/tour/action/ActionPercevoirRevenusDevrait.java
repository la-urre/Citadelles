package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.AssociationsDeTour;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadelles.joueur.JoueursPredefinis.unJoueurAyantBati;
import static com.montaury.citadelles.tour.AssociationJoueurPersonnage.associationEntre;
import static com.montaury.citadelles.tour.AssociationsDeTour.associationsDeTour;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionPercevoirRevenusDevrait {

    @Before
    public void setUp() {
        action = new ActionPercevoirRevenus();
    }

    @Test
    public void percevoir_les_revenus_des_quartiers_nobles_pour_le_roi() {
        Joueur joueur = unJoueurAyantBati(Carte.MANOIR_1, Carte.PALAIS_1);
        AssociationsDeTour associations = associationsDeTour(
                associationEntre(joueur, Personnage.ROI)
        );

        action.réaliser(associationEntre(joueur, Personnage.ROI), associations, Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(2);
    }

    @Test
    public void percevoir_les_revenus_de_l_ecole_de_magie_quand_personnage_correspond() {
        Joueur joueur = unJoueurAyantBati(Carte.ECOLE_DE_MAGIE);
        AssociationsDeTour associations = associationsDeTour(
                associationEntre(joueur, Personnage.ROI)
        );

        action.réaliser(associationEntre(joueur, Personnage.ROI), associations, Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(1);
    }

    @Test
    public void ne_pas_percevoir_les_revenus_de_l_ecole_de_magie_quand_personnage_ne_correspond_pas() {
        Joueur joueur = unJoueurAyantBati(Carte.ECOLE_DE_MAGIE);
        AssociationJoueurPersonnage association = associationEntre(joueur, Personnage.VOLEUR);
        AssociationsDeTour associations = associationsDeTour(
                association
        );

        action.réaliser(association, associations, Pioche.vide());

        assertThat(joueur.trésor().pieces()).isEqualTo(0);
    }

    private ActionPercevoirRevenus action;
}