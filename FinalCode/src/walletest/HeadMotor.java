package walletest;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.utility.Delay;
import java.util.*;

public class HeadMotor {
    public boolean isRotating = false;
    
    private UltrasonicSensor sensorService;
    private EV3MediumRegulatedMotor spinMotor;
    private Direction currentDirection;
    
    public HeadMotor(EV3MediumRegulatedMotor spinMotor, UltrasonicSensor sensorService) {
        // Initialization
        this.sensorService = sensorService;
        this.spinMotor = spinMotor;
        this.currentDirection = Direction.NORTH;
    }


    /*
     * This function is called when the UltraSonic Sensor detects a wall closeby. It will turn left and right, calling for
     * the USSensor to check if there is a wall close by when it has completed its movements.
     */
    public ArrayList<Boolean> getAvailableDirections() {
    	
    	ArrayList<Boolean> list = new ArrayList<Boolean>();
    	/*
    	System.out.println("Rotating");
    	spinMotor.setSpeed(200);
		ArrayList<Direction> list = new ArrayList<Direction>();
		

    
    	
    	//check right 
    	spinMotor.rotate(90, true);
    	Delay.msDelay(1000);
    	if(sensorService.getDistance() > 0.3) {
    		Direction direction = directionTranslate("RIGHT");
    		list.add(direction);

    	}
    	Delay.msDelay(1000);
    	
    	//check left
    	spinMotor.rotate(-180, true);
    	Delay.msDelay(1000);
    	if(sensorService.getDistance() > 0.3) {
    		Direction direction = directionTranslate("LEFT");
    		list.add(direction);	
    	}
    	Delay.msDelay(1000);
    	
    	//reset to forward
    	spinMotor.rotate(90, true);
    	System.out.println(list);
    	return list;
    	*/
    	//check right 
    	spinMotor.rotate(90, true);
    	Delay.msDelay(1000);
    	if(sensorService.getDistance() > 0.3) {	
    		list.add(true);	
    	}
    	else {list.add(false);}
    	
    	Delay.msDelay(1000);
    	
    	//check left
    	spinMotor.rotate(-180, true);
    	Delay.msDelay(1000);
    	if(sensorService.getDistance() > 0.3) {
    		Direction direction = directionTranslate("LEFT");
    		list.add(true);	
    	}
    	else {list.add(false);}
    	Delay.msDelay(1000);
    	
    	//reset to forward
    	spinMotor.rotate(90, true);
    	
    	return list;
    	
    	
    }
    
    public void setCurrentDirection(Direction dir) {
    	this.currentDirection = dir;
    	
    }
    
         

    public void stopRotate() {         
    	if (isRotating) {             
    		isRotating = false;             
    		spinMotor.stop();         
    	} else {             
			  //LCD.drawString("Motor is not rotating", 2, 2);         
    	}     
    }  
    
    public boolean checkIfPath() {
    	boolean path = sensorService.getDistance() > 0.5;//placeholder value, value is how far before it is considered there is no wall (a path)	
    	return path;
    }
    
    public Direction directionTranslate(String relativeDir) {
    	
    	switch (this.currentDirection) {
    	
    	case NORTH:
    		if(relativeDir == "RIGHT") {
    			return Direction.EAST;
    		}
    		return Direction.WEST;

    	
      	case EAST:
    		if(relativeDir == "RIGHT") {
    			return Direction.SOUTH;
    		}
    		return Direction.NORTH;
    	
    		
      	case SOUTH:
    		if(relativeDir == "RIGHT") {
    			return Direction.WEST;
    		}
    		return Direction.EAST;
    		
      	case WEST:
    		if(relativeDir == "RIGHT") {
    			return Direction.NORTH;
    		}
    		return Direction.SOUTH;
    		
    	default:
    		return Direction.NORTH;   	
    	}
    	
	
    }
    


}
