package com.montaury.citadelles;

import com.montaury.citadelles.joueur.ControlleurHumain;
import com.montaury.citadelles.joueur.ControlleurOrdinateur;
import com.montaury.citadelles.joueur.Joueur;
import io.vavr.collection.List;

import java.util.Scanner;

public class ConfigurateurDeJeuSolo {

    public Jeu nouveauJeu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Quel est votre nom ? ");
        String nomDuJoueur = scanner.next();
        System.out.println("Quel est votre age ? ");
        int ageDuJoueur = scanner.nextInt();
        CitésComplètes citésComplètes = new CitésComplètes();
        List<Joueur> joueurs = List.of(new Joueur(nomDuJoueur, ageDuJoueur, new Cité(citésComplètes), new ControlleurHumain()));
        int nombreDeJoueurs = faireSaisirLeNombreDeJoueurs(scanner);
        joueurs = joueurs.appendAll(List.range(0, nombreDeJoueurs-1)
                .map(i -> new Joueur("Computer " + i, 35, new Cité(citésComplètes), new ControlleurOrdinateur())));
        return new Jeu(joueurs);
    }

    private static int faireSaisirLeNombreDeJoueurs(Scanner scanner) {
        System.out.println("Saisir le nombre de joueurs total (entre 2 et 8): ");
        int nombreDeJoueur;
        do {
            nombreDeJoueur = scanner.nextInt();
        } while(nombreDeJoueur < MINIMUM_DE_JOUEURS || nombreDeJoueur > MAXIMUM_DE_JOUEURS);
        return nombreDeJoueur;
    }

    private static final int MINIMUM_DE_JOUEURS = 2;
    private static final int MAXIMUM_DE_JOUEURS = 8;
}
