package com.montaury.citadelles.tour;

import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;

import java.util.Collections;

public class Ordonnanceur {
    public List<Joueur> ordonnerJoueurs(List<Joueur> joueurs, Joueur joueurAvecCouronne) {
        java.util.List<Joueur> list = joueurs.asJavaMutable();
        Collections.rotate(list, -joueurs.indexOf(joueurAvecCouronne));
        return List.ofAll(list);
    }
}
