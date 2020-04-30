package com.levels;

public class Easy implements Difficulty {
    @Override
    public int getInitialVelocity() {
        return 0;
    }

    @Override
    public int getFinalVelocity() {
        return 5;
    }

    @Override
    public int getSpawnRate() {
        return 1000;
    }
}
