package com.montaury.citadels.player;

import com.montaury.citadels.district.Cost;

public class Gold {
    public static Gold empty() {
        return new Gold();
    }

    private Gold() {
    }

    public void add(int coins) {
        this.coins += coins;
    }

    void add(Gold gold) {
        this.coins += gold.coins;
    }

    void pay(Cost cost) {
        this.coins -= cost.amount();
    }

    boolean canPay(Cost cost) {
        return this.coins >= cost.amount();
    }

    public int coins() {
        return coins;
    }

    private int coins;
}
