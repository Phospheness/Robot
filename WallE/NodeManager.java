


import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import java.nio.file.Path;

public class NodeManager {

	private PoseProvider poseP;
	private Navigator navigator;
	private DFS dfs;
	private Node previousNode;

	Driver driver;
	MovePilot pilot;

	public NodeManager() {
		
		 pilot = driver.getPilot();
		this.poseP = new OdometryPoseProvider(driver.getPilot());
		this.navigator = new Navigator (driver.getPilot(),poseP);
		this.dfs = new DFS();
		this.previousNode = null;
		
	}

	/* The getCur method retrieves the position at the coordinates x and y
	 It then creates a node and adds this position values int it. 
	 (I do not know how to implement the direction variable in the constructor) 
	 The created node is then added to the dfs with dfs.addNode(curNode);
	 */
public void getCur() {
		
		 float x = poseP.getPose().getX();
	     float y = poseP.getPose().getY();
	     
	     Node curNode = new Node(x,y,);
	     
	     dfs.addNode(curNode);
	     
	/* Code below is used for connecting nodes. 
	 * The previous nodee points at the next node and then the current node becomes the previous node 
	 * */ 
	 	if(previousNode != null) {
	 		previousNode.setNext(curNode);
	 	}
	 	previousNode = curNode;
	}


// Note there are a lot of things not being used in my code such as the driver,pilot and navigator but i just keep it to not get undefined errors.

}
