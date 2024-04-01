package WallE;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.Button;
import java.util.*;
public class Main {
    // Motor and sensor declarations
    private EV3MediumRegulatedMotor spinMotor;
    private BaseRegulatedMotor mRight, mLeft;
    private MovePilot pilot;
    private UltrasonicSensor ultrasonicSensorService; // This should be the service class
    SharedState sharedState = new SharedState();

    // Behavioral components
    private DFS dfs;
    private HeadMotor headMotor;
    private NodeManager nodeManager;
    private DriverBehavior driverBehavior;
    private checkForWall checkForWall; // Ensure class names follow Java naming conventions
    private Rescue rescueBehavior;
    private BatteryLevel batteryLevelBehavior;
    private emergRobotStop emergencyStopBehavior; // Assuming naming convention correction

    public Main() {
        // Motors and sensors initialization
        this.spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        this.mRight = new EV3LargeRegulatedMotor(MotorPort.B);
        this.mLeft = new EV3LargeRegulatedMotor(MotorPort.C);
        Wheel wR = WheeledChassis.modelWheel(mLeft, 60).offset(29);
        Wheel wL = WheeledChassis.modelWheel(mRight, 60).offset(-29);
        this.pilot = new MovePilot(new WheeledChassis(new Wheel[]{wR, wL}, WheeledChassis.TYPE_DIFFERENTIAL));
        // Initialize the ultrasonic sensor service
        EV3UltrasonicSensor ev3UltrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
        this.ultrasonicSensorService = new UltrasonicSensor(ev3UltrasonicSensor); // Assuming this wraps the EV3UltrasonicSensor

        // Behavior and control component initialization
        this.dfs = new DFS();
        this.headMotor = new HeadMotor(spinMotor, ultrasonicSensorService); 
        this.nodeManager = new NodeManager(dfs, headMotor, pilot, sharedState);
        this.driverBehavior = new DriverBehavior(dfs, nodeManager, pilot, Direction.NORTH, sharedState);
        this.checkForWall = new checkForWall(ultrasonicSensorService, pilot, headMotor, nodeManager, dfs,  sharedState);
        this.rescueBehavior = new Rescue(dfs, pilot);
        this.batteryLevelBehavior = new BatteryLevel(1.0f);
        this.emergencyStopBehavior = new emergRobotStop();
        nodeManager.setDriverBehavior(driverBehavior);
        driverBehavior.setNodeManager(nodeManager);

    
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
        Behavior [] bArray = {checkForWall,
        					nodeManager,
        					rescueBehavior,
        					driverBehavior,
        					batteryLevelBehavior, 
        					emergencyStopBehavior};
        Arbitrator arby = new Arbitrator(bArray);
        arby.go(); // might have to change to .go()
    }
    
    public void run() {
        
        //create a node and add it to the maze
    	ArrayList<Direction> list = new ArrayList<Direction>();
    	list.add(Direction.NORTH);
        Node startNode = nodeManager.createNode(list);
        dfs.addNode(startNode);
        
        //LCD.drawString("startnode :"+String.valueOf(startNode.getDirections().get(0)), 0, 4);
        //start the DFS algorithm
        //dfs.traverse(startNode);


        //run the behaviours
        startBehaviours();
        
        //LCD.drawString("startnode :"+String.valueOf(startNode.getDirections().get(0)), 0, 4);

     
    }

}
