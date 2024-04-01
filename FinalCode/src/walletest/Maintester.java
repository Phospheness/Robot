package walletest;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.subsumption.*;
import lejos.utility.Delay;
import lejos.robotics.navigation.MovePilot;

import lejos.hardware.Button;
import java.util.*;


public class Maintester {
    // Motor and sensor declarations
    private EV3MediumRegulatedMotor spinMotor;
    private BaseRegulatedMotor mRight, mLeft;
    private MovePilot pilot;
    private UltrasonicSensor ussensor;
    private HeadMotor headMotor;
    private LightSensor ls;
    
    private Rescue rescue;
    
    private DFS dfs;
    private NodeManager nodeManager;
    private Direction currentDir = Direction.NORTH;
    
    

    public Maintester() {
        // Motors and sensors initialization
        this.spinMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        this.mRight = new EV3LargeRegulatedMotor(MotorPort.B);
        this.mLeft = new EV3LargeRegulatedMotor(MotorPort.C);
        Wheel wR = WheeledChassis.modelWheel(mLeft, 60).offset(54);
        Wheel wL = WheeledChassis.modelWheel(mRight, 60).offset(-54);
        this.pilot = new MovePilot(new WheeledChassis(new Wheel[]{wR, wL}, WheeledChassis.TYPE_DIFFERENTIAL));
        
        this.pilot.setLinearSpeed(30);
        this.pilot.setAngularSpeed(30);
        
        EV3UltrasonicSensor ev3UltrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
        this.ussensor = new UltrasonicSensor(ev3UltrasonicSensor);
        this.headMotor = new HeadMotor(spinMotor, ussensor); 
        this.dfs = new DFS();
        this.nodeManager = new NodeManager(dfs, headMotor, pilot);
        this.ls = new LightSensor();
        this.rescue = new Rescue(pilot);

    }
    
    
    public static void main(String [] args) {
        LCD.drawString("Welcome test!", 0, 0);
        LCD.drawString("By Jae, Dhara", 0, 1);
        LCD.drawString("Ibrahim and MB", 0, 2);
        LCD.drawString("0.9.1-beta", 0, 3);
        Button.ENTER.waitForPressAndRelease();
        LCD.clear();
        
        
        Maintester main = new Maintester();
        main.run();
        
    }
    
    
   
    
    
    public void run() {
    		traverse();
    
	
      }
    
    public void goBackToLastNode() {
    	
    	boolean atNode = false;
    	pilot.rotate(90);
		Delay.msDelay(1500);
		pilot.forward();
		
		while(!atNode) {
			atNode = nodeManager.checkArrived(dfs.getMaze().getLast());
		}
		
    }
    
    public void travelUntilNode() {
    	boolean atNode = false;
		pilot.forward();
		
		while(!atNode) {
			atNode = nodeManager.checkArrived(dfs.getMaze().getLast());
		}
		pilot.stop();
    }
        
    
    public void traverse() {
    	//start method by creating a node and setting initial direction to north
    	ArrayList<Direction> list = new ArrayList<Direction>();
    	list.add(Direction.NORTH);    
        nodeManager.createNode(list);
        dfs.addNode(nodeManager.createNode(list));
        
        boolean found = false; //the target to rescue
        
    	
        while(!found) {
        	//driver should move forwards now until we hit a wall
           	
        	LCD.clear();
            pilot.forward();
            	
        	boolean atJunction = false; 
        	//continually check for either the rescue target or a wall 
        	LCD.drawString("atJunction; "+atJunction, 0, 3);
        	while(!atJunction) {
        		float distanceToWall = ussensor.getDistance();
        		LCD.drawString("Distance: " + distanceToWall, 0, 5);
        		if(distanceToWall < 0.10) { //if at a wall
        			pilot.stop();
        			atJunction = true;
        		}
        		if(ls.getTargetFound()) { //if target is found, break out of both while loops to start rescue procedure
        			pilot.stop();
        			found = true;
        			break;
        		}
        	}
        	if(found) {//break out of main loop if found
        		System.out.println("found");
        		break;
        	}
        	//arrived at wall
        	
        	//now check left and right 
        	LCD.drawString("NOW CHECKING DIR", 0, 1);
        	ArrayList<Boolean> directions = headMotor.getAvailableDirections();
        	LCD.drawString("DIR CHECKED", 0,2);
        	//if dead end
        	LCD.clear();
        	/*
        	if(directions.size() == 0) {
        		goBackToLastNode();
        	}
        	
        	if(directions.size() == 1) {
        		Node node = nodeManager.createNode(directions);
        		Direction dirToTurn = node.getDirections().get(0);
        		node.setDirectionsEmpty();
        		rotateToDirection(dirToTurn);
        		headMotor.setCurrentDirection(dirToTurn);
        	}
        	
        	if(directions.size() == 2) {
        		Node node = nodeManager.createNode(directions);
        		Direction dirToTurn = node.getDirections().get(0);
        		node.removeIndex(0);
        		rotateToDirection(dirToTurn);
        		headMotor.setCurrentDirection(dirToTurn);
        	}
        	*/
        	if(directions.contains(false)) {//if there is only one direction
        		if(directions.get(0) == false) {//if right is not the direction
        			pilot.rotate(100);//rotate left 90
        		}
        		else {
        			pilot.rotate(-100);//rotate right
        		}
        	}
        	else {
        		pilot.rotate(100);//default case turn left
        	}
        	
        	
        }
        
        
        //start rescue procedure
        
        rescue.rescueTarget();
        
        /*
        goBackToLastNode();
        while (dfs.getMaze().size() > 1) {
        	Direction dir = getDirectionOfNode(dfs.getMaze().getLast());
        	rotateToDirection(dir);
        	travelUntilNode();	
        }
        */

    } 
    


    private boolean checkForTargetOrWalls() {
        float distanceToWall = ussensor.getDistance();
        if (distanceToWall < 0.085) { // Wall is too close
            pilot.stop();
            return true; // Indicate to re-evaluate the environment
        }
        if (ls.getTargetFound()) { // Target found
            pilot.stop();
            return true; // Indicate target is found
        }
        return false; // Continue moving forward
    }

    private void handleTurn(ArrayList<Direction> directions) {
        Node node = nodeManager.createNode(directions);
        Direction dirToTurn = directions.get(0); // Example: choose the first direction
        rotateToDirection(dirToTurn);
        travelUntilNode(); // Move to the next node
        dfs.addNode(node); // Add the new node to the DFS path
    }
    
    public Direction getDirectionOfNode(Node curNode) {
    	dfs.removeLastNode();
        Node nextNode = dfs.getMaze().getLast(); // Assuming you want to find the direction to the last node in the list

        float deltaX = nextNode.getX() - curNode.getX();
        float deltaY = nextNode.getY() - curNode.getY();

        // Assuming positive Y direction is NORTH and positive X direction is EAST
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // The movement is primarily horizontal
            if (deltaX > 0) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        } else {
            // The movement is primarily vertical
            if (deltaY > 0) {
                return Direction.NORTH;
            } else {
                return Direction.SOUTH;
            }
        }
    }

    
   
        
    public void rotateToDirection(Direction dir) {
 
        int turns = (dir.ordinal() - currentDir.ordinal() + 4) % 4;
        // Determine the shortest path to the next direction
        if (turns == 1) {
            // Turn right 90 degrees
        	 pilot.rotate(180);
        } else if (turns == 2) {
            // Turn 180 degrees, could be right or left twice
        	 pilot.rotate(360);
        } else if (turns == 3) {
            // Turn left 90 degrees
            pilot.rotate(-180);
        }
        
    }
        
        
        
        
        

     
   
}