package WallE;

import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.*;

public class Main {


    //instance of all the classes
	private DFS dfs = new DFS();
   
    //private HeadMotor rotateBehavior = new HeadMotor();
    private Rescue rescueBehavior = new Rescue(dfs);
    private BatteryLevel batteryLevelBehavior = new BatteryLevel(6.5f);
    private emergRobotStop emergencyStopBehavior = new emergRobotStop();
    private DriverBehavior driverBehavior = new DriverBehavior(dfs);
    private NodeManager nodeManager = new NodeManager(dfs, driverBehavior.getPilot());
		  

    
    public Main() {
    	
        nodeManager = new NodeManager(dfs,((DriverBehavior) driverBehavior).getPilot());
        
    }
    
    
    public static void main(String [] args) {
        LCD.drawString("v1.1", 0, 0);
        Main main = new Main();
        main.run();
        
    }

    public void startBehaviours() {
        Behavior [] bArray = {rescueBehavior, batteryLevelBehavior, 
        					  driverBehavior, emergencyStopBehavior};
        Arbitrator arby = new Arbitrator(bArray);
        arby.go(); // might have to change to .go()
    }
    
    public void run() {
        
        //create a node and add it to the maze
        Node startNode = nodeManager.createNode();
        dfs.addNode(startNode);

        //run the behaviours
        startBehaviours();

        //start the DFS algorithm
        dfs.traverse(startNode);

    }

}
