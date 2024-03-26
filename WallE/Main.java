package WallE;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.subsumption.*;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.hardware.Button;

public class Main {

	//pilot
	public BaseRegulatedMotor mRight;
	public BaseRegulatedMotor mLeft;
	public Wheel wR;
	public Wheel wL;
	public Wheel[] wheels;
	public MovePilot pilot;
	public Chassis chassis;

	private DFS dfs;
	private HeadMotor headMotor;
	private NodeManager nodeManager;
	private DriverBehavior driverBehavior;
	
	
	
	private Rescue rescueBehavior;
	private BatteryLevel batteryLevelBehavior;
	private emergRobotStop emergencyStopBehavior;

    
    public Main() {

    	this.mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		this.mLeft = new EV3LargeRegulatedMotor(MotorPort.C);
		this.wR=  WheeledChassis.modelWheel(mLeft,60).offset(29);
		this.wL=  WheeledChassis.modelWheel(mRight,60).offset(-29);;
		this.wheels = new Wheel[] {wR, wL};
		this.chassis = new WheeledChassis ((new Wheel[] {wR, wL}), WheeledChassis.TYPE_DIFFERENTIAL);
		this.pilot = new MovePilot(chassis);
		
    	this.dfs = new DFS();
    	this.headMotor = new HeadMotor(dfs);
    	this.nodeManager = new NodeManager(dfs, headMotor, pilot);
    	this.driverBehavior = new DriverBehavior(dfs, headMotor, nodeManager, pilot);

    	this.rescueBehavior = new Rescue(this.dfs, this.pilot);
    	this.batteryLevelBehavior = new BatteryLevel(6.5f);
    	this.emergencyStopBehavior = new emergRobotStop();
        
    }
    
    
    public static void main(String [] args) {
        LCD.drawString("Welcome!", 0, 0);
        LCD.drawString("By Jae, Dhara", 0, 1);
        LCD.drawString("Ibrahim and MB", 0, 2);
        LCD.drawString("0.9.1-beta", 0, 3);
        Button.ENTER.waitForPressAndRelease();
        LCD.clear();
        
        
        Main main = new Main();
        main.run();
        
    }

    public void startBehaviours() {
        Behavior [] bArray = {driverBehavior,
        							rescueBehavior,
        						batteryLevelBehavior, 
        					  emergencyStopBehavior};
        Arbitrator arby = new Arbitrator(bArray);
        arby.go(); // might have to change to .go()
    }
    
    public void run() {
        
        //create a node and add it to the maze
        Node startNode = nodeManager.createNode();
        dfs.addNode(startNode);
        
        LCD.drawString("startnode :"+String.valueOf(startNode.getDirections().get(0)), 0, 4);
        //start the DFS algorithm
        dfs.traverse(startNode);


        //run the behaviours
        startBehaviours();
        
        LCD.drawString("startnode :"+String.valueOf(startNode.getDirections().get(0)), 0, 4);

     
    }

}
