package com.montaury.citadelles.quartier;

import com.montaury.citadelles.personnage.Personnage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuartierDevrait {

    @Test
    public void ne_pas_etre_destructible_si_c_est_le_donjon() {
        boolean estDestructible = Quartier.DONJON.estDestructible();

        assertThat(estDestructible).isFalse();
    }

    @Test
    public void rapporter_1_piece_s_il_est_de_la_meme_couleur_que_le_personnage() {
        int revenus = Quartier.CATHEDRALE.percevoirRevenusPour(Personnage.EVEQUE);

        assertThat(revenus).isEqualTo(1);
    }

    @Test
    public void ne_pas_rapporter_de_piece_s_il_n_est_pas_de_la_meme_couleur_que_le_personnage() {
        int revenus = Quartier.CATHEDRALE.percevoirRevenusPour(Personnage.MARCHAND);

        assertThat(revenus).isEqualTo(0);
    }

    @Test
    public void rapporter_1_piece_pour_l_ecole_de_magie_si_le_personnage_a_une_couleur() {
        int revenus = Quartier.ECOLE_DE_MAGIE.percevoirRevenusPour(Personnage.MARCHAND);

        assertThat(revenus).isEqualTo(1);
    }

}