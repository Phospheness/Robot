package WallE;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.*;

public class emergRobotStop implements Behavior {
  
  static EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S3);
  private boolean suppressed = false;
  static SampleProvider sampleProvider = touch.getTouchMode();
  static float touchValue = 0;
    
	public boolean takeControl() {
        return Button.ESCAPE.isDown() || touchValue == 1;
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
    
	public void isSensed() {
		float[] sample = new float[sampleProvider.sampleSize()];
        sampleProvider.fetchSample(sample, 0);
        touchValue = sample[0];
	}	
}

// should stop robot if escape button is pressed manually or touch sensor is pressed
