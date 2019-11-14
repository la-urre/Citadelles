package com.montaury.citadelles.personnage;

import com.montaury.citadelles.action.TypeAction;
import com.montaury.citadelles.quartier.TypeQuartier;
import io.vavr.collection.List;
import org.junit.Test;

import static com.montaury.citadelles.personnage.Personnage.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonnageDevrait {
    @Test
    public void retourner_les_personnages_dans_l_ordre() {
        List<Personnage> personnages = Personnage.dansLOrdre();

        assertThat(personnages).containsExactly(ASSASSIN, VOLEUR, MAGICIEN, ROI, EVEQUE, MARCHAND, ARCHITECTE, CONDOTTIERE);
    }

    @Test
    public void permettre_a_un_assassin_d_assassiner() {
        assertThat(ASSASSIN.pouvoirs()).contains(TypeAction.ASSASSINER);
    }

    @Test
    public void permettre_a_un_voleur_de_voler() {
        assertThat(VOLEUR.pouvoirs()).contains(TypeAction.VOLER);
    }

    @Test
    public void permettre_a_un_magicien_d_echanger_sa_main_avec_un_autre_joueur() {
        assertThat(MAGICIEN.pouvoirs()).contains(TypeAction.ECHANGER_MAIN_AVEC_JOUEUR);
    }

    @Test
    public void permettre_a_un_magicien_d_echanger_des_cartes_avec_la_pioche() {
        assertThat(MAGICIEN.pouvoirs()).contains(TypeAction.ECHANGER_CARTES_AVEC_PIOCHE);
    }

    @Test
    public void permettre_a_un_roi_de_percevoir_ses_revenus() {
        assertThat(ROI.pouvoirs()).contains(TypeAction.PERCEVOIR_REVENUS);
    }

    @Test
    public void permettre_a_un_eveque_de_percevoir_ses_revenus() {
        assertThat(EVEQUE.pouvoirs()).contains(TypeAction.PERCEVOIR_REVENUS);
    }

    @Test
    public void permettre_a_un_marchand_de_percevoir_ses_revenus() {
        assertThat(MARCHAND.pouvoirs()).contains(TypeAction.PERCEVOIR_REVENUS);
    }

    @Test
    public void permettre_a_un_marchand_de_recevoir_une_piece_d_or() {
        assertThat(MARCHAND.pouvoirs()).contains(TypeAction.RECEVOIR_PIECE);
    }

    @Test
    public void permettre_a_un_architecte_de_piocher_2_cartes() {
        assertThat(ARCHITECTE.pouvoirs()).contains(TypeAction.PIOCHER_2_CARTES);
    }

    @Test
    public void permettre_a_un_architecte_de_batir_2_quartiers_supplementaires() {
        assertThat(ARCHITECTE.pouvoirs()).containsSubsequence(TypeAction.BATIR_QUARTIER, TypeAction.BATIR_QUARTIER);
    }

    @Test
    public void permettre_a_un_condottiere_de_percevoir_ses_revenus() {
        assertThat(CONDOTTIERE.pouvoirs()).contains(TypeAction.PERCEVOIR_REVENUS);
    }

    @Test
    public void permettre_a_un_condottiere_de_detruire_un_quartier() {
        assertThat(CONDOTTIERE.pouvoirs()).contains(TypeAction.DETRUIRE_QUARTIER);
    }

    @Test
    public void associer_le_roi_aux_quartiers_nobles() {
        assertThat(ROI.typeQuartierAssocie()).contains(TypeQuartier.NOBLE);
    }

    @Test
    public void associer_l_eveque_aux_quartiers_religieux() {
        assertThat(EVEQUE.typeQuartierAssocie()).contains(TypeQuartier.RELIGIEUX);
    }

    @Test
    public void associer_le_marchand_aux_quartiers_commercants() {
        assertThat(MARCHAND.typeQuartierAssocie()).contains(TypeQuartier.COMMERCANT);
    }

    @Test
    public void associer_le_condottiere_aux_quartiers_militaires() {
        assertThat(CONDOTTIERE.typeQuartierAssocie()).contains(TypeQuartier.MILITAIRE);
    }

}