package WallE;

public class Node { //nodes to be placed down for the pathfinding algorithm

    private int x;
    private int y;
    private Direction[] directions;
    private Node next;
    private boolean visited;


    /*
     * Constructor for the Node class
     * 
     * @param x the x coordinate of the node
     * @param y the y coordinate of the node
     * @param directions the directions that are available to travel in from this node 
     */
    public Node(int x, int y, Direction[] directions) {
        this.x = x;
        this.y = y;
        this.directions = directions;
        this.visited = false;
        this.next = null;
    }



    /*
     * Gets the next node in the linked list
     * 
     * @param next the next node in the linked list
     */
    public Node getNext() {
        return next;
    }

    /*
     * Sets the next node in the linked list
     * 
     * @param next the next node in the linked list
     */
    public void setNext(Node next) {
        this.next = next;
    }
    
    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public boolean isVisited(){
        return visited;
    }   

    public Direction[] getDirections() {
        return directions;
    }   

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
