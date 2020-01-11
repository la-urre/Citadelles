package com.montaury.citadels.player;

import com.montaury.citadels.City;

import static com.montaury.citadels.CityFixtures.emptyCity;

public class PlayerFixtures {
    public static Player aPlayer() {
        return aPlayerWith(Gold.empty());
    }

    public static Player aPlayerWithAge(int age) {
        return new Player("Nom", age, emptyCity(), Gold.empty(), new StubPlayerController());
    }

    public static Player aPlayerWith(Hand hand) {
        return aPlayerWith(hand, Gold.empty());
    }

    public static Player aPlayerWith(Hand hand, PlayerController controller) {
        return aPlayerWith(hand, Gold.empty(), controller);
    }

    public static Player aPlayerWith(Gold gold) {
        return aPlayer(emptyCity(), gold);
    }

    public static Player aPlayerWith(Hand hand, Gold gold) {
        return aPlayerWith(hand, gold, new StubPlayerController());
    }

    public static Player aPlayerWith(Gold gold, PlayerController controller) {
        return aPlayer(gold, controller);
    }

    public static Player aPlayerWith(Hand hand, Gold gold, PlayerController controller) {
        Player player = aPlayer(gold, controller);
        player.addCardsInHand(hand.cards());
        return player;
    }

    public static Player aPlayerWith(Hand hand, City city, Gold gold) {
        Player player = aPlayer(city, gold);
        player.addCardsInHand(hand.cards());
        return player;
    }

    public static Player aPlayerWith(City city) {
        return aPlayer(city, Gold.empty(), new StubPlayerController());
    }

    public static Player aPlayerWith(City city, PlayerController controller) {
        return aPlayer( city, Gold.empty(), controller);
    }

    public static Player aPlayerWith(City city, Gold gold, PlayerController controller) {
        return aPlayer(city, gold, controller);
    }

    public static Player aPlayer(PlayerController controller) {
        return aPlayer(emptyCity(), Gold.empty(), controller);
    }

    private static Player aPlayer(Gold gold, PlayerController controller) {
        return aPlayer(emptyCity(), gold, controller);
    }

    private static Player aPlayer(City city, Gold gold) {
        return aPlayer(city, gold, new StubPlayerController());
    }

    private static Player aPlayer(City city, Gold gold, PlayerController controller) {
        return new Player("Nom", 20, city, gold, controller);
    }
}
