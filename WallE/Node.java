package WallE;

import java.util.ArrayList;


public class Node { //nodes to be placed down for the pathfinding algorithm

    private float x;
    private float y;
    private ArrayList<Direction> directions;
    private Node next;
    private boolean visited;


    /*
     * Constructor for the Node class
     * 
     * @param x the x coordinate of the node
     * @param y the y coordinate of the node
     * @param directions the directions that are available to travel in from this node 
     */
    public Node(float x, float y, ArrayList<Direction> directions) {
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

    public ArrayList<Direction> getDirections() {
        return directions;
    }   

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


}
