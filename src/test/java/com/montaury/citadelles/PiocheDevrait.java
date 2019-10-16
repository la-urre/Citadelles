package com.montaury.citadelles;


import com.montaury.citadelles.quartier.Carte;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.junit.Test;

import static com.montaury.citadelles.PiochePrédéfinie.piocheAvec;
import static org.assertj.core.api.Assertions.assertThat;

public class PiocheDevrait {

    @Test
    public void ne_pas_donner_de_carte_si_elle_est_vide() {
        Option<Carte> carte = Pioche.vide().tirerCarte();

        assertThat(carte).isEqualTo(Option.none());
    }

    @Test
    public void donner_l_unique_carte_en_piochant_une_carte() {
        Option<Carte> carte = piocheAvec(Carte.CASERNE_1).tirerCarte();

        assertThat(carte).isEqualTo(Option.of(Carte.CASERNE_1));
    }

    @Test
    public void remettre_des_cartes_dessous() {
        Pioche pioche = piocheAvec(Carte.SALLE_DES_CARTES);

        pioche.mettreDessous(List.of(Carte.CASERNE_1, Carte.CATHEDRALE_1));

        pioche.tirerCarte();
        assertThat(pioche.tirerCarte()).isEqualTo(Option.of(Carte.CASERNE_1));
        assertThat(pioche.tirerCarte()).isEqualTo(Option.of(Carte.CATHEDRALE_1));
    }

    @Test
    public void donner_plusieurs_cartes_d_un_coup() {
        Pioche pioche = piocheAvec(Carte.SALLE_DES_CARTES, Carte.ECHOPPE_2);

        Set<Carte> cartes = pioche.tirerCartes(2);

        assertThat(cartes)
                .containsExactlyInAnyOrder(Carte.ECHOPPE_2, Carte.SALLE_DES_CARTES);
    }

    @Test
    public void donner_le_nombre_de_cartes_disponibles_si_le_nombre_demande_est_plus_grand_que_la_pioche() {
        Pioche pioche = piocheAvec(Carte.SALLE_DES_CARTES, Carte.EGLISE_2);

        Set<Carte> cartes = pioche.tirerCartes(4);

        assertThat(cartes)
                .containsExactlyInAnyOrder(Carte.EGLISE_2, Carte.SALLE_DES_CARTES);
    }

    @Test
    public void echanger_des_cartes() {
        Pioche pioche = piocheAvec(Carte.SALLE_DES_CARTES, Carte.TEMPLE_3);

        Set<Carte> cartes = pioche.echangerCartes(List.of(Carte.CHATEAU_1, Carte.MANOIR_1));

        assertThat(cartes)
                .containsExactlyInAnyOrder(Carte.SALLE_DES_CARTES, Carte.TEMPLE_3);
        assertThat(pioche.tirerCartes(2))
                .containsExactlyInAnyOrder(Carte.CHATEAU_1, Carte.MANOIR_1);
    }
}