package week2;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class BatteryLevel implements Behavior {
	
	private static float BATTERY_LOW;
	public boolean suppressed = false;
	
	public BatteryLevel(float volts) {
	      BATTERY_LOW = volts;
	}
	
	public boolean takeControl() {
		return Battery.getVoltage() < BATTERY_LOW;
	}
	
	public void suppress() {
		suppressed = true;
	}
	
	public void action() {
		suppressed = false;
		Sound.beep();
		LCD.clear();
		LCD.drawString("Battery is low" + Battery.getVoltage(), 0, 0);
		Sound.beep();
	}
	
}
