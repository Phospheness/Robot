import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class RotateBehaviour implements Behavior {
    private EV3MediumRegulatedMotor spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
    private boolean isRotating = false;

    @Override
    public boolean takeControl() {
        // Define the condition to take control
        return isRotating;
    }

    @Override
    public void action() {
        // Implement the behavior's action
        rotateSensor(500, 50); // Adjust the delay and speed as needed
    }

    @Override
    public void suppress() {
        // Implement the suppression of the behavior
        stopRotate();
    }

    public void rotateSensor(int delay, int speed) {
        String direction = "left";

        while (isRotating) {
            // Rotate left
            rotateDirection("left", 90, speed);
            Delay.msDelay(delay);

            // Rotate right to original position
            rotateDirection("right", 180, speed);
            Delay.msDelay(delay);

            // Toggle direction
            direction = (direction.equalsIgnoreCase("left")) ? "right" : "left";
        }
    }

    public void rotateDirection(String direction, int degrees, int speed) {
        spinMotor.setSpeed(speed); // Set the motor speed.
        if (direction.equalsIgnoreCase("left")) {
            spinMotor.rotate(degrees, true); // Start rotation and return immediately.
        } else if (direction.equalsIgnoreCase("right")) {
            spinMotor.rotate(-degrees, true); // Start rotation and return immediately.
        } else {
            LCD.drawString("Not a valid direction", 0, 0);
            return; // Exit if direction is invalid.
        }
        spinMotor.waitComplete(); // Wait for the rotation to complete.
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
