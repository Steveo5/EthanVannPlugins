package com.example.Script;

public class ScriptState {
    private Script script;
    private ScriptStatus status;
    private long lastLoopedAt = 0L;
    private long lastLoopInterval = 0L;

    public ScriptState(Script script, ScriptStatus status) {

        this.script = script;
        this.status = status;
    }

    public Script getScript() {
        return script;
    }

    public ScriptStatus getStatus() {
        return status;
    }

    public void setStatus(ScriptStatus status) {
        this.status = status;
    }

    public void setLastLoopedAt(long ms) {

        this.lastLoopedAt = ms;
    }

    public long getLastLoopedAt() {
        return lastLoopedAt;
    }

    public long getLastLoopInterval() {
        return lastLoopInterval;
    }

    public void setLastLoopInterval(long lastLoopInterval) {
        this.lastLoopInterval = lastLoopInterval;
    }
}
