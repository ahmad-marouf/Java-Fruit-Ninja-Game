package com.levels;

public class Easy implements Difficulty {

    @Override
    public String toString() {
        return "Easy";
    }

    @Override
    public int getInitialVelocity() {
        return 5;
    }

    @Override
    public int getFinalVelocity() {
        return -5;
    }

    @Override
    public int getSpawnRate() {
        return 1000;
    }
}
