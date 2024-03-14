package walle;

import lejos.hardware.Button;

public class RobotBehaviour {
    public static void main(String[] args) {
        RotateBehaviour rotateBehavior = new RotateBehaviour();
        // Set the flag to true to start rotation
        rotateBehavior.isRotating = true;
        
        // Action is now self-contained and should not be repeated unintentionally
        while (!Button.ENTER.isDown()) {
            if (!rotateBehavior.getState()) {
                rotateBehavior.action(); // Initiates rotation sequence once
            }
        }
        
        // Ensure the motor stops when the program ends
        rotateBehavior.stopRotate();
    }
}
