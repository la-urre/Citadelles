package com.montaury.citadelles.quartier;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public enum Carte {
    MANOIR_1(Quartier.MANOIR),
    MANOIR_2(Quartier.MANOIR),
    MANOIR_3(Quartier.MANOIR),
    MANOIR_4(Quartier.MANOIR),
    MANOIR_5(Quartier.MANOIR),
    CHATEAU_1(Quartier.CHATEAU),
    CHATEAU_2(Quartier.CHATEAU),
    CHATEAU_3(Quartier.CHATEAU),
    CHATEAU_4(Quartier.CHATEAU),
    PALAIS_1(Quartier.PALAIS),
    PALAIS_2(Quartier.PALAIS),
    PALAIS_3(Quartier.PALAIS),

    TOUR_DE_GUET_1(Quartier.TOUR_DE_GUET),
    TOUR_DE_GUET_2(Quartier.TOUR_DE_GUET),
    TOUR_DE_GUET_3(Quartier.TOUR_DE_GUET),
    PRISON_1(Quartier.PRISON),
    PRISON_2(Quartier.PRISON),
    PRISON_3(Quartier.PRISON),
    CASERNE_1(Quartier.CASERNE),
    CASERNE_2(Quartier.CASERNE),
    CASERNE_3(Quartier.CASERNE),
    FORTERESSE_1(Quartier.FORTERESSE),
    FORTERESSE_2(Quartier.FORTERESSE),

    TAVERNE_1(Quartier.TAVERNE),
    TAVERNE_2(Quartier.TAVERNE),
    TAVERNE_3(Quartier.TAVERNE),
    TAVERNE_4(Quartier.TAVERNE),
    TAVERNE_5(Quartier.TAVERNE),
    ECHOPPE_1(Quartier.ECHOPPE),
    ECHOPPE_2(Quartier.ECHOPPE),
    ECHOPPE_3(Quartier.ECHOPPE),
    MARCHE_1(Quartier.MARCHE),
    MARCHE_2(Quartier.MARCHE),
    MARCHE_3(Quartier.MARCHE),
    MARCHE_4(Quartier.MARCHE),
    COMPTOIR_1(Quartier.COMPTOIR),
    COMPTOIR_2(Quartier.COMPTOIR),
    COMPTOIR_3(Quartier.COMPTOIR),
    PORT_1(Quartier.PORT),
    PORT_2(Quartier.PORT),
    PORT_3(Quartier.PORT),
    HOTEL_DE_VILLE_1(Quartier.HOTEL_DE_VILLE),
    HOTEL_DE_VILLE_2(Quartier.HOTEL_DE_VILLE),

    TEMPLE_1(Quartier.TEMPLE),
    TEMPLE_2(Quartier.TEMPLE),
    TEMPLE_3(Quartier.TEMPLE),
    EGLISE_1(Quartier.EGLISE),
    EGLISE_2(Quartier.EGLISE),
    EGLISE_3(Quartier.EGLISE),
    MONASTERE_1(Quartier.MONASTERE),
    MONASTERE_2(Quartier.MONASTERE),
    MONASTERE_3(Quartier.MONASTERE),
    CATHEDRALE_1(Quartier.CATHEDRALE),
    CATHEDRALE_2(Quartier.CATHEDRALE),

    COUR_DES_MIRACLES(Quartier.COUR_DES_MIRACLES),
    DRACOPORT(Quartier.DRACOPORT),
    UNIVERSITE(Quartier.UNIVERSITE),
    TRESOR_IMPERIAL(Quartier.TRESOR_IMPERIAL),
    SALLE_DES_CARTES(Quartier.SALLE_DES_CARTES),
    OBSERVATOIRE(Quartier.OBSERVATOIRE),
    CIMETIERE(Quartier.CIMETIERE),
    FORGE(Quartier.FORGE),
    BIBLIOTHEQUE(Quartier.BIBLIOTHEQUE),
    ECOLE_DE_MAGIE(Quartier.ECOLE_DE_MAGIE),
    LABORATOIRE(Quartier.LABORATOIRE),
    GRANDE_MURAILLE(Quartier.GRANDE_MURAILLE),
    DONJON_1(Quartier.DONJON),
    DONJON_2(Quartier.DONJON);

    Carte(Quartier quartier) {
        this.quartier = quartier;
    }

    private final Quartier quartier;

    public Quartier quartier() {
        return quartier;
    }

    public static Set<Carte> toutes() {
        return HashSet.of(values());
    }
}
