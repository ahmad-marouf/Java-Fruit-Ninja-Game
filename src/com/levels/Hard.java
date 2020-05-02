package com.levels;

public class Hard implements Difficulty {
    @Override
    public int getInitialVelocity() {
        return 8;
    }

    @Override
    public int getFinalVelocity() {
        return -8;
    }

    @Override
    public int getSpawnRate() {
        return 500;
    }

    @Override
    public String toString() {
        return "Hard";
    }
}
