package WallE;
import  java.util.LinkedList;

public class DFS {

    /*
     * This class is used to implement the Depth First Search algorithm
     * It should be software based and not use any sensors or motors
     */

    private boolean found;
    private LinkedList<Node> maze;
    private boolean nextNodeArrived = false;
    private Direction nextDirection;
    
    


    public DFS() {
        found = false;
        maze = new LinkedList<Node>();
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextNodeArrived(boolean nextNodeArrived) {
        this.nextNodeArrived = nextNodeArrived;
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

    /*
     * to be implemented when found == true and WallE has reached the rescue target
     * this makes it easier to trace back the path WallE took
     */ 
    public void reverseList() {
        LinkedList<Node> temp = new LinkedList<Node>();
        for (int i = maze.size() - 1; i >= 0; i--) {
            temp.add(maze.get(i));
        }    
        maze = temp;
    }    


    //this is the main function that will be called to start the DFS algorithm
    //it will use thread.yield() to pause the algorithm when WallE is moving
    public void dfs(Node node) {
        //This node has been visited
        node.setVisited(true);


        //if WallE has ran into a dead end or has already visited this node, return
        if (node.getDirections().length == 0 || node.isVisited()) {
            return;
        }

        //PLACEHOLDER
        //call DetectTarget() to check if WallE has found the rescue target        
        if (found) { 
            reverseList();
            return;
        }

        //iterate through the directions of the node, WallE will need to move in all directions from the node
        for (Direction direction : node.getDirections()) {

            //Here the next direction to travel is set.
            //The while loop will pause the algorithm until WallE has reached the next junction
            //Other classes should call getNextDirection() to get the next direction to travel
            //and setNextNodeArrived() to true when WallE has reached the next junction so they can continue the algorithm
            nextDirection = direction;
            System.out.println("Direction to travel calculated, waiting for next junction " + direction);
            while (!nextNodeArrived) {
                Thread.yield();
            }
            nextNodeArrived = false; //reset the flag

            //Once the next junction is reached the algorithm will call itself recursively
            //This will continue until the rescue target is found
            dfs(node.getNext());
            
        }
 
    }




    
}
