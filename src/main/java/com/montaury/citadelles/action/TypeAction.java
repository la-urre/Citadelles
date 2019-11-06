package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.TourDeJeu;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.Pioche;

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

    public boolean estRéalisablePar(Joueur joueur, TourDeJeu tourDeJeu, Pioche pioche) {
        return action.estRéalisablePar(joueur, tourDeJeu, pioche);
    }

    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        action.réaliser(associationJoueurPersonnage, tourDeJeu, pioche);
    }

    private final Action action;
}
