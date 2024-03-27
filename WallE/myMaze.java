package myMaze;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

public class myMaze {
    
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    public static final int STOP_DISTANCE = 60;
    public static final int SLOW_DISTANCE = STOP_DISTANCE + 30;
    public static final int FAST_FORWARD_SPEED = -50;
    public static final int SLOW_FORWARD_SPEED = -20;
    public static final int TURN_SPEED = 30;
    public static final int TURN_LEFT_DELAY = 1130;
    public static final int TURN_RIGHT_DELAY = 1180;
    
    private EV3UltrasonicSensor ultrasonicSensor;
    private RegulatedMotor leftMotor;
    private RegulatedMotor rightMotor;

    private boolean isMoving;
    private int frontDistanceCm;
    private int leftDistanceCm;
    private int rightDistanceCm;
    private int light;
    private int testing;
    private TextLCD lcd;

    public myMaze() {
        ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        lcd = LocalEV3.get().getTextLCD();
    }

    public void writeLcd(String lcdStr) {
    	 lcd.clear(); 
         lcd.drawString(lcdStr, 0, 0);
    }

    public void moveForward() {
        leftMotor.setSpeed(500); // Example speed setting
        rightMotor.setSpeed(500);
        leftMotor.forward();
        rightMotor.forward();
    }

    public void stopMotors() {
        leftMotor.stop();
        rightMotor.stop();
    }

    public int getFrontDistance() {
        float[] sample = new float[ultrasonicSensor.sampleSize()];
        ultrasonicSensor.fetchSample(sample, 0);
        frontDistanceCm = (int) (sample[0] * 100); 
        light = 25;  
        writeLcd("F: " + frontDistanceCm + " L: " + leftDistanceCm + " R: " + rightDistanceCm + " I: " + light);
        return frontDistanceCm;
    }
    
    public void getLeftRightDistance() {
        float[] leftSample = new float[ultrasonicSensor.sampleSize()];
        ultrasonicSensor.fetchSample(leftSample, 0);
        leftDistanceCm = (int) (leftSample[0] * 100);
        
        float[] rightSample = new float[ultrasonicSensor.sampleSize()];
        ultrasonicSensor.fetchSample(rightSample, 0);
        rightDistanceCm = (int) (rightSample[0] * 100); 
        
        light = 25;  // Placeholder for light sensor reading
        writeLcd("F: " + frontDistanceCm + " L: " + leftDistanceCm + " R: " + rightDistanceCm + " I: " + light);
    }


    public void navigate() {
        // Main navigation loop
        writeLcd("Forward");
        getFrontDistance();

        while (frontDistanceCm == 0) {
            getFrontDistance();
        }

        if (testing == 1) {
            turnRight();  // For testing purposes
            return;
        }

        isMoving = true;
        moveForward();

        while (true) {
            getFrontDistance();

            while (frontDistanceCm > SLOW_DISTANCE) {
                if (light < 5) {
                    stopMotors();
                    endProgram();
                }
                getFrontDistance();
            }

            slowDown();

            while (frontDistanceCm > STOP_DISTANCE) {
                getFrontDistance();
            }

            stopMotors();
            getLeftRightDistance();

            if (leftDistanceCm > rightDistanceCm) {
                turnLeft();
            } else {
                turnRight();
            }

            moveForward();
        }
    }

    public void turnLeft() {
    	leftMotor.rotate(-90);
    }

    public void turnRight() {
    	rightMotor.rotate(90);
    }

    public void slowDown() {
    	leftMotor.setSpeed(SLOW_FORWARD_SPEED); 
        rightMotor.setSpeed(SLOW_FORWARD_SPEED);
    }

    public void endProgram() {
        leftMotor.stop();
        rightMotor.stop();
    }
}
