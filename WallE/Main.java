package WallE;

import lejos.robotics.subsumption.*;

public class Main {


    //instance of all the classes
    private Driver driver;
    private NodeManager nodeManager;
    private DFS dfs;

    
    public Main() {
    	dfs = new DFS();
        nodeManager = new NodeManager(dfs,driver.getPilot());
        
    }
    
    
    public static void main(String [] args) {
        
        Main main = new Main();
        main.run();
        
    }

    public void startBehaviours() {

        Behavior b1 = new RotateBehaviour();
        Behavior b2 = new Rescue(dfs);
        Behavior b3 = new BatteryLevel(6.5f);
        Behavior b4 = new emergRobotStop();
        Behavior b5 = new Driver(dfs);
        Behavior [] bArray = {b1, b2, b3, b4, b5};
        Arbitrator arby = new Arbitrator(bArray);
        arby.go(); // might have to change to .go()
    }
    
    public void run() {
        
        //create a node and add it to the maze
        Node startNode = nodeManager.createNode();
        dfs.addNode(startNode);

        //run the behaviours
        startBehaviours();

        //start the DFS algorithm
        dfs.traverse(startNode);

    }

}
