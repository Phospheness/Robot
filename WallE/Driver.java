package WallE;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class Driver extends Behavior {

	public BaseRegulatedMotor mRight;
	public BaseRegulatedMotor mLeft;
	public Wheel wR;
	public Wheel wL;
	public Wheel[] wheels;
	public MovePilot pilot;
	public Chassis chassis;
	public DFS dfs;
	private boolean suppressed = false;
	
	
	public Driver(DFS dfs) {
		this.mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		this.mLeft = new EV3LargeRegulatedMotor(MotorPort.B);
		this.wR=  WheeledChassis.modelWheel(mLeft,60).offset(29);
		this.wL=  WheeledChassis.modelWheel(mRight,60).offset(-29);;
		this.wheels= new Wheel[] {wR,wL};
		this.pilot = new MovePilot(chassis);
		this.dfs = dfs;
				
				
	}

	public boolean takeControl() {
		return dfs.getNeedToMove();
	}

	public void action() {

//PLACEHOLDER UNTIL FIGURE OUT HOW TO FACE COMPASS DIRECTION
		switch (dfs.getNextDirection()) {
		case Direction.NORTH:
			forward();
			break;
		case Direction.EAST:
			turnRight();
			break;
		case Direction.SOUTH:
			turnRight();
			turnRight();
			break;

		case Direction.WEST:
			turnLeft();
			break;
		}

		// Missing node detector class
		// detect whether the robot has reached a node
		if (NodeDetector.arrivedAtNode()){ //or get nextnodearrived from dfs.. not sure yet
			stop();
		}
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
		return pilot;
	}
}
