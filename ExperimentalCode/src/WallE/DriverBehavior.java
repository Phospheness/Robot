package WallE;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

 
// Controls how the robot drives. Includes rotating to a direction, moving forward, what speed, and other variables.

public class DriverBehavior implements Behavior {
	private volatile boolean isMoving = false;
	public boolean isRotating = false;
	public DFS dfs;
	private boolean suppressed = false;
	private NodeManager nodeManager;
	private MovePilot pilot;
	private SharedState sharedState;
	private volatile Direction nextDirection;
	private Direction currentDirection; // Start facing North
	
	public void setNodeManager(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
    }
	
	public DriverBehavior(DFS dfs, NodeManager nodeManager, MovePilot mainPilot, Direction startingDirection, SharedState sharedState) {
		this.dfs = dfs;
		this.nodeManager = nodeManager;
		this.pilot = mainPilot;
		this.sharedState = sharedState;
		this.currentDirection = startingDirection;
		
		setMovementSpeed(50);
		setTurnRate(180);
	}
	
	public synchronized void setNextDirection(Direction direction) {
        if (!isMoving) { // Only update if no movement is in progress
            this.nextDirection = direction;
        }
        // Else, you might queue the update or handle it as needed
    }

    public void setMovementSpeed(double speed) {
        pilot.setLinearSpeed(speed);
    }

    // Method to set the angular turn rate
    public void setTurnRate(double rate) {
        pilot.setAngularSpeed(rate);
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction; // Use this method to set the starting direction if needed
    }
    
    public void rotateToDirection() {
        if (nextDirection == null || nextDirection == currentDirection) {
            // If no direction is set or already facing the correct direction, no rotation needed
            return;
        }
        
        int turns = (nextDirection.ordinal() - currentDirection.ordinal() + 4) % 4;
        // Determine the shortest path to the next direction
        if (turns == 1) {
            // Turn right 90 degrees
            turnRight();
        } else if (turns == 2) {
            // Turn 180 degrees, could be right or left twice
            turnRight();
            turnRight();
        } else if (turns == 3) {
            // Turn left 90 degrees
            turnLeft();
        }
        // Update the currentDirection to nextDirection
        currentDirection = nextDirection;
        
    }
	

    @Override
	public boolean takeControl() {
    	return true;
	}
	
	@Override
	public void action() {
		if (nextDirection != null) {
            rotateToDirection();
            Delay.msDelay(2500);
            nextDirection = null; // Reset after rotation
        }
		forward();
		
		//start moving is a direction

		//start checking if robot has hit a wall
		/*
	    while (!suppressed) {
	        if (sharedState.isWallDetected()) {
	        	pilot.stop();
	        	suppressed = true;
	        } 
	    }*/
	}
	

	public void suppress() {
		suppressed = true;
	}
	
	public void nodeArrived(Node node) {
		
		dfs.setNextNodeArrived(true);
		
	}



	
	public void forward() {
		pilot.forward();
	}
	private void turnLeft() {
		pilot.rotate(-180);
        switch (currentDirection) {
            case NORTH:
                currentDirection = Direction.WEST;
                break;
            case WEST:
                currentDirection = Direction.SOUTH;
                break;
            case SOUTH:
                currentDirection = Direction.EAST;
                break;
            case EAST:
                currentDirection = Direction.NORTH;
                break;
        }
    }

    private void turnRight() {
        // Rotate the robot right
        // Update current direction clockwise
    	pilot.rotate(180);
        switch (currentDirection) {
            case NORTH:
                currentDirection = Direction.EAST;
                break;
            case EAST:
                currentDirection = Direction.SOUTH;
                break;
            case SOUTH:
                currentDirection = Direction.WEST;
                break;
            case WEST:
                currentDirection = Direction.NORTH;
                break;
        }
    }
    
	public void stop() {
		pilot.stop();
	}

	public MovePilot getPilot() {
		return this.pilot;
	}
}
