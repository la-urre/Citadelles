package com.montaury.citadels.character;

import io.vavr.collection.List;

public class StubRandomCharacterSelector extends RandomCharacterSelector {
    public StubRandomCharacterSelector(List<Character> stubbedCharacters) {
        this.stubbedCharacters = stubbedCharacters;
    }

    @Override
    public Character among(List<Character> characters) {
        this.proposedCharacters = characters;
        Character next;
        do {
            next = this.stubbedCharacters.head();
            this.stubbedCharacters = this.stubbedCharacters.tail();
        }
        while (!characters.contains(next));
        return next;
    }

    public List<Character> proposedCharacters;
    private List<Character> stubbedCharacters;
}
