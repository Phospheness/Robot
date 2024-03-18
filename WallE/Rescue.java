package WallE;

public class Rescue extends Behaviour{

    private LightSensor lightSensor;
    private EV3MediumRegulatedMotor claws = new EV3MediumRegulatedMotor(MotorPort.B);
    private boolean suppressed = false; 

    public Rescue() {
        lightSensor = new LightSensor();
    }


    //take control if the light sensor detects the rescue target
    @Override
    public boolean takeControl() {
        return LightSensor.getTargetFound();
    }


   
    @Override
    public void action() {
        lift();
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
