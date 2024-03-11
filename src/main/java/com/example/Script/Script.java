package com.example.Script;

public abstract class Script {
    private String name;

    public Script(String name) {
        this.name = name;
    }

    public abstract void onStart();
    public abstract void onStop();
    public abstract int onLoop();

    public String getName() {
        return name;
    }
}
