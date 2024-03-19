

import java.util.ArrayList;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;


public class NodeManager {

	private PoseProvider poseP;
	private DFS dfs;
   

	public NodeManager(DFS dfs, Driver driver) {
		
		MovePilot pilot = driver.getPilot();
		this.poseP = new OdometryPoseProvider(pilot);
		this.dfs = dfs;
		
	}

	/* The getCur method retrieves the position at the coordinates x and y
	 It then creates a node and adds this position values int it. 
	 (I do not know how to implement the direction variable in the constructor) 
	 The created node is then added to the dfs with dfs.addNode(curNode);
	 */

	public void createNode() {
		
		float x = poseP.getPose().getX();
		float y = poseP.getPose().getY();

		//need to call the sensors to get the directions that are available from this node 
		ArrayList<Direction> directions = new ArrayList<Direction>();
		Node curNode = new Node(x,y, directions);
		
		dfs.addNode(curNode);

	}

	public void detectNode(){
		//this method should get the next node that the robot should go to
		//it should track its current position and compare it to the node it needs to get to (allow for it to be slightly off)
		//once at the node it should flag dfs to notify it that it has arrived at the node
	}


// Note there are a lot of things not being used in my code such as the driver,pilot and navigator but i just keep it to not get undefined errors.

}
