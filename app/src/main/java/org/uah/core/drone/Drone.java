package org.uah.core.drone;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.common.msg_attitude;
import com.MUHLink.common.msg_heartbeat;
import com.MUHLink.enums.MUH_MSG_ID;

import org.uah.core.drone.variables.Attitude;
import org.uah.core.drone.variables.Time;

/**
 * Created by Gui Zhou on 2016/3/18.
 */
public class Drone {

    public byte type_num;
    public byte contral_mode;
    public byte fly_state;
    public byte fly_mode;

    public double longitude;
    public double latitude;
    public float altitude;

    public float ground_speed;
    public float vertical_speed;
    public float line_angle;
    public float flight_angle;

    public float cpu_ratio;
    public float voltage;
    public float oil_meter;

    public Time fly_time;
    public Attitude attitude;

    public Drone() {

    }

    public void getHeartbeatMsg(msg_heartbeat msgHeartbeat) {
        this.type_num = msgHeartbeat.type_num;
        this.contral_mode = msgHeartbeat.contral_mode;
        this.fly_state = msgHeartbeat.fly_state;
        this.fly_mode = msgHeartbeat.fly_mode;

        this.longitude = msgHeartbeat.longitude;
        this.latitude = msgHeartbeat.latitude;
        this.altitude = msgHeartbeat.altitude;

        this.ground_speed = msgHeartbeat.ground_speed;
        this.vertical_speed = msgHeartbeat.vertical_speed;
        this.line_angle = msgHeartbeat.line_angle;
        this.flight_angle = msgHeartbeat.flight_angle;

        this.cpu_ratio = msgHeartbeat.cpu_ratio;
        this.voltage = msgHeartbeat.voltage;
        this.oil_meter = msgHeartbeat.oil_meter;
        this.fly_time = new Time(msgHeartbeat.fly_time);
    }

    public void getAttitudeMsg(msg_attitude msgAttitude) {
        this.attitude = new Attitude(msgAttitude.rollAngle,
                msgAttitude.yawAngle, msgAttitude.pitchAngle);
    }

}
