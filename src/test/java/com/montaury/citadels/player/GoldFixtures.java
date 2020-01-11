package com.montaury.citadels.player;

public class GoldFixtures {
    public static Gold gold(int coins) {
        Gold gold = Gold.empty();
        gold.add(coins);
        return gold;
    }
}
