package com.montaury.citadelles.tour;

import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Quartier;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Option;

public class AssociationJoueurPersonnage {
    public static AssociationJoueurPersonnage associationEntre(Joueur joueur, Personnage personnage) {
        return new AssociationJoueurPersonnage(joueur, personnage);
    }

    private AssociationJoueurPersonnage(Joueur joueur, Personnage personnage) {
        this.joueur = joueur;
        this.personnage = personnage;
    }

    public Joueur joueur() {
        return joueur;
    }

    public Personnage personnage() {
        return personnage;
    }

    public boolean estPersonnage(Personnage personnage) {
        return this.personnage == personnage;
    }

    public void assassiner() {
        this.assassiné = true;
    }

    public boolean estAssassiné() {
        return assassiné;
    }

    public void voléPar(Joueur joueur) {
        voléPar = Option.of(joueur);
    }

    public Option<Joueur> voleur() {
        return voléPar;
    }

    Set<TypeAction> actionsDebutDeTour() {
        Set<TypeAction> actionsDebutDeTour = ACTION_DEBUT_TOUR;
        List<Quartier> quartiers = joueur().cité().quartiers();
        return quartiers.flatMap(quartier -> quartier.remplacerAction(actionsDebutDeTour)).orElse(actionsDebutDeTour).toSet();
    }

    Set<TypeAction> actionsOptionnelles() {
        return ACTIONS_OPTIONNELLES_DE_BASE
                .addAll(personnage.pouvoirs())
                .addAll(joueur().cité().quartiers().flatMap(Quartier::actionSupplementaire));
    }

    private final Joueur joueur;
    public final Personnage personnage;
    private boolean assassiné;
    private Option<Joueur> voléPar = Option.none();
    private static final Set<TypeAction> ACTION_DEBUT_TOUR = HashSet.of(TypeAction.PIOCHER_1_CARTE_PARMI_2, TypeAction.PRENDRE_2_PIECES);
    private static final Set<TypeAction> ACTIONS_OPTIONNELLES_DE_BASE = HashSet.of(TypeAction.BATIR_QUARTIER, TypeAction.TERMINER_TOUR);
}
