package walletest;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class LightSensor {
	
	private float[] samples = new float[3]; 
	private EV3ColorSensor coloursensor = new EV3ColorSensor(SensorPort.S2);
	private float greenValue = 0.0f;
	private float GREEN;
	private float RED;
	private float BLUE;
	
	
	

	public void fetchSamples() {
		coloursensor.getRedMode().fetchSample(samples, 0);
	}

	public boolean getTargetFound() {
		fetchSamples();
		return samples[0] > 0.4;
		
	}
}
