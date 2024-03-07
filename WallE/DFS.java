package WallE;
import  java.util.LinkedList;


public class DFS {

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




    
}
