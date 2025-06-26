package com.nikoverflow.overflowclient.event;

public interface Cancellable {

    void cancel();
    boolean isCancelled();
}