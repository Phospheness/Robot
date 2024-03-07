package walle;

import lejos.hardware.Button;

public class TesterClass {
    public void testRotate() {
        Rotate rotate = new Rotate();
        UVSensor uvSensor = new UVSensor();

        // Start the rotation in a separate thread
        new Thread(() -> rotate.rotateSensor(50, 90)).start();

        while (Button.ENTER.isUp()) {
            // Check for other events or conditions
            float distance = uvSensor.getDistance();
            if (distance < 0.5f) {
                // Do something
            } else if (distance >= 0.5f && distance < 1.0f) {
                // Do something else
            }
        }

        // Stop the rotation
        //rotate.stopRotate();
    }

    public static void main(String[] args) {
        TesterClass test = new TesterClass();
        test.testRotate();
    }
}
