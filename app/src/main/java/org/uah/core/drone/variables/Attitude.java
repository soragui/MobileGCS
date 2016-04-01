package org.uah.core.drone.variables;

/**
 * Created by Gui Zhou on 2016-03-19.
 */
public class Attitude {

    private float rollAngle;
    private float yawAngle;
    private float pitchAngle;

    public Attitude(float rollAngle, float yawAngle, float pitchAngle) {
        this.rollAngle = rollAngle;
        this.yawAngle = yawAngle;
        this.pitchAngle = pitchAngle;
    }

    public float getPitchAngle() {
        return pitchAngle;
    }

    public float getRollAngle() {
        return rollAngle;
    }

    public float getYawAngle() {
        return yawAngle;
    }
}
