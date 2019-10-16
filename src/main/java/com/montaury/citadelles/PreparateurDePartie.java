package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;

public class PreparateurDePartie {
    private List<String> nomJoueurs = List.empty();
    private static final int MINIMUM_DE_JOUEURS = 2;
    private static final int MAXIMUM_DE_JOUEURS = 8;

    public void ajouterJoueur(String nomDuJoueur) {
        nomJoueurs = nomJoueurs.append(nomDuJoueur);
    }

    public Jeu preparer() {
        CitésComplètes citésComplètes = new CitésComplètes();
        return new Jeu(nomJoueurs.map(nom -> new Joueur(nom, 20, new Cité(citésComplètes))));
    }

    public boolean peutCommencer() {
        return nomJoueurs.size() >= MINIMUM_DE_JOUEURS;
    }

    public boolean peutAjouterJoueur() {
        return nomJoueurs.size() < MAXIMUM_DE_JOUEURS;
    }

    public boolean joueurExiste(String nomDuJoueur) {
        return nomJoueurs.contains(nomDuJoueur);
    }
}
