package com.montaury.citadelles.joueur;

import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.Tuple2;
import io.vavr.collection.*;

import java.util.Scanner;
import java.util.function.Function;

public class ControlleurHumain implements ControlleurDeJoueur {

    @Override
    public Personnage choisirSonPersonnage(List<Personnage> personnagesDisponibles, List<Personnage> personnagesEcartesFaceVisible) {
        afficherPersonnagesPourChoix(personnagesDisponibles, personnagesEcartesFaceVisible);
        return personnagesDisponibles.get(scanner.nextInt() - 1);
    }

    @Override
    public TypeAction choisirAction(List<TypeAction> actionsPossibles) {
        afficherActionsPourChoix(actionsPossibles);
        return actionsPossibles.get(scanner.nextInt() - 1);
    }

    private void afficherActionsPourChoix(List<TypeAction> actionsPossibles) {
        for (int i = 0; i < actionsPossibles.size(); i++) {
            System.out.println(actionsPossibles.get(i).name() + "(" + (i + 1) + "), ");
        }
    }

    @Override
    public Carte choisirParmi(Set<Carte> cartesDisponibles) {
        List<Carte> cartes = cartesDisponibles.toList();
        afficherCartesPourChoix(cartes);
        return cartes.get(scanner.nextInt() - 1);
    }

    private void afficherCartesPourChoix(List<Carte> cartes) {
        for (int i = 0; i < cartes.size(); i++) {
            System.out.println(cartes.get(i).name() + "(" + (i + 1) + "), ");
        }
    }

    @Override
    public Personnage choisirParmi(List<Personnage> personnages) {
        afficherPersonnagesPourChoix(personnages);
        return personnages.get(scanner.nextInt() - 1);
    }

    @Override
    public Joueur choisirParmiJoueurs(List<Joueur> joueurs) {
        afficherJoueursPourChoix(joueurs);
        return joueurs.get(scanner.nextInt() - 1);
    }

    @Override
    public Set<Carte> choisirPlusieursParmi(Set<Carte> cartes) {
        List<Carte> listeCartes = cartes.toList();
        System.out.println("Choisissez les cartes à échanger (ex: 1,2,3");
        for (int i = 0; i < listeCartes.size(); i++) {
            System.out.println(listeCartes.get(i).name() + "(" + (i + 1) + "), ");
        }
        String choix = scanner.next();
        String[] positionCartes = choix.split(",");
        return Stream.of(positionCartes)
                .map(Integer::valueOf)
                .map(listeCartes::get)
                .toSet();
    }

    @Override
    public QuartierDestructible choisirQuartierADetruireParmi(Map<Joueur, List<QuartierDestructible>> joueursAttaquables) {
        System.out.println("Choisissez le quartier à détruire");
        int i = 0;
        Seq<QuartierDestructible> quartierDestructiblesOrdonnes = joueursAttaquables.values().flatMap(Function.identity());
        for (Tuple2<Joueur, List<QuartierDestructible>> jq : joueursAttaquables) {
            System.out.println(jq._1.nom());
            for (QuartierDestructible quartierDestructible : jq._2) {
                System.out.println(quartierDestructible.quartier() + " (" + i++ + ")");
            }
        }
        return quartierDestructiblesOrdonnes.get(scanner.nextInt());
    }

    @Override
    public boolean accepterCarte(Carte carte) {
        System.out.println("Souhaitez-vous récupérer la carte " + carte + " ? (o/n)");
        return scanner.next().equals("o");
    }

    private static void afficherPersonnagesPourChoix(List<Personnage> personnages) {
        for (int i = 0; i < personnages.size(); i++) {
            System.out.println(personnages.get(i).nom() + "(" + (i + 1) + "), ");
        }
    }

    private static void afficherJoueursPourChoix(List<Joueur> joueurs) {
        for (int i = 0; i < joueurs.size(); i++) {
            System.out.println(joueurs.get(i).nom() + "(" + (i + 1) + "), ");
        }
    }

    private static void afficherPersonnagesPourChoix(List<Personnage> personnages, List<Personnage> personnagesEcartesFaceVisible) {
        afficherPersonnagesPourChoix(personnages);
        for (int i = 0; i < personnagesEcartesFaceVisible.size(); i++) {
            System.out.println("Ecarté: " + personnagesEcartesFaceVisible.get(i).name());
        }
    }

    private final Scanner scanner = new Scanner(System.in);
}
