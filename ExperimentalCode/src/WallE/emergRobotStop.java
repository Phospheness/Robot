package WallE;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.*;

public class emergRobotStop implements Behavior {
  
  private boolean suppressed = false;
  private float touchValue = 0;
  private EV3TouchSensor touchSensor1;
  private EV3TouchSensor touchSensor2;
  
    
	public boolean takeControl() {
		float[] sample1 = new float[touchSensor1.sampleSize()];
        float[] sample2 = new float[touchSensor2.sampleSize()];
        touchSensor1.fetchSample(sample1, 0);
        touchSensor2.fetchSample(sample2, 0);
        return Button.ESCAPE.isDown() || sample1[0] > 0 || sample2[0] > 0;
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

// should stop robot if escape button is pressed manually or touch sensor is pressed
