package WallE;

import java.util.ArrayList;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;
//This class creates and manages nodes for navigation.
public class NodeManager implements Behavior {

    private PoseProvider poseP;
    private DFS dfs;
    private HeadMotor headMotor;
    public MovePilot pilot;
    private volatile boolean suppressed = false;
    private volatile boolean nodePlacementTriggered = false;
    private DriverBehavior driverBehavior;
    private SharedState ss;

	// Initializes the parameters.
    public NodeManager(DFS dfs, HeadMotor headMotor, MovePilot pilot, SharedState ss) {    
        this.dfs = dfs;
        this.pilot = pilot;
        this.poseP = new OdometryPoseProvider(pilot);
        this.headMotor = headMotor;
        this.ss = ss;
    }

    @Override
    public boolean takeControl() {
        // Determine conditions under which this behavior should take control
        return this.nodePlacementTriggered;
    }
    
    @Override
    public void action() {
        if(nodePlacementTriggered) {
        	ArrayList<Direction> directions = headMotor.getAvailableDirections();//look around for directions
            Node newNode = createNode(directions);
            dfs.addNode(newNode);
            
            Direction dir = newNode.getDirections().get(0);
            
            
            
            this.nodePlacementTriggered = false; // Reset the trigger
            driverBehavior.setNextDirection(dir);
            ss.setShouldMoveForward(true);
        }
        
        
    }
 
    public void setDriverBehavior(DriverBehavior driverBehavior) {
        this.driverBehavior = driverBehavior;
    }
    @Override
    public void suppress() {
        suppressed = true;
    }
    

    public void triggerNodePlacement() {
        this.nodePlacementTriggered = true;
    }
    // Creates nodes determined by directions available.
    public Node createNode(ArrayList<Direction> directionList) {
        float x = poseP.getPose().getX();
        float y = poseP.getPose().getY();
        Node curNode = new Node(x, y, directionList);
        return curNode;
    }

	
	//it should track its current position and compare it to the node it needs to get to (allow for it to be slightly off): JAY
	public boolean checkIfOnNode(Pose p, Node targetNode) {
	
		// Get current coordinates
		float robotX = p.getX();
		float robotY = p.getY();

		// Get target coordinates
		float targetX = targetNode.getX();
		float targetY = targetNode.getY();

		// The distance that would be considered as the node being reached
		float threshold = 0.1f; 

		//Caculates the distance between robot current position and the position to be reached
		float distance = (float) Math.sqrt(Math.pow(targetX - robotX, 2) + Math.pow(targetY - robotY, 2));

		
		return distance <= threshold;
    
	}




	public boolean checkArrived(Node targetNode) {
		
		// Get the current pose of the robot
		Pose pose = poseP.getPose();

		// Check if the robot is on the target node
		if (checkIfOnNode(pose, targetNode)) {
			dfs.setNextNodeArrived(true);
			return true;
		}
		return false;
		
	}
}


// Note there are a lot of things not being used in my code such as the driver,pilot and navigator but i just keep it to not get undefined errors.

