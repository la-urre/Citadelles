package com.montaury.citadels.character;

import io.vavr.collection.List;

public class RandomCharacterSelector {

    public Character among(List<Character> characters) {
        return characters.get((int) Math.floor(Math.random() * characters.size()));
    }
}
