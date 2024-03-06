package walle;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Rotate {
	private static EV3MediumRegulatedMotor spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
	private static boolean isRotating = false;

	public void rotateSensor(int delay, int speed) {
	    if (!isRotating) {
	        isRotating = true;
	        String direction = "left";
	        while (isRotating) {
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

	/*
	public void rotateSensor(int degrees, int delay, int speed) {
	    if (!isRotating) {
	        isRotating = true;
	        String direction = "left";
	        while (isRotating) {
	            rotateDirection(direction, degrees, speed); // Rotate in the current direction
	            applyDelay(delay); // Apply delay before changing direction
	            
	            // Toggle direction
	            direction = (direction.equalsIgnoreCase("left")) ? "right" : "left";
	            rotateDirection(direction, degrees, speed); //reset to original position
	        }
	    } else {
	        LCD.drawString("Motor is already rotating", 2, 2);
	    }   
	}
	*/


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
	    try {
	        Thread.sleep(delay);
	    } catch (InterruptedException error) {
	        error.printStackTrace();
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
