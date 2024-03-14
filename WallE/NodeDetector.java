import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;

public class NodeDetector  {
	
public PoseProvider poseP;
public Navigator navigator;

Driver driver = new Driver();

public NodeDetector() {
	MovePilot pilot = driver.getPilot();
	this.poseP = new OdometryPoseProvider(pilot);
	this.navigator = new Navigator (pilot, poseP);
}


}
