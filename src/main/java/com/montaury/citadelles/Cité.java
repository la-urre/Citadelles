package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.*;
import com.montaury.citadelles.score.Score;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import static com.montaury.citadelles.quartier.Quartier.COUR_DES_MIRACLES;
import static com.montaury.citadelles.quartier.Quartier.GRANDE_MURAILLE;

public class Cité {
    private static final int NOMBRE_QUARTIERS = 8;
    private static final Score SCORE_BONUS_TOUTES_LES_COULEURS = Score.de(3);
    private final CitésComplètes citésComplètes;
    private List<Carte> cartesQuartiers = List.empty();

    public Cité(CitésComplètes citésComplètes) {
        this.citésComplètes = citésComplètes;
    }

    public void batirQuartier(Carte quartier) {
        cartesQuartiers = cartesQuartiers.append(quartier);
        if (estComplete()) {
            citésComplètes.marquer(this);
        }
    }

    public boolean estComplete() {
        return cartesQuartiers.size() >= NOMBRE_QUARTIERS;
    }

    public Score score(Possession possession) {
        Score score = Score.de(coutDeConstructionTotalDesQuartiers())
                .plus(bonusDesQuartiers(possession));
        if (estEligibleAuBonusDeCouleurs()) {
            score = score.plus(SCORE_BONUS_TOUTES_LES_COULEURS);
        }
        if (citésComplètes.estPremiere(this)) {
            score = score.plus(Score.de(2));
        }
        if (estComplete()) {
            score = score.plus(Score.de(2));
        }
        return score;
    }

    private Score bonusDesQuartiers(Possession possession) {
        return quartiers().map(quartier -> quartier.appliquerBonusDeScore(possession)).fold(Score.nul(), Score::plus);
    }

    private boolean estEligibleAuBonusDeCouleurs() {
        return typesDesQuartiers().size() == TypeQuartier.values().length
                || typesDesQuartiers().size() == TypeQuartier.values().length - 1 && estBati(COUR_DES_MIRACLES) && nombreDeQuartiersDeType(TypeQuartier.MERVEILLE) > 1;
    }

    private Cout coutDeConstructionTotalDesQuartiers() {
        return quartiers().map(Quartier::coutDeConstruction).fold(Cout.nul(), Cout::plus);
    }

    int nombreDeQuartiersDeType(TypeQuartier typeQuartier) {
        return cartesQuartiers.map(Carte::quartier).filter(quartier -> quartier.estDeType(typeQuartier)).size();
    }

    private Set<TypeQuartier> typesDesQuartiers() {
        return quartiers().map(Quartier::typeDeQuartier).toSet();
    }

    public boolean estBati(Quartier quartier) {
        return quartiers().contains(quartier);
    }

    public void detruireQuartier(Carte carte) {
        cartesQuartiers = cartesQuartiers.remove(carte);
    }

    public List<QuartierDestructible> quartiersDestructiblesPar(Joueur joueur) {
        return estComplete() ?
                List.empty() :
                cartesQuartiers
                        .filter(carte -> carte.quartier().estDestructible())
                        .filter(carte -> joueur.peutPayer(coutDeDestruction(carte)))
                        .map(carte -> new QuartierDestructible(carte, coutDeDestruction(carte)));
    }

    private Cout coutDeDestruction(Carte carte) {
        return carte.quartier().coutDeConstruction().moins(estBati(GRANDE_MURAILLE) && carte.quartier() != GRANDE_MURAILLE ? Cout.nul() : Cout.de(1));
    }

    public List<Quartier> quartiers() {
        return cartesQuartiers.map(Carte::quartier);
    }

    public int revenusPour(Personnage personnage) {
        return quartiers()
                .map(quartier -> quartier.percevoirRevenusPour(personnage))
                .sum().intValue();
    }
}
