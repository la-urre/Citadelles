package com.montaury.citadels.player;

import com.montaury.citadels.City;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.Possession;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.Cost;
import com.montaury.citadels.score.Score;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public class Player {
    private final String name;
    private final int age;
    private final City city;
    public final PlayerController controller;
    private Gold gold;
    private Hand hand = Hand.empty();

    public Player(String name, int age, City city, PlayerController controller) {
        this(name, age, city, Gold.empty(), controller);
    }

    Player(String name, int age, City city, Gold gold, PlayerController controller) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.gold = gold;
        this.controller = controller;
    }

    public String name() {
        return name;
    }

    public int age() {
        return age;
    }

    public City city() {
        return city;
    }

    public void earn(int goldCoins) {
        gold.add(goldCoins);
    }

    public void addCardsInHand(Set<Card> cards) {
        hand.add(cards);
    }

    public void addCardInHand(Card card) {
        hand.add(card);
    }

    public Gold gold() {
        return gold;
    }

    public boolean canAfford(Cost cost) {
        return gold.canPay(cost);
    }

    private boolean canBuildDistrict(Card card) {
        return gold.canPay(card.district().cost()) && !city.isBuilt(card.district());
    }

    public Set<Card> buildableDistrictsInHand() {
        return hand.cards().filter(this::canBuildDistrict);
    }

    public void buildDistrict(Card card) {
        if (!canBuildDistrict(card)) {
            return;
        }
        hand.draw(card);
        city.buildDistrict(card);
        gold.pay(card.district().cost());
    }

    public Score score() {
        return city.score(new Possession(gold, hand));
    }

    public void exchangeHandWith(Player otherPlayer) {
        Hand swappingHand = hand;
        hand = otherPlayer.hand;
        otherPlayer.hand = swappingHand;
    }

    public Hand hand() {
        return hand;
    }

    public void steal(Player otherPlayer) {
        gold.add(otherPlayer.gold);
        otherPlayer.gold = Gold.empty();
    }

    public void swapCards(List<Card> cardsToSwap, CardPile cardPile) {
        hand.draw(cardsToSwap);
        cardPile.discard(cardsToSwap);
        hand.add(cardPile.draw(cardsToSwap.size()));
    }

    public void pay(Cost cost) {
        gold.pay(cost);
    }

    public void receiveIncomeFor(Character character) {
        gold.add(city.incomeFor(character));
    }

}
