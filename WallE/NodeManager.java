
import java.util.ArrayList;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;

public class NodeManager {

	private PoseProvider poseP;
	private DFS dfs;
    	private Navigator navigator;
	HeadMotor headMotor = new HeadMotor();

	public NodeManager(DFS dfs, MovePilot driverPilot, Navigator navigator) {	
		this.poseP = new OdometryPoseProvider(driverPilot);
		this.dfs = dfs;
		this.navigator = navigator;
	}



	public Node createNode() {
		Pose pose;
		pose = navigator.getPoseProvider().getPose();
		float x = poseP.getPose().getX();
		float y = poseP.getPose().getY();

		//need to call the sensors to get the directions that are available from this node 
		ArrayList<Direction> directions = headMotor.checkAvailableDirections();

		Node curNode = new Node(x,y,directions);
		
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

