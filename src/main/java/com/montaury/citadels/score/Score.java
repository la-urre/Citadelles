package com.montaury.citadels.score;

import com.montaury.citadels.district.Cost;

import java.util.Objects;

public class Score implements Comparable<Score> {
    public static Score nil() {
        return new Score(0);
    }

    public static Score of(int points) {
        return new Score(points);
    }

    public static Score of(Cost cost) {
        return new Score(cost.amount());
    }

    private Score(int points) {
        this.points = points;
    }

    public Score plus(Score score) {
        return Score.of(points + score.points);
    }

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

    private final int points;
}
