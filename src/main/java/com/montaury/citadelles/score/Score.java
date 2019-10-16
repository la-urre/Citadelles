package com.montaury.citadelles.score;

import com.montaury.citadelles.quartier.Cout;

import java.util.Objects;

public class Score implements Comparable<Score> {
    public static Score nul() {
        return new Score(0);
    }

    public static Score de(int points) {
        return new Score(points);
    }

    public static Score de(Cout cout) {
        return new Score(cout.montant());
    }

    private Score(int points) {
        this.points = points;
    }

    public Score plus(Score score) {
        return Score.de(points + score.points);
    }

    private final int points;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return points == score.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }

    @Override
    public int compareTo(Score score) {
        return Integer.compare(points, score.points);
    }
}
