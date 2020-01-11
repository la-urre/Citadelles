package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.district.DestructibleDistrict;
import io.vavr.collection.List;
import io.vavr.collection.Map;

public class DestroyDistrictAction implements Action {

    @Override
    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return associations.districtsDestructibleBy(player).exists(districtsByPlayer -> !districtsByPlayer._2().isEmpty());
    }

    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Map<Player, List<DestructibleDistrict>> districtsDestructibleByPlayer = associations.districtsDestructibleBy(playerCharacterAssociation.player());
        DestructibleDistrict districtToDestroy = playerCharacterAssociation.player().controller.selectDistrictToDestroyAmong(districtsDestructibleByPlayer);
        Player playerToAttack = districtsDestructibleByPlayer.find(districtsByPlayer -> districtsByPlayer._2.contains(districtToDestroy)).get()._1;
        playerCharacterAssociation.player().pay(districtToDestroy.destructionCost());
        Card destroyedDistrict = districtToDestroy.card();
        playerToAttack.city().destroyDistrict(destroyedDistrict);
        associations.playerTakingDestroyedDistrict(destroyedDistrict)
                .onEmpty(() -> cardPile.discard(destroyedDistrict));
    }
}
