package com.montaury.citadels.player;

import com.montaury.citadels.CityFixtures;
import com.montaury.citadels.district.Card;

import static com.montaury.citadels.CityFixtures.emptyCity;

public class PlayerFixtures {
    public static Player aPlayer() {
        return aPlayerWith(Gold.empty());
    }

    public static Player aPlayerWithAge(int age) {
        return new Player("Nom", age, emptyCity(), Gold.empty(), new StubPlayerController());
    }
    public static Player aPlayerWith(Gold gold) {
        return aPlayer("nom", gold);
    }

    public static Player aPlayerHavingBuilt(Card... cards) {
        return new Player("nom", 20, CityFixtures.cityWith(cards), new StubPlayerController());
    }

    public static Player aPlayer(String nom, Gold gold) {
        return new Player(nom, 20, emptyCity(), gold, new StubPlayerController());
    }

    public static Player aPlayer(String nom, PlayerController controller) {
        return new Player(nom, 20, emptyCity(), controller);
    }

    public static Player aPlayer(PlayerController controller) {
        return aPlayer("Nom", controller);
    }
}
