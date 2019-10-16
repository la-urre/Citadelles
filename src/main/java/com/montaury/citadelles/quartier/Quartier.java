package com.montaury.citadelles.quartier;

import com.montaury.citadelles.Possession;
import com.montaury.citadelles.TypeQuartier;
import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.score.Score;
import io.vavr.collection.Set;
import io.vavr.control.Option;

import static com.montaury.citadelles.TypeQuartier.*;
import static com.montaury.citadelles.action.TypeAction.*;

public enum Quartier {
    MANOIR(3, NOBLE),
    CHATEAU(4, NOBLE),
    PALAIS(5, NOBLE),

    TOUR_DE_GUET(1, MILITAIRE),
    PRISON(2, MILITAIRE),
    CASERNE(3, MILITAIRE),
    FORTERESSE(5, MILITAIRE),

    TAVERNE(1, COMMERCANT),
    ECHOPPE(2, COMMERCANT),
    MARCHE(2, COMMERCANT),
    COMPTOIR(3, COMMERCANT),
    PORT(4, COMMERCANT),
    HOTEL_DE_VILLE(5, COMMERCANT),

    TEMPLE(1, RELIGIEUX),
    EGLISE(2, RELIGIEUX),
    MONASTERE(3, RELIGIEUX),
    CATHEDRALE(5, RELIGIEUX),

    COUR_DES_MIRACLES(2, MERVEILLE), // Pour le calcul du score, la Cour des Miracles est considérée comme un quartier de la couleur de votre choix
    DRACOPORT(6, MERVEILLE, Effet.bonusDeScore(2)), // Coûte 6 pièces d'or à bâtir mais vaut 8 points pour le calcul du score
    UNIVERSITE(6, MERVEILLE, Effet.bonusDeScore(2)), // Coûte 6 pièces d'or à bâtir mais vaut 8 points pour le calcul du score
    TRESOR_IMPERIAL(5, MERVEILLE, Effet.bonusDeScore(Possession::pieces)), // A la fin de la partie, marquez 1 point supp. pour chaque pièce d'or dans votre trésor
    SALLE_DES_CARTES(5, MERVEILLE, Effet.bonusDeScore(Possession::nombreDeCartesEnMain)), // A la fin de la partie, marquez 1 point supp. pour chaque carte dans votre main
    OBSERVATOIRE(4, MERVEILLE, Effet.actionRemplacante(PIOCHER_1_CARTE_PARMI_2, PIOCHER_1_CARTE_PARMI_3)), // si vous choisissez de piocher des cartes au début de votre tour, piochez-en 3 au lieu de 2. Choisissez-en une et défaussez les 2 autres
    CIMETIERE(5, MERVEILLE), // Lorsque le Condottiere détruit un quartier, vous pouvez payer 1 pièce d'or pour le prendre dans votre main. Vous ne pouvez pas le faire si vous êtes vous-même Condottiere
    FORGE(5, MERVEILLE, Effet.actionSupplementaire(PIOCHER_3_CARTES_CONTRE_2_PIECES)), // Une fois par tour, vous pouvez payer 2 pièces d'or pour piocher 3 cartes
    BIBLIOTHEQUE(6, MERVEILLE), // Si vous choisissez de piocher des cartes au début de votre tour, conservez-les toutes
    ECOLE_DE_MAGIE(6, MERVEILLE, Effet.bonusDePerception(1)), // Pour la perception des revenus, l'école de Magie est considérée comme un quartier de la couleur de votre choix. Elle vous rapporte donc si vous êtes Roi, Eveque, Marchand ou Condottiere
    LABORATOIRE(5, MERVEILLE, Effet.actionSupplementaire(TypeAction.DEFAUSSER_1_CARTE_ET_RECEVOIR_2_PIECES)), // Une fois par tour, vous pouvez défausser 1 carte et recevoir 2 pièces d'or
    GRANDE_MURAILLE(6, MERVEILLE), // Le prix à payer par le Condottiere pour détruire vos autres quartiers est augmenté de 1
    DONJON(3, MERVEILLE, Effet.indestructible()); // Le donjon ne peut pas être détruit par le Condottiere

    Quartier(int coutDeConstruction, TypeQuartier typeDeQuartier) {
        this(coutDeConstruction, typeDeQuartier, Effet.aucun());
    }

    Quartier(int coutDeConstruction, TypeQuartier typeDeQuartier, Effet effet) {
        this.coutDeConstruction = Cout.de(coutDeConstruction);
        this.typeDeQuartier = typeDeQuartier;
        this.effet = effet;
    }

    public final Cout coutDeConstruction() {
        return coutDeConstruction;
    }

    public final TypeQuartier typeDeQuartier() {
        return typeDeQuartier;
    }

    public boolean estDestructible() {
        return !effet.estIndestructible();
    }

    public Score appliquerBonusDeScore(Possession possession) {
        return effet.appliquerBonusDeScore(possession);
    }

    public Option<TypeAction> actionSupplementaire() {
        return effet.actionSupplementaire();
    }

    public Set<TypeAction> remplacerAction(Set<TypeAction> actions) {
        return actions.map(effet::remplacerAction);
    }

    public int percevoirRevenusPour(Personnage personnage) {
        return (personnage.typeQuartierAssocie().contains(typeDeQuartier) ? 1 : 0)
                + effet.percevoirRevenusPour(personnage);
    }

    private final Cout coutDeConstruction;
    private final TypeQuartier typeDeQuartier;
    private final Effet effet;
}
