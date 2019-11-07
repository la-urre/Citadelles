package com.montaury.citadelles.action;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.AssociationsDeTour;

public enum TypeAction {
    // ACTIONS DE BASE
    PIOCHER_1_CARTE_PARMI_2(new ActionPiocher1CarteParmi(2)),
    PRENDRE_2_PIECES(new ActionPrendre2Pieces()),
    BATIR_QUARTIER(new ActionBatirQuartier()),
    TERMINER_TOUR(new ActionTerminerTour()),

    // ACTIONS DE PERSONNAGE
    ASSASSINER(new ActionAssassiner()),
    VOLER(new ActionVoler()),
    ECHANGER_MAIN_AVEC_JOUEUR(new ActionEchangerMainAvecJoueur()),
    ECHANGER_CARTES_AVEC_PIOCHE(new ActionEchangerCartesAvecPioche()),
    PERCEVOIR_REVENUS(new ActionPercevoirRevenus()),
    RECEVOIR_PIECE(new ActionRecevoirUnePiece()),
    PIOCHER_2_CARTES(new ActionPiocher2Cartes()),
    DETRUIRE_QUARTIER(new ActionDetruireQuartier()),

    // ACTIONS DE MERVEILLE
    PIOCHER_1_CARTE_PARMI_3(new ActionPiocher1CarteParmi(2)),
    PIOCHER_3_CARTES_CONTRE_2_PIECES(new ActionPiocher3CartesContre2Pieces()),
    DEFAUSSER_1_CARTE_ET_RECEVOIR_2_PIECES(new ActionDefausser1CartePourRecevoir2Pieces());

    TypeAction(Action action) {
        this.action = action;
    }

    public boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return action.estRéalisablePar(joueur, associations, pioche);
    }

    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        action.réaliser(associationJoueurPersonnage, associations, pioche);
    }

    private final Action action;
}
