package WallE;

public class Node { //nodes to be placed down for the pathfinding algorithm

    private int x;
    private int y;
    private String[] directions;
    private Node next;


    /*
     * Constructor for the Node class
     * 
     * @param x the x coordinate of the node
     * @param y the y coordinate of the node
     * @param directions the directions that are available to travel in from this node 
     */
    public Node(int x, int y, String[] directions) {
        this.x = x;
        this.y = y;
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
    
}
