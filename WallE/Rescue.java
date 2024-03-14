package WallE;

public class Rescue extends Behaviour{

    private LightSensor lightSensor;
    private EV3MediumRegulatedMotor claws = new EV3MediumRegulatedMotor(MotorPort.B);
    private boolean suppressed = false; 

    public Rescue() {
        lightSensor = new LightSensor();
    }

    @Override
    public void takeControl() {
        return LightSensor.getTargetFound();
    }
    
    @Override
    public void action() {
        lift();
    }

    @Override
    public void suppress() {
        supressed = true;
    }

    public void lift() {
        claws.setSpeed(100);
        claws.rotate(50);
    }
    
}
