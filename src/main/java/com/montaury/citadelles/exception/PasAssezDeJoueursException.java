package com.montaury.citadelles.exception;

public class PasAssezDeJoueursException extends RuntimeException {
    public PasAssezDeJoueursException() {
        super("Pas assez de joueurs, veuillez en ajouter");
    }
}
