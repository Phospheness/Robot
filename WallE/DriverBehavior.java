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
 

public class DriverBehavior implements Behavior {
	public boolean isRotating = false;
	public HeadMotor headMotor = new HeadMotor();
	public BaseRegulatedMotor mRight;
	public BaseRegulatedMotor mLeft;
	public Wheel wR;
	public Wheel wL;
	public Wheel[] wheels;
	public MovePilot pilot;
	public Chassis chassis;
	public DFS dfs;
	private boolean suppressed = false;
	
	
	public DriverBehavior(DFS dfs) {
		this.mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		this.mLeft = new EV3LargeRegulatedMotor(MotorPort.C);
		this.wR=  WheeledChassis.modelWheel(mLeft,60).offset(29);
		this.wL=  WheeledChassis.modelWheel(mRight,60).offset(-29);;
		this.wheels = new Wheel[] {wR, wL};
		this.chassis = new WheeledChassis ((new Wheel[] {wR, wL}), WheeledChassis.TYPE_DIFFERENTIAL);
		this.dfs = dfs;
		this.pilot = new MovePilot(chassis);
				
				
	}

	public boolean takeControl() {
		//return dfs.getNeedToMove();
		return true;
	}

	public void action() {
		if (isRotating == false) {
			headMotor.rotateSensor(500, 90);
			isRotating = true;
		} else {
			LCD.drawString("It's already rotating", 0, 0);
			Delay.msDelay(500);
			LCD.clear();
		}
//PLACEHOLDER UNTIL FIGURE OUT HOW TO FACE COMPASS DIRECTION
		switch (dfs.getNextDirection()) {
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
		}

		// Missing node detector class
		// detect whether the robot has reached a node
		//if (NodeManager.detectNode()){ //or get nextnodearrived from dfs.. not sure yet
		//	stop();
		//}
	}

	public void suppress() {
		suppressed = true;
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