package com.montaury.citadelles;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.collection.Set;
import io.vavr.control.Option;

public class Pioche {

    public static Pioche vide() {
        return new Pioche(List.empty());
    }

    public static Pioche completeMelangee() {
        return new Pioche(Carte.toutes().toList().shuffle());
    }

    Pioche(List<Carte> cartes) {
        this.cartes = Queue.ofAll(cartes);
    }

    public boolean peutFournirCartes(int nombre) {
        return cartes.size() >= nombre;
    }

    public Option<Carte> tirerCarte() {
        Option<Tuple2<Carte, Queue<Carte>>> defile = cartes.dequeueOption();
        cartes = defile.map(Tuple2::_2).getOrElse(cartes);
        return defile.map(Tuple2::_1);
    }

    public Set<Carte> tirerCartes(int nombre) {
        return List.range(0, nombre).flatMap(i -> tirerCarte()).toSet();
    }

    public void mettreDessous(Carte carte) {
        mettreDessous(List.of(carte));
    }

    public void mettreDessous(List<Carte> defausse) {
        cartes = cartes.enqueueAll(defausse);
    }

    public Set<Carte> echangerCartes(List<Carte> cartesAEchanger) {
        Set<Carte> nouvellesCartes = tirerCartes(cartesAEchanger.size());
        mettreDessous(cartesAEchanger);
        return nouvellesCartes;
    }

    private Queue<Carte> cartes;
}
