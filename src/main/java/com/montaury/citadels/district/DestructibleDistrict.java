package com.montaury.citadels.district;

public class DestructibleDistrict {
    public DestructibleDistrict(Card card, Cost destructionCost) {
        this.card = card;
        this.destructionCost = destructionCost;
    }

    public Card card() {
        return card;
    }

    public Cost destructionCost() {
        return destructionCost;
    }

    private final Card card;
    private final Cost destructionCost;
}
