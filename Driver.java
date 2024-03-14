import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class Driver {

	public BaseRegulatedMotor mRight;
	public BaseRegulatedMotor mLeft;
	public Wheel wR;
	public Wheel wL;
	public Wheel[] wheels;
	public MovePilot pilot;
	public Chassis chassis;
	
	
public Driver() {
		this.mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		this.mLeft = new EV3LargeRegulatedMotor(MotorPort.B);
		this.wR=  WheeledChassis.modelWheel(mLeft,60).offset(29);
		this.wL=  WheeledChassis.modelWheel(mRight,60).offset(-29);;
		this.wheels= new Wheel[] {wR,wL};
		this.pilot = new MovePilot(chassis);
				
				
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
}
