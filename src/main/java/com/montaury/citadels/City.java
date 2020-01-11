package com.montaury.citadels;

import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.*;
import com.montaury.citadels.score.Score;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import static com.montaury.citadels.district.District.HAUNTED_CITY;
import static com.montaury.citadels.district.District.GREAT_WALL;

public class City {
    private static final int END_GAME_DISTRICT_NUMBER = 8;
    private static final Score ALL_COLORS_BONUS_SCORE = Score.of(3);
    private final Board board;
    private List<Card> districtCards = List.empty();

    public City(Board board) {
        this.board = board;
    }

    public void buildDistrict(Card card) {
        districtCards = districtCards.append(card);
        if (isComplete()) {
            board.mark(this);
        }
    }

    public boolean isComplete() {
        return districtCards.size() >= END_GAME_DISTRICT_NUMBER;
    }

    public Score score(Possession possession) {
        Score score = Score.of(totalDistrictsCost())
                .plus(districtsScoreBonus(possession));
        if (winsAllColorBonus()) {
            score = score.plus(ALL_COLORS_BONUS_SCORE);
        }
        if (board.isFirst(this)) {
            score = score.plus(Score.of(2));
        }
        if (isComplete()) {
            score = score.plus(Score.of(2));
        }
        return score;
    }

    private Score districtsScoreBonus(Possession possession) {
        return districts().map(district -> district.applyScoreBonus(possession)).fold(Score.nil(), Score::plus);
    }

    private boolean winsAllColorBonus() {
        return districtTypes().size() == DistrictType.values().length
                || districtTypes().size() == DistrictType.values().length - 1 && isBuilt(HAUNTED_CITY) && builtSpecialDistricts() > 1;
    }

    private Cost totalDistrictsCost() {
        return districts().map(District::cost).fold(Cost.free(), Cost::plus);
    }

    private int builtSpecialDistricts() {
        return districtCards.map(Card::district).filter(district -> district.isOf(DistrictType.SPECIAL)).size();
    }

    private Set<DistrictType> districtTypes() {
        return districts().map(District::districtType).toSet();
    }

    public boolean isBuilt(District district) {
        return districts().contains(district);
    }

    public void destroyDistrict(Card card) {
        districtCards = districtCards.remove(card);
    }

    public List<DestructibleDistrict> districtsDestructibleBy(Player player) {
        return isComplete() ?
                List.empty() :
                districtCards
                        .filter(card -> card.district().isDestructible())
                        .filter(card -> player.canAfford(destructionCost(card)))
                        .map(card -> new DestructibleDistrict(card, destructionCost(card)));
    }

    private Cost destructionCost(Card card) {
        return card.district().cost().minus(isBuilt(GREAT_WALL) && card.district() != GREAT_WALL ? Cost.free() : Cost.of(1));
    }

    public List<District> districts() {
        return districtCards.map(Card::district);
    }

    public int incomeFor(Character character) {
        return districts()
                .map(district -> district.incomeFor(character))
                .sum().intValue();
    }
}
