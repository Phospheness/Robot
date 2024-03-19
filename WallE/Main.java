package WallE;

public class Main {


    //instance of all the classes
    private Driver driver;
    private NodeDetector nodeDetector;
    private DFS dfs;

    
    public Main() {

        nodeDetector = new NodeDetector();
        dfs = new DFS();
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
        arby.start(); // might have to change to .go()
    }
    
    public void run() {
        
        //create a node and add it to the maze
        Node startNode = nodeDetector.createNode();
        dfs.addNode(startNode);

        //run the behaviours
        startBehaviours();

        //start the DFS algorithm
        dfs.traverse(startNode);

    }

}
