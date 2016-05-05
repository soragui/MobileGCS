package org.uah.core.drone;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.common.msg_attitude;
import com.MUHLink.common.msg_flystatus1;
import com.MUHLink.common.msg_flystatus2;
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

    public float theta;
    public float psi;
    public float phi;

    public Drone() {

    }

    public void getHeartbeatMsg(msg_heartbeat msgHeartbeat) {

        this.longitude = msgHeartbeat.ac_lon;
        this.latitude = msgHeartbeat.ac_lat;
        this.altitude = msgHeartbeat.ac_height;

        this.fly_time = new Time(msgHeartbeat.flyTime);
    }

    public void getFlyStatus1(msg_flystatus1 msgFlystatus1) {
        this.theta = msgFlystatus1.theta;
        this.psi = msgFlystatus1.psi;
        this.phi = msgFlystatus1.phi;
    }

    public void getFlyStatus2(msg_flystatus2 msgFlystatus2) {

    }

    public void getAttitudeMsg(msg_attitude msgAttitude) {
        this.attitude = new Attitude(msgAttitude.rollAngle,
                msgAttitude.yawAngle, msgAttitude.pitchAngle);
    }

}
