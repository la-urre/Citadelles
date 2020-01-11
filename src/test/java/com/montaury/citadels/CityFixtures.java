package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import io.vavr.collection.List;

public class CityFixtures {

    public static City emptyCity() {
        return new City(new Board());
    }

    public static City cityWith(Card... cards) {
        City city = emptyCity();
        List.of(cards).forEach(city::buildDistrict);
        return city;
    }
}
