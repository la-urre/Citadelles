package com.montaury.citadelles.faux;

import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.joueur.ControlleurDeJoueur;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import io.vavr.control.Option;

public class FauxControlleur implements ControlleurDeJoueur {

    public List<Personnage> personnagesDisponibles;
    public List<Personnage> personnagesEcartesFaceVisible;
    public List<List<TypeAction>> actionsPossibles = List.empty();
    public Option<Carte> carteProposee = Option.none();
    private Personnage personnagePrechoisi;
    private Carte cartePrechoisie;
    public Set<Carte> cartesDisponibles = HashSet.empty();
    private List<TypeAction> typeActionPrechoisi = List.empty();
    private Joueur joueurPrechoisi;
    public List<Joueur> joueursPourEchange = List.empty();
    public Set<Carte> cartesAEchanger;
    private Set<Carte> cartesPrechoisies;
    public Map<Joueur, List<QuartierDestructible>> quartiersDestructibles = HashMap.empty();
    private boolean cartePreAcceptee;

    public void prechoisirPersonnage(Personnage personnage) {
        personnagePrechoisi = personnage;
    }

    public void prechoisirJoueur(Joueur joueur) {
        joueurPrechoisi = joueur;
    }

    public void prechoisirCarte(Carte carte) {
        cartePrechoisie = carte;
    }

    public void prechoisirCartes(Set<Carte> cartes) {
        cartesPrechoisies = cartes;
    }

    @Override
    public Personnage choisirSonPersonnage(List<Personnage> personnagesDisponibles, List<Personnage> personnagesEcartesFaceVisible) {
        this.personnagesDisponibles = personnagesDisponibles;
        this.personnagesEcartesFaceVisible = personnagesEcartesFaceVisible;
        return personnagePrechoisi == null ? personnagesDisponibles.head() : personnagePrechoisi;
    }

    public void prechoisirAction(TypeAction typeAction) {
        prechoisirActions(List.of(typeAction));
    }

    public void prechoisirActions(List<TypeAction> typesAction) {
        typeActionPrechoisi = typesAction;
    }

    @Override
    public TypeAction choisirAction(List<TypeAction> actionsPossibles) {
        this.actionsPossibles = this.actionsPossibles.append(actionsPossibles);
        Option<TypeAction> head = typeActionPrechoisi.headOption();
        this.typeActionPrechoisi = this.typeActionPrechoisi.tailOption().getOrElse(List.empty());
        return head.getOrElse(TypeAction.TERMINER_TOUR);
    }

    @Override
    public Carte choisirParmi(Set<Carte> cartesDisponibles) {
        this.cartesDisponibles = cartesDisponibles;
        return cartePrechoisie;
    }

    @Override
    public Personnage choisirParmi(List<Personnage> personnagesDisponibles) {
        this.personnagesDisponibles = personnagesDisponibles;
        return personnagePrechoisi;
    }

    @Override
    public Joueur choisirParmiJoueurs(List<Joueur> joueurs) {
        joueursPourEchange = joueurs;
        return joueurPrechoisi;
    }

    @Override
    public Set<Carte> choisirPlusieursParmi(Set<Carte> cartes) {
        cartesAEchanger = cartes;
        return cartesPrechoisies;
    }

    @Override
    public QuartierDestructible choisirQuartierADetruireParmi(Map<Joueur, List<QuartierDestructible>> quartiersDestructibles) {
        this.quartiersDestructibles = quartiersDestructibles;
        return quartiersDestructibles.flatMap(Tuple2::_2).find(quartierDestructible -> quartierDestructible.quartier() == cartePrechoisie).get();
    }

    @Override
    public boolean accepterCarte(Carte carte) {
        carteProposee = Option.of(carte);
        return cartePreAcceptee;
    }

    public void preAccepterCarte() {
        cartePreAcceptee = true;
    }
}
