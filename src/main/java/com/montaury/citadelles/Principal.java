package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.score.Classement;
import io.vavr.collection.List;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        System.out.println("Le jeu Citadelles va commencer !");

        Jeu jeu = preparerJeu();

        System.out.println("Le jeu va commencer avec :" + noms(jeu.joueurs()));

        Pioche pioche = Pioche.completeMelangee();
        Joueur joueurAvecCouronne = new MiseEnPlace().commencerPartie(pioche, jeu.joueurs());

        TourDeJeu dernierTourDeJeu = jeu.jouer(joueurAvecCouronne, pioche);

        List<Joueur> classement = new Classement().classer(dernierTourDeJeu);
        System.out.println("Classement: " + classement);
    }

    private static List<String> noms(List<Joueur> joueurs) {
        return joueurs.map(Joueur::nom);
    }

    private static Jeu preparerJeu() {
        Scanner scanner = new Scanner(System.in);
        PreparateurDePartie preparateurDePartie = new PreparateurDePartie();
        while (preparateurDePartie.peutAjouterJoueur() && !preparateurDePartie.peutCommencer()) {
            System.out.println("Saisir le nom du joueur (2 minimum, c pour commencer le jeu): ");
            String nomDuJoueur = scanner.next();
            if (!nomDuJoueur.equals("c")) {
                if (!preparateurDePartie.joueurExiste(nomDuJoueur)) {
                    preparateurDePartie.ajouterJoueur(nomDuJoueur);
                }
                else {
                    System.out.println("Ce joueur a déjà été ajouté à la partie");
                }
            }
        }
        return preparateurDePartie.preparer();
    }

}
