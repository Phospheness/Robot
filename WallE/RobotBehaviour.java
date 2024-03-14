package week2;
import lejos.nxt.SensorPort;
import lejos.hardware.port.MotorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
public class RobotBehaviour {
	
	   public static void main(String [] args) {
	      Behavior b1 = new RotateBehaviour(MotorPort.A);
	      Behavior b2 = new Rescue();
	      Behavior b3 = new BatteryLevel(6.5f);
	      Behavior b4 = new emergRobotStop();
	      Behavior [] bArray = {b1, b2, b3, b4};
	      Arbitrator arby = new Arbitrator(bArray);
	      arby.start();
	   }
	}
}
