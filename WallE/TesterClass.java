package walle;                                       

import lejos.hardware.Button;

public class TesterClass {
	
	public void testRotate(){
		Rotate Rotate = new Rotate();
		UVSensor uvSensor = new UVSensor();
		while (Button.ENTER.isDown()) {
			Rotate.stopRotate();                 //move to emergency  stop behaviour
		}
		Rotate.rotateSensor(50, 90);
		while (Rotate.getState() == true) {
			float Distance = uvSensor.getDistance();
			if (Distance < 0.5f) {
				;
			} else if (Distance >= 0.5f && Distance < 1.0f) {
				;
			}
		}
	}

	public static void main(String[] args) {
		TesterClass test = new TesterClass();
		test.testRotate();
	}

}
