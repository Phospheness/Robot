package WallE;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.MovePilot;
 

public class DriverBehavior implements Behavior {
	public boolean isRotating = false;
	public DFS dfs;
	private boolean suppressed = false;
	private HeadMotor headMotor;
	private NodeManager nodeManager;
	private MovePilot pilot;
	
	
	public DriverBehavior(DFS dfs, HeadMotor mainHeadMotor, NodeManager mainNodeManager, MovePilot mainPilot) {
		this.dfs = dfs;
		this.headMotor = mainHeadMotor;
		this.nodeManager = mainNodeManager;
		this.pilot = mainPilot;
				
				
	}

	public boolean takeControl() {
		return true;
	}

	public void action() {
		Sound.beep();
		try {
		
			//PLACEHOLDER UNTIL FIGURE OUT HOW TO FACE COMPASS DIRECTION
			switch (this.dfs.getNextDirection()) {
			case NORTH:
				forward();
				break;
			case EAST:
				turnRight();
				break;
			case SOUTH:
				turnRight();
				turnRight();
				break;
	
			case WEST:
				turnLeft();
				break;
			default:
				forward();
				break;
			}
		}
		catch(NullPointerException e) {
			LCD.drawString("driver error: "+ e.getMessage(), 0, 3);
		}
		
		//start looking for a junction while moving
		while(dfs.getNeedToMove()) {
			headMotor.rotateSensor();
		}
			
		dfs.setNeedToMove(false);
		nodeArrived();
		
		
	}

	public void suppress() {
		suppressed = true;
	}
	
	public void nodeArrived() {
		
		//stop driver from driving
		stop();
		
		//create a node at the junction
		Node newNode = nodeManager.createNode();
		dfs.addNode(newNode);
		
		//flag dfs to continue
		dfs.setNextNodeArrived(true);
	}



	
	public void forward() {
		pilot.forward();
	}
	public void turnLeft() {
		pilot.rotate(-90);
	}
	public void turnRight() {
		pilot.rotate(90);
		}
	public void stop() {
		pilot.stop();
	}

	public MovePilot getPilot() {
		return this.pilot;
	}
}