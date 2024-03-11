package com.example.StevesPlugin;

import java.util.function.Consumer;

public class Task {
    protected Consumer<Boolean> onCompleteCallback;
    protected boolean isRunning = false;
    private Thread taskThread;

    protected Task() {

    }

    public Task onComplete(Consumer<Boolean> onCompleteCallback) {
        this.onCompleteCallback = onCompleteCallback;
        return this;
    }

    public int onLoop() {
        return 1000;
    }

    public Task start() {
        isRunning = true;


        taskThread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(onLoop());
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });

        taskThread.start();

        return this;
    }

    public void stop() {
        isRunning = false;
    }
}
