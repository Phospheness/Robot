package walle;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class RotateBehaviour implements Behavior {
    private EV3MediumRegulatedMotor spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
    public boolean isRotating = false;

    @Override
    public boolean takeControl() {
        return isRotating;
    }

    @Override
    public void action() {
        isRotating = true;
        rotateSensor(500, 50); // Call rotate with specific delay and speed
    }

    @Override
    public void suppress() {
        stopRotate();
    }

    public void rotateSensor(int delay, int speed) {
        // Ensure motor speed is set
        spinMotor.setSpeed(speed);

        // Rotate left
        rotateDirection("left", 90);
        Delay.msDelay(delay);

        // Reset to original position
        rotateDirection("right", 90);
        Delay.msDelay(delay);

        // Rotate right
        rotateDirection("right", 90);
        Delay.msDelay(delay);

        // Reset to original position
        rotateDirection("left", 90);
        Delay.msDelay(delay);
    }

    public void rotateDirection(String direction, int degrees) {
        if (direction.equalsIgnoreCase("left")) {
            spinMotor.rotate(degrees);
        } else if (direction.equalsIgnoreCase("right")) {
            spinMotor.rotate(-degrees);
        } else {
            LCD.drawString("Invalid direction", 0, 0);
        }
        spinMotor.waitComplete(); // Wait for rotation to complete
    }

    public void stopRotate() {
        isRotating = false;
        spinMotor.stop(true);
        spinMotor.waitComplete(); // Ensure motor stops completely
    }

    public boolean getState() {
        return isRotating;
    }
}
