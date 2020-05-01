package com.levels;

public class Medium implements Difficulty {
    @Override
    public int getInitialVelocity() {
        return 7;
    }

    @Override
    public int getFinalVelocity() {
        return -7;
    }

    @Override
    public int getSpawnRate() {
        return 700;
    }
}
