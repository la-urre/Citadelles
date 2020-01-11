package com.montaury.citadels.district;

public final class Cost {
    public static Cost free() {
        return new Cost(0);
    }

    public static Cost of(int amount) {
        return new Cost(amount);
    }

    private Cost(int amount) {
        this.amount = amount;
    }

    public Cost minus(Cost cost) {
        return new Cost(amount - cost.amount);
    }

    public Cost plus(Cost cost) {
        return new Cost(amount + cost.amount);
    }

    public int amount() {
        return amount;
    }

    private final int amount;
}
