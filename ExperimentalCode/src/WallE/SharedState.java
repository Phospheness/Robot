package WallE;

import java.util.concurrent.atomic.AtomicBoolean;

// Needed a way for multiple classes to interact with eachother, even if they both require each other. Atomic Booleans are more precise when working with multiple threads.

public class SharedState {
    private AtomicBoolean wallDetected = new AtomicBoolean(false);
    private volatile boolean shouldMoveForward = true;
    
    public void setWallDetected(boolean detected) {
        wallDetected.set(detected);
    }
    public synchronized void setShouldMoveForward(boolean shouldMove) {
        this.shouldMoveForward = shouldMove;
    }

    public synchronized boolean getShouldMoveForward() {
        return shouldMoveForward;
    }
    public boolean isWallDetected() {
    	
        return wallDetected.get();
    }
}
