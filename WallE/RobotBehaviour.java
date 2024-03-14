package WallE;

import lejos.hardware.Button;

public class RobotBehaviour {
    public static void main(String[] args) {
        RotateBehaviour rotateBehavior = new RotateBehaviour();
        rotateBehavior.isRotating = true; // Set the flag to true to start rotation
        
        boolean actionCalled = false; // Flag to track whether action has been called
        
        // Loop until the center button is pressed
        while (!Button.ENTER.isDown()) {
            if (!actionCalled) {
                rotateBehavior.action(); // Call the action method to rotate
                actionCalled = true; // Set the flag to true after action is called
            }
        }
        
        rotateBehavior.stopRotate(); // Stop the rotation when the center button is pressed
    }
}
