package com.nikoverflow.overflowclient;

public class OverflowClient implements IOverflowAPI {

    private static OverflowClient INSTANCE;

    public void initialize() {
        INSTANCE = this;
    }

    public static OverflowClient getInstance() {
        return INSTANCE;
    }
}
