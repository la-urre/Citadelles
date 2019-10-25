package com.montaury.citadelles.joueur;

import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

import java.util.function.Function;

public class ControlleurOrdinateur implements ControlleurDeJoueur {
    private int aleatoireJusque(int maxExclus) {
        return (int) Math.floor(Math.random() * maxExclus);
    }

    @Override
    public Personnage choisirSonPersonnage(List<Personnage> personnagesDisponibles, List<Personnage> personnagesEcartesFaceVisible) {
        return aleatoireParmi(personnagesDisponibles);
    }

    @Override
    public TypeAction choisirAction(List<TypeAction> actionsPossibles) {
        return aleatoireParmi(actionsPossibles);
    }

    @Override
    public Carte choisirParmi(Set<Carte> cartesDisponibles) {
        return cartesDisponibles.head();
    }

    @Override
    public Personnage choisirParmi(List<Personnage> personnagesAssassinables) {
        return aleatoireParmi(personnagesAssassinables);
    }

    @Override
    public Joueur choisirParmiJoueurs(List<Joueur> joueurs) {
        return aleatoireParmi(joueurs);
    }

    @Override
    public Set<Carte> choisirPlusieursParmi(Set<Carte> cartes) {
        return HashSet.of(cartes.head());
    }

    @Override
    public QuartierDestructible choisirQuartierADetruireParmi(Map<Joueur, List<QuartierDestructible>> joueursAttaquables) {
        return joueursAttaquables.values().filter(list->!list.isEmpty()).flatMap(Function.identity()).head();
    }

    @Override
    public boolean accepterCarte(Carte carte) {
        return false;
    }

    private <T> T aleatoireParmi(List<? extends T> list) {
        return list.get(aleatoireJusque(list.size()));
    }
}
