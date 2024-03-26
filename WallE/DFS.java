package WallE;
import  java.util.*;

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
    
    


    public DFS() {
        found = false;
        maze = new LinkedList<Node>();
    }

    public boolean getNeedToMove() {
        return needToMove;
    }
    
    public void setNeedToMove(boolean val) {
    	this.needToMove = val;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextNodeArrived(boolean nextNodeArrived) {
        this.nextNodeArrived = nextNodeArrived;
    }

    public boolean isBacktracking() {
        return isBacktracking;
    }

    /*
     * To be implemented when WallE finds a junction
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

        //if WallE has ran into a dead end or has already visited this node, return
        if (node.getDirections().size() == 0 || node.isVisited() || found) {
            backTrack();
            //wait until Walle has reached the node before continuing
            while (!nextNodeArrived) {
                Thread.yield();
            }
            nextNodeArrived = false; //reset the flag
            return;
        }

    

        //iterate through the directions of the node, WallE will need to move in all directions from the node
        for (Direction direction : node.getDirections()) {

            //Here the next direction to travel is set.
            //The while loop will pause the algorithm until WallE has reached the next junction
            //Other classes should call getNextDirection() to get the next direction to travel
            //and setNextNodeArrived() to true when WallE has reached the next junction so they can continue the algorithm
            this.nextDirection = direction;
            System.out.println("Direction to travel calculated, waiting for next junction " + direction);
            this.needToMove = true;

            while (!nextNodeArrived) {
                Thread.yield();
            }
            this.nextNodeArrived = false; //reset the flag
            
            List<Direction> list= node.getDirections();
            if(list.get(list.size()-1) == direction) {
            	//if this is the last direction to explore
            	node.setVisited(true);
            }

            //Once the next junction is reached the algorithm will call itself recursively
            //This will continue until the rescue target is found
            traverse(node.getNext());
            
        }
 
    }




    
}
