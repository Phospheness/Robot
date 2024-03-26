package WallE;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import java.util.*;

public class HeadMotor {
	private static EV3MediumRegulatedMotor spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
    public boolean isRotating = false;
    private Direction currentDirection = Direction.NORTH;
    private UltraSonicSensor sensor = new UltraSonicSensor();
    private DFS dfs;
    private int delay = 50; //CHANGE
    private int speed = 100; //CHANGE
    
    public HeadMotor(DFS mainDfs) {
    	this.dfs = mainDfs;
    }



    public void rotateSensor() {
  
        switch (currentDirection) {
            case NORTH:
            	
                // Rotate to EAST
                rotateDirection(Direction.EAST, 90, speed);
                checkDistance();
                Delay.msDelay(delay);
                
                // Reset to NORTH
                rotateDirection(Direction.NORTH, 90, speed);
                Delay.msDelay(delay);
                
                // Rotate to WEST
                rotateDirection(Direction.WEST, 90, speed);
                checkDistance();
                Delay.msDelay(delay);
                
                // Reset to NORTH again
                rotateDirection(Direction.NORTH, 90, speed);
                break;
                
                
            case EAST:
                break;
            case WEST:
                break;
            case SOUTH:
            	break;
        }

        Delay.msDelay(delay); // Additional delay if needed
        // No need to toggle direction here since we're controlling it within the case
    }
    
     

    public void rotateDirection(Direction direction, int degrees, int speed) {         
  	  // Adjust rotation based on target direction         
      	if (direction == Direction.EAST) {             
      		spinMotor.setSpeed(speed);             
      		spinMotor.rotate(degrees, true);          
      	} else if (direction == Direction.NORTH) {             
      		spinMotor.setSpeed(speed);             
      		spinMotor.rotate(-degrees, true);          
      	} else {             
      		LCD.drawString("Not a valid direction", 0, 0);         
      	}          
      	currentDirection = direction;     
  		}      

    public void stopRotate() {         
    	if (isRotating) {             
    		isRotating = false;             
    		spinMotor.stop();         
    	} else {             
			  LCD.drawString("Motor is not rotating", 2, 2);         
    	}     
    }  
    
    public boolean checkDistance() {
    	boolean path = sensor.getDistance() > 30;//placeholder value, value is how far before it is considered there is no wall (a path)	
    	
    	if(path) {
    		dfs.setNeedToMove(false); //will stop driver 
    		return true; 		
    	}
    	return false;
    	
    	
    }
    
    public ArrayList<Direction> checkAvailableDirections(){
    	ArrayList<Direction> list = new ArrayList<Direction>();
    	list.add(Direction.NORTH);
    	return list;
    }
    
    public boolean checkForNewPath() {
    	//call rotate sensor rotatesensor()
    	//check left and right for a new path
    	return false;
    	
    }

}
