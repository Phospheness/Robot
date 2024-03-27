package WallE;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor {
    private EV3UltrasonicSensor ultrasonicSensor;
    private SampleProvider distanceSampleProvider;
    private float[] sample;

    public UltrasonicSensor(EV3UltrasonicSensor sensor) {
        this.ultrasonicSensor = sensor;
        this.distanceSampleProvider = sensor.getDistanceMode();
        this.sample = new float[distanceSampleProvider.sampleSize()];
    }

    public float getDistance() {
        distanceSampleProvider.fetchSample(sample, 0);
        return sample[0];
    }
}
