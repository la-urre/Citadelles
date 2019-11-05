package com.montaury.citadelles;

import com.montaury.citadelles.joueur.ControlleurHumain;
import com.montaury.citadelles.joueur.ControlleurOrdinateur;
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
        System.out.println("Hello! Quel est votre nom ? ");
        String nomDuJoueur = scanner.next();
        System.out.println("Quel est votre age ? ");
        int ageDuJoueur = scanner.nextInt();
        CitésComplètes citésComplètes = new CitésComplètes();
        preparateurDePartie.ajouterJoueur(new Joueur(nomDuJoueur, ageDuJoueur, new Cité(citésComplètes), new ControlleurHumain()));
        int nombreDeJoueurs = faireSaisirLeNombreDeJoueurs(scanner);
        List.range(0, nombreDeJoueurs-1).forEach(i -> preparateurDePartie.ajouterJoueur(new Joueur("Computer " + i, 35, new Cité(citésComplètes), new ControlleurOrdinateur())));
        return preparateurDePartie.preparer();
    }

    private static int faireSaisirLeNombreDeJoueurs(Scanner scanner) {
        System.out.println("Saisir le nombre de joueurs total (entre 2 et 8): ");
        int nombreDeJoueur;
        do {
            nombreDeJoueur = scanner.nextInt();
        } while(nombreDeJoueur < PreparateurDePartie.MINIMUM_DE_JOUEURS || nombreDeJoueur > PreparateurDePartie.MAXIMUM_DE_JOUEURS);
        return nombreDeJoueur;
    }

}
