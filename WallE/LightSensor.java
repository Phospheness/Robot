package week2;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class LightSensor {
	
	static float[] samples = new float[3]; 
	static EV3ColorSensor coloursensor = new EV3ColorSensor(SensorPort.S1);
	static float greenValue = 0.0f;
	public static float FINAL_SAMPLE;
	public static float GREEN;
	public static float RED;
	public static float BLUE;
	
	public void greenColour() {	
	
		while (true) {
			coloursensor.getRGBMode().fetchSample(samples , 0);
			RED = samples[0];
			GREEN = samples[1];
			BLUE = samples[2];
				if( GREEN > RED && GREEN > BLUE) {
					greenValue = samples[1];
					LCD.clear(); 
					LCD.drawString("Green Value: " + greenValue, 1, 1);
				}
		}
	}
}
