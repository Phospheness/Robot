package WallE;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.subsumption.*;

public class emergRobotStop implements Behavior {
	
    private boolean suppressed = false;
    
	public boolean takeControl() {
        return Button.ESCAPE.isDown();
    }
	
	public void suppress() {
	        suppressed = true;
	}
	
    public void action() {
        suppressed = false;
        Motor.A.stop(); //Wheel1
        Motor.B.stop(); //wheel2
        Motor.C.stop(); // rotate_head
        Motor.D.stop(); // rescue_claws
        }	
}
