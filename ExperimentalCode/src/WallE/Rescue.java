package WallE;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.*;
import lejos.utility.Delay;

public class Rescue implements Behavior{

    private LightSensor lightSensor;
    private EV3MediumRegulatedMotor claws = new EV3MediumRegulatedMotor(MotorPort.D);
    private boolean suppressed = false; 
    private DFS dfs;
    private MovePilot pilot;

    public Rescue(DFS dfs, MovePilot pilot) {
        lightSensor = new LightSensor();
        this.dfs = dfs;
        this.pilot = pilot;
    }


    //take control if the light sensor detects the rescue target
    @Override
    public boolean takeControl() {
        return lightSensor.getTargetFound();
    }


   
    @Override
    public void action() {
    	
        pilot.travel(-50);
    	drop();
    	Delay.msDelay(500);
        pilot.travel(70);
        dfs.setFound(true);
        Delay.msDelay(1500);
        lift();
        stop();
        suppress();
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    
    public void lift() {
        claws.setSpeed(200);
        claws.rotate(90);
    }
    
    public void stop() {
    	claws.stop();
    }
    
    public void drop() {
        claws.setSpeed(200);
        claws.rotate(-70);
    }
    
    

}
