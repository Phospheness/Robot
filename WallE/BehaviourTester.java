import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class BehaviourTester {
    public static void main(String[] args) {
        RotateBehaviour rotateBehavior = new RotateBehaviour();
        // Set the flag to true to start rotation
        rotateBehavior.isRotating = true;
        
        // Action is now self-contained and should not be repeated unintentionally
        while (!Button.ENTER.isDown()) {
        	if (rotateBehavior.isRotating == true) {
        		LCD.drawString("i am alive and i will SPIN", 0, 0);
        		rotateBehavior.action(); // Initiates rotation sequence once
        	}
        }
        
        // Ensure the motor stops when the program ends
        rotateBehavior.stopRotate();
    }
}
