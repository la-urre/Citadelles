package com.montaury.citadelles.joueur;

import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.quartier.QuartierDestructible;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

import java.util.Scanner;

public class ControlleurHumain implements ControlleurDeJoueur {
    @Override
    public Personnage choisirSonPersonnage(List<Personnage> personnagesDisponibles, List<Personnage> personnagesEcartesFaceVisible) {
        afficherPersonnagesPourChoix(personnagesDisponibles, personnagesEcartesFaceVisible);
        Scanner scanner = new Scanner(System.in);
        return personnagesDisponibles.get(scanner.nextInt() - 1);
    }

    @Override
    public TypeAction choisirAction(List<TypeAction> actionsPossibles) {
        afficherActionsPourChoix(actionsPossibles);
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
        return personnages.get(scanner.nextInt() - 1);
    }

    @Override
    public Joueur choisirParmiJoueurs(List<Joueur> joueurs) {
        return null;
    }

    @Override
    public Set<Carte> choisirPlusieursParmi(Set<Carte> cartes) {
        return null;
    }

    @Override
    public QuartierDestructible choisirQuartierADetruireParmi(Map<Joueur, List<QuartierDestructible>> joueursAttaquables) {
        return null;
    }

    @Override
    public boolean accepterCarte(Carte carte) {
        return false;
    }

    private static void afficherPersonnagesPourChoix(List<Personnage> personnages) {
        for (int i = 0; i < personnages.size(); i++) {
            System.out.println(personnages.get(i).name() + "(" + (i + 1) + "), ");
        }
    }

    private static void afficherPersonnagesPourChoix(List<Personnage> personnages, List<Personnage> personnagesEcartesFaceVisible) {
        afficherPersonnagesPourChoix(personnages);
        for (int i = 0; i < personnagesEcartesFaceVisible.size(); i++) {
            System.out.println("Ecarté: " + personnagesEcartesFaceVisible.get(i).name());
        }
    }
}
