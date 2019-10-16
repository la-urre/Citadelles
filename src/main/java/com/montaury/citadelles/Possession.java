package com.montaury.citadelles;

import com.montaury.citadelles.joueur.Main;
import com.montaury.citadelles.joueur.Trésor;

public class Possession {
    public final Trésor trésor;
    public final Main main;

    public Possession(Trésor trésor, Main main) {
        this.trésor = trésor;
        this.main = main;
    }

    public int pieces() {
        return trésor.pieces();
    }

    public int nombreDeCartesEnMain() {
        return main.nombreDeCartes();
    }
}
