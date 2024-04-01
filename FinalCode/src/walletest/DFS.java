package walletest;
import  java.util.*;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class DFS {

    /*
     * This class is used to implement the Depth First Search algorithm
     * It should be software based and not use any sensors or motors
     */

    private boolean found;
    private LinkedList<Node> maze;
    private boolean nextNodeArrived = false;
    private Direction nextDirection;
    private boolean needToMove = false;
    private boolean isBacktracking = false;
    
    public void listenForButtonPresses() {
        while (true) {
            if (Button.ESCAPE.isDown()) {
                // If the back button is pressed, return to main menu and exit
                LCD.clear();
                LCD.drawString("Returning to main menu...", 0, 0);
                // Delay to display the message
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Exit the program
                System.exit(0);
            }
            // Check for button presses periodically
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    


    public DFS() {
        found = false;
        maze = new LinkedList<Node>();
    }
    
    public LinkedList<Node> getMaze(){
    	return maze;
    }
    	
    public void removeLastNode() {
    	maze.removeLast();
    }

    public boolean getNeedToMove() {
        return needToMove;
    }
    
    public void setNeedToMove(boolean val) {
    	this.needToMove = val;
    }

    public Direction getNextDirection() {
    	  if (nextDirection == null) {
    	        nextDirection = Direction.NORTH; // Set to NORTH if nextDirection is null
    	    }
    	    return nextDirection;
    }

    public void setNextNodeArrived(boolean nextNodeArrived) {
        this.nextNodeArrived = nextNodeArrived;
    }

    public boolean isBacktracking() {
        return isBacktracking;
    }

    /*
     * To be implemented when WallE finds a 
     */
    public void addNode(Node node) {
        if (!maze.isEmpty()) {
            Node lastNode = maze.getLast();
            lastNode.setNext(node);
        }
        maze.add(node);
    }

    public void setFound(boolean found) {
        this.found = found;
    }   

    public void backTrack() {
        if (maze.size() == 1) {
            return;
        }
        isBacktracking = true;
        maze.removeLast();
        nextDirection = maze.getLast().getDirections().get(0);
        needToMove = true;
    }

    //this is the main function that will be called to start the DFS algorithm
    //it will use thread.yield() to pause the algorithm when WallE is moving
    public void traverse(Node node) {
    	LCD.clear();
    	//LCD.drawString("size: " + node.getDirections().size(), 0, 0);
        //LCD.drawString("isVisited:" + node.isVisited(), 0, 1);
        //LCD.drawString("\n found:" + found, 0, 2);
    	//if WallE has ran into a dead end or has already visited this node, return
    	
    	/*
    	try {
	        if (node.getDirections().size() == 0 || node.isVisited() || found) {
	        	LCD.drawString("Made it past the if statement", 0, 3);
	            backTrack();
	            //wait until Walle has reached the node before continuing
	            while (!nextNodeArrived) {
	                Thread.yield();
	            }
	            nextNodeArrived = false; //reset the flag
	            return;
	        }
    	} catch (NullPointerException e) {
			LCD.drawString("Backtrack error: "+ e.getMessage(), 0, 3);
			listenForButtonPresses();
		}
    	*/

        //iterate through the directions of the node, WallE will need to move in all directions from the node
        
        // change this:
        /*
         * When WallE gets to a node, his head turns left. Use USSensor to check distance. If wall is close,
         * then WallE will turn his head the other way. We will rig the maze so one is true at any given scenario.
         */
    	try {
	        for (Direction direction : node.getDirections()) {
	        	LCD.clear();
	        	LCD.drawString("direction: " + direction, 0, 0);
	        	LCD.drawString("nodeFunc: " + node.getDirections(), 0, 1);
	            //Here the next direction to travel is set.
	            //The while loop will pause the algorithm until WallE has reached the next junction
	            //Other classes should call getNextDirection() to get the next direction to travel
	            //and setNextNodeArrived() to true when WallE has reached the next junction so they can continue the algorithm
	            this.nextDirection = direction;
	            //System.out.println("Direction to travel calculated, waiting for next junction " + direction);
	            this.needToMove = true;
	            
	            List<Direction> list= node.getDirections();
	            if(list.get(list.size()-1) == direction) {
	            	//if this is the last direction to explore
	            	node.setVisited(true);
	            }
	
	            //Once the next junction is reached the algorithm will call itself recursively
	            //This will continue until the rescue target is found
	            traverse(node.getNext());
	            
	            while (!nextNodeArrived) {
	                Thread.yield();
	            }
	            this.nextNodeArrived = false; //reset the flag
	            
	        }
        } catch (NullPointerException e) {
			LCD.drawString("Direction error: "+ e.getMessage(), 0, 3);
			listenForButtonPresses();
		}
 
    }




    
}
