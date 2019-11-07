package com.montaury.citadelles.tour;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.quartier.Cout;
import com.montaury.citadelles.quartier.Quartier;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

public class AssociationsDeTour {
    public static AssociationsDeTour associationsDeTour(List<AssociationJoueurPersonnage> associations) {
        return new AssociationsDeTour(associations);
    }

    public static AssociationsDeTour associationsDeTour(AssociationJoueurPersonnage... associations) {
        return associationsDeTour(List.of(associations));
    }

    public AssociationsDeTour(List<AssociationJoueurPersonnage> associations) {
        this.associations = associations;
    }

    public List<Joueur> joueursSans(Joueur joueur) {
        return associations.map(AssociationJoueurPersonnage::joueur).remove(joueur);
    }

    public List<Personnage> assassinables() {
        return Personnage.dansLOrdre().remove(Personnage.ASSASSIN);
    }

    public void assassiner(Personnage personnage) {
        associationAuPersonnage(personnage).peek(AssociationJoueurPersonnage::assassiner);
    }

    public Option<AssociationJoueurPersonnage> associationAuPersonnage(Personnage personnage) {
        return associations.find(a -> a.personnage == personnage);
    }

    public void voler(Joueur voleur, Personnage personnage) {
        associationAuPersonnage(personnage).peek(association -> association.voléPar(voleur));
    }

    public List<Personnage> volables() {
        return Personnage.dansLOrdre()
                .remove(Personnage.ASSASSIN)
                .remove(Personnage.VOLEUR)
                .removeAll(assassiné());
    }

    private Option<Personnage> assassiné() {
        return associations.find(AssociationJoueurPersonnage::estAssassiné).map(AssociationJoueurPersonnage::personnage);
    }

    public Map<Joueur, List<QuartierDestructible>> quartiersDestructiblesPar(Joueur joueur) {
        return associations
                .filter(associationJoueurPersonnage -> !associationJoueurPersonnage.estPersonnage(Personnage.EVEQUE) || associationJoueurPersonnage.estAssassiné())
                .map(AssociationJoueurPersonnage::joueur)
                .toMap(j -> Tuple.of(j, j.cité().quartiersDestructiblesPar(joueur)));
    }

    public Option<Joueur> joueurPrenantQuartierDetruit(Carte quartierDetruit) {
        return associationAyant(Quartier.CIMETIERE)
                .filter(association -> !association.estPersonnage(Personnage.CONDOTTIERE))
                .map(AssociationJoueurPersonnage::joueur)
                .filter(j -> j.peutPayer(COUT_DE_RECUPERATION_DE_QUARTIER_DETRUIT))
                .filter(j -> j.controlleur.accepterCarte(quartierDetruit))
                .peek(j -> j.ajouterCarteALaMain(quartierDetruit))
                .peek(j -> j.payer(COUT_DE_RECUPERATION_DE_QUARTIER_DETRUIT));
    }

    private Option<AssociationJoueurPersonnage> associationAyant(Quartier quartier) {
        return associations
                .find(associationJoueurPersonnage -> associationJoueurPersonnage.joueur().cité().estBati(quartier));
    }

    private static final Cout COUT_DE_RECUPERATION_DE_QUARTIER_DETRUIT = Cout.de(1);

    final List<AssociationJoueurPersonnage> associations;
}
