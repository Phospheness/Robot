package walle;

import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

public class UVSensor {
	private static EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S1);
	
	public float getDistance() {
		SampleProvider Sample = usSensor.getDistanceMode();
		float[] Samples = new float[1];
		Sample.fetchSample(Samples, 0);
		return Samples[0];
	}
	
	public void closeSensors() {
		usSensor.close();
	}
}


