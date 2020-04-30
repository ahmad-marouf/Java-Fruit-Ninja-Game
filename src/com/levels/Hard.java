package com.levels;

public class Hard implements Difficulty {
    @Override
    public int getInitialVelocity() {
        return 0;
    }

    @Override
    public int getFinalVelocity() {
        return 9;
    }

    @Override
    public int getSpawnRate() {
        return 500;
    }
}
