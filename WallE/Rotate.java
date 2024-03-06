package walle;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Rotate {
	private static EV3MediumRegulatedMotor spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private static boolean isRotating = false;
	
	public void rotateSensor(int degrees, int delay,int speed) {
		if (!isRotating) {
			isRotating = true;
			spinMotor.setSpeed(speed);
			spinMotor.forward();
			while (spinMotor.getTachoCount() < degrees) {
				Thread.yield();
			}
			spinMotor.stop();
			spinMotor.resetTachoCount();
	
			try {
				Thread.sleep(delay);
			} catch (InterruptedException error) {
				error.printStackTrace();
			}
			
			spinMotor.backward();
			while (spinMotor.getTachoCount() > -degrees) {
				Thread.yield();
			}
			spinMotor.stop();
			spinMotor.resetTachoCount();
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException error) {
				error.printStackTrace();
			} 
		} else {
			LCD.drawString("Motor is already rotating", 2, 2);
		}	
	}
	
	public void stopRotate() {
		if (isRotating) {
			isRotating = false;
			spinMotor.stop();
		} else {
			LCD.drawString("Motor is not rotating", 2, 2);
		}
	}
	
	public boolean getState() {
		return isRotating;
	}
}
