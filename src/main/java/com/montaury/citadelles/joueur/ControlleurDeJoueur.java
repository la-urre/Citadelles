package com.montaury.citadelles.joueur;

import com.montaury.citadelles.Carte;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

public interface ControlleurDeJoueur {
    Personnage choisirSonPersonnage(List<Personnage> personnagesDisponibles, List<Personnage> personnagesEcartesFaceVisible);

    TypeAction choisirAction(List<TypeAction> actionsPossibles);

    Carte choisirParmi(Set<Carte> cartesDisponibles);

    Personnage choisirParmi(List<Personnage> personnagesAssassinables);

    Joueur choisirParmiJoueurs(List<Joueur> joueurs);

    Set<Carte> choisirPlusieursParmi(Set<Carte> cartes);

    QuartierDestructible choisirQuartierADetruireParmi(Map<Joueur, List<QuartierDestructible>> joueursAttaquables);

    boolean accepterCarte(Carte carte);
}
