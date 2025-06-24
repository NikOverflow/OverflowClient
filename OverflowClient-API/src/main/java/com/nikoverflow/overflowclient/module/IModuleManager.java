package com.nikoverflow.overflowclient.module;

public interface IModuleManager {

    void addModule(Module module);
    Module getModule(String name);
}