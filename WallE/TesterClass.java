package walle;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.*;

public class Rotate {
	private static EV3MediumRegulatedMotor spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private static boolean isRotating = false;

	public void rotateSensor(int delay, int speed) {
	    if (isRotating == false) {
	        isRotating = true;
	        String direction = "left";
	        while (isRotating == true) {
	            // Rotate left
	            rotateDirection("left", 90, speed);
	            applyDelay(delay);
	            
	            // Rotate right to original position
	            rotateDirection("right", 180, speed);
	            applyDelay(delay);
	            
	            // Toggle direction
	            direction = (direction.equalsIgnoreCase("left")) ? "right" : "left";
	        }
	    } else {
	        LCD.drawString("Motor is already rotating", 2, 2);
	    }   
	}

	public void rotateDirection(String direction, int degrees, int speed) {
	    if (direction.equalsIgnoreCase("left")) {
	        spinMotor.rotate(degrees, true); 
	    } else if (direction.equalsIgnoreCase("right")) {
	        spinMotor.rotate(-degrees, true); 
	    } else {
	        LCD.drawString("Not a valid direction", 0, 0);
	    }
	}
	
	
	public void applyDelay(int delay) {
        Delay.msDelay(delay);
    }
	
	public void stopRotate() {
		if (isRotating == true) {
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
