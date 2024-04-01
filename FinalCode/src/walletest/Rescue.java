package walletest;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.*;
import lejos.utility.Delay;

public class Rescue {


    private EV3MediumRegulatedMotor claws = new EV3MediumRegulatedMotor(MotorPort.D);
    private boolean suppressed = false; 
    private DFS dfs;
    private MovePilot pilot;
   

    public Rescue(MovePilot pilot) {

        this.pilot = pilot;
    }


  

   
    
    public void rescueTarget() {
    	
        pilot.travel(-50);
    	drop();
    	Delay.msDelay(500);
        pilot.travel(70);

        Delay.msDelay(1500);
        lift();
        stop();
      
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
