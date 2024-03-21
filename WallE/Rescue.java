package WallE;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.subsumption.*;

public class Rescue implements Behavior{

    private LightSensor lightSensor;
    private EV3MediumRegulatedMotor claws = new EV3MediumRegulatedMotor(MotorPort.B);
    private boolean suppressed = false; 
    private DFS dfs;

    public Rescue(DFS dfs) {
        lightSensor = new LightSensor();
        this.dfs = dfs;
    }


    //take control if the light sensor detects the rescue target
    @Override
    public boolean takeControl() {
        return lightSensor.getTargetFound();
    }


   
    @Override
    public void action() {
        lift();
        dfs.setFound(true);
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    
    public void lift() {
        claws.setSpeed(100);
        claws.rotate(50);
    }
    
    

}
