package WallE;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.*;

public class DeadEnd implements Behavior {
  
	private EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S3);
	private boolean suppressed = false;
	private SampleProvider sampleProvider = touch.getTouchMode();
	private float touchValue = 0;
	private DriverBehavior driver;
  
	public DeadEnd(DriverBehavior mainDriver) {
		this.driver = mainDriver;
	}
  
    
	public boolean takeControl() {
	    return touchValue == 1;
	}
	
	public void suppress() {
	        suppressed = true;
	}
	
	public void action() {
		driver.nodeArrived();
	}
	  
	
	public void isSensed() {
		float[] sample = new float[sampleProvider.sampleSize()];
	    sampleProvider.fetchSample(sample, 0);
	    touchValue = sample[0];
	}	
}

// should stop robot if escape button is pressed manually or touch sensor is pressed
