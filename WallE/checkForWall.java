package WallE;
import java.util.*;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class checkForWall implements Behavior {
    private volatile boolean suppressed = false;
    private MovePilot pilot;
    private UltrasonicSensor sensorService;
    private SharedState sharedState;
    private HeadMotor headMotor;
    private NodeManager nodeManager;
    private DFS dfs;
    private boolean atJunction;
    
    public checkForWall(UltrasonicSensor sensorService, MovePilot pilot, HeadMotor headMotor,  NodeManager nodeManager, DFS dfs,  SharedState sharedState) {
        this.pilot = pilot;
        this.sensorService = sensorService;
        this.sharedState = sharedState;
        this.headMotor = headMotor;
        this.nodeManager = nodeManager;
        this.dfs = dfs;
    }

    @Override
    public boolean takeControl() {
        // This could continuously check for wall proximity
        return !atJunction && sensorService.getDistance() < 0.10; // Threshold for wall proximity
    }
    
    public void setJunction(boolean val) {
    	this.atJunction = val;
    }
    
    @Override
    public void action() {
    	pilot.stop();
        atJunction = true;
        sharedState.setWallDetected(true);
        nodeManager.triggerNodePlacement();
        
        // Signal that it's time to re-evaluate direction and potentially move forward
        //sharedState.setShouldMoveForward(true);
        atJunction = false;
        sharedState.setShouldMoveForward(false); // Reset the move forward flag
       
        while (!suppressed) {
            Thread.yield(); // Wait for this behavior to be suppressed
        }
    }
    @Override
    public void suppress() {
        suppressed = true;
        sharedState.setWallDetected(false);
    }
}