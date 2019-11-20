package com.montaury.citadelles.personnage;

import com.montaury.citadelles.quartier.TypeQuartier;
import com.montaury.citadelles.tour.action.TypeAction;
import io.vavr.collection.List;
import io.vavr.control.Option;

public enum Personnage {
    ASSASSIN(1, "Assassin", List.of(TypeAction.ASSASSINER)),
    VOLEUR(2, "Voleur", List.of(TypeAction.VOLER)),
    MAGICIEN(3, "Magicien", List.of(TypeAction.ECHANGER_MAIN_AVEC_JOUEUR, TypeAction.ECHANGER_CARTES_AVEC_PIOCHE)),
    ROI(4, "Roi", List.of(TypeAction.PERCEVOIR_REVENUS), TypeQuartier.NOBLE),
    EVEQUE(5, "Evèque", List.of(TypeAction.PERCEVOIR_REVENUS), TypeQuartier.RELIGIEUX),
    MARCHAND(6, "Marchand", List.of(TypeAction.PERCEVOIR_REVENUS, TypeAction.RECEVOIR_PIECE), TypeQuartier.COMMERCANT),
    ARCHITECTE(7, "Architecte", List.of(TypeAction.PIOCHER_2_CARTES, TypeAction.BATIR_QUARTIER, TypeAction.BATIR_QUARTIER)),
    CONDOTTIERE(8, "Condottiere", List.of(TypeAction.PERCEVOIR_REVENUS, TypeAction.DETRUIRE_QUARTIER), TypeQuartier.MILITAIRE);

    Personnage(int ordre, String nom, List<TypeAction> typeActionsSpecifiques)
    {
        this(ordre, nom, typeActionsSpecifiques, null);
    }

    Personnage(int ordre, String nom, List<TypeAction> pouvoirs, TypeQuartier typeQuartierAssocie)
    {
        this.ordre = ordre;
        this.nom = nom;
        this.pouvoirs = pouvoirs;
        this.typeQuartierAssocie = Option.of(typeQuartierAssocie);
    }

    public int getOrdre() {
        return ordre;
    }

    public String nom() {
        return nom;
    }

    public static List<Personnage> dansLOrdre() {
        return List.of(Personnage.values()).sortBy(Personnage::getOrdre);
    }

    public List<TypeAction> pouvoirs() {
        return pouvoirs;
    }

    public Option<TypeQuartier> typeQuartierAssocie() {
        return typeQuartierAssocie;
    }

    private final int ordre;
    private final String nom;
    private final List<TypeAction> pouvoirs;
    private Option<TypeQuartier> typeQuartierAssocie;
}