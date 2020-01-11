package com.montaury.citadels.character;

import io.vavr.collection.List;

public class StubRandomCharacterSelector extends RandomCharacterSelector {
    public StubRandomCharacterSelector(List<Character> characters) {
        this.characters = characters;
    }

    @Override
    public Character among(List<Character> characters) {
        Character next;
        do {
            next = this.characters.head();
            this.characters = this.characters.tail();
        }
        while (!characters.contains(next));
        return next;
    }

    private List<Character> characters;
}
