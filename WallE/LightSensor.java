package WallE;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class LightSensor {
	
	private float[] samples = new float[3]; 
	private EV3ColorSensor coloursensor = new EV3ColorSensor(SensorPort.S1);
	private float greenValue = 0.0f;
	private float GREEN;
	private float RED;
	private float BLUE;
	

	public void fetchSamples() {
		coloursensor.getRGBMode().fetchSample(samples, 0);
		GREEN = samples[1];
		RED = samples[0];
		BLUE = samples[2];
	}

	public boolean getTargetFound() {
		if( GREEN > RED && GREEN > BLUE) {
			greenValue = samples[1];
			LCD.clear(); 
			LCD.drawString("Green Value: " + greenValue, 1, 1);
			return greenValue > 0.5;
		}
		
	}
}
