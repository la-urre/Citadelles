package com.montaury.citadelles.joueur;

import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.Possession;
import com.montaury.citadelles.Cité;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.quartier.Cout;
import com.montaury.citadelles.score.Score;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public class Joueur {
    private final String nom;
    private final int age;
    private final Cité cité;
    public final ControlleurDeJoueur controlleur;
    private Trésor trésor = Trésor.vide();
    private Main main = Main.vide();

    public Joueur(String nom, int age, Cité cité) {
        this(nom, age, cité, new ControlleurHumain());
    }

    public Joueur(String nom, int age, Cité cité, ControlleurDeJoueur controlleur) {
        this.nom = nom;
        this.age = age;
        this.cité = cité;
        this.controlleur = controlleur;
    }

    public String nom() {
        return nom;
    }

    public int age() {
        return age;
    }

    public Cité cité() {
        return cité;
    }

    public void ajouterPieces(int pieces) {
        trésor.ajouter(pieces);
    }

    public void ajouterCartesALaMain(Set<Carte> cartes) {
        main.ajouter(cartes);
    }

    public void ajouterCarteALaMain(Carte carte) {
        main.ajouter(carte);
    }

    public Trésor trésor() {
        return trésor;
    }

    public boolean peutPayer(Cout cout) {
        return trésor.peutPayer(cout);
    }

    public boolean peutBatirQuartier(Carte carte) {
        return trésor.peutPayer(carte.quartier().coutDeConstruction()) && !cité.estBati(carte.quartier());
    }

    public Set<Carte> quartierBatissablesEnMain() {
        return main.cartes().filter(this::peutBatirQuartier);
    }

    public void batirQuartier(Carte carte) {
        if (!peutBatirQuartier(carte)) {
            return;
        }
        main.retirer(carte);
        cité.batirQuartier(carte);
        trésor.payer(carte.quartier().coutDeConstruction());
    }

    public Score score() {
        return cité.score(new Possession(trésor, main));
    }

    public void echangerMainAvec(Joueur autreJoueur) {
        Main mainEchange = main;
        main = autreJoueur.main;
        autreJoueur.main = mainEchange;
    }

    public Main main() {
        return main;
    }

    public void voler(Joueur autreJoueur) {
        trésor.ajouter(autreJoueur.trésor);
        autreJoueur.trésor = Trésor.vide();
    }

    public void echangerCartes(List<Carte> cartesAEchanger, Pioche pioche) {
        main.retirer(cartesAEchanger);
        pioche.mettreDessous(cartesAEchanger);
        main.ajouter(pioche.tirerCartes(cartesAEchanger.size()));
    }

    public void payer(Cout cout) {
        trésor.payer(cout);
    }

    public void percevoirRevenus(Personnage personnage) {
        trésor.ajouter(cité.revenusPour(personnage));
    }

}
