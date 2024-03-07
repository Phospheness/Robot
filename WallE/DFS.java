package WallE;
import  java.util.LinkedList;





public class DFS {

    /*
     * This class is used to implement the Depth First Search algorithm
     * It should be software based and not use any sensors or motors
     */

    private boolean found;
    private LinkedList<Node> maze;
    
    


    public DFS() {
        found = false;
        maze = new LinkedList<Node>();
    }


    /*
     * To be implemented when WallE finds a junction
     */
    public void addNode(Node node) {
        maze.add(node);  
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

    public void setFound(boolean found) {
        this.found = found;
    }


    //this is the main function that will be called to start the DFS algorithm
    //it will use thread.yield() to pause the algorithm when WallE is moving
    public void dfs(Node node) {
        node.setVisited(true);


        //call DetectTarget() to check if WallE has found the rescue target
        if (found) { 
            reverseList();
            return;
        }

        //iterate through the directions of the node
        for (Direction direction : node.getDirections()) {
            Node neighbor = node.getNeighbor(direction);
            if (!neighbor.isVisited()) {
                dfs(neighbor);
            }
        }


        
        




    

 
    }




    
}
