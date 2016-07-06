package com.MUHLink.enums;

/**
 * Created by Gui Zhou on 2016-03-10.
 */

public class MUH_MSG_ID {

    /**
     * 同步头定义 数据长度的定义
     */
    public static final int DATALINK_STX1 = 0xEB;
    public static final int DATALINK_STX2 = 0x90;
    public static final int DATALINK_MAX_PAYLOAD_LEN = 256;

    /**
     * 系统和组件编码
     */
    public static final int UAV_SYS_ID = 0x01;   /* 系统号 */

    public static final int UAV_COMP_ID_SYSENABLE = 1;
    public static final int UAV_COMP_ID_SYSINFO = 2;
    public static final int UAV_COMP_ID_HELIINFO = 3;
    public static final int UAV_COMP_ID_WAYPOINT = 4;
    public static final int UAV_COMP_ID_CTRLCHANNEL = 5;
    public static final int UAV_COMP_ID_CMDFLY = 6;
    public static final int UAV_COMP_ID_CMDMISSION = 7;
    public static final int UAV_COMP_ID_CMDTEST = 8;
    public static final int UAV_COMP_ID_FILE = 9;
    public static final int UAV_COMP_ID_FCC = 51; /* 飞控机组件号 */
    public static final int UAV_COMP_ID_FBL = 52;
    public static final int UAV_COMP_ID_IMU = 56;
    public static final int UAV_COMP_ID_GPS = 57;
    public static final int UAV_COMP_ID_HMR = 58;
    public static final int UAV_COMP_ID_ACS = 81;
    public static final int UAV_COMP_ID_RC1 = 121;
    public static final int UAV_COMP_ID_RECORD = 141;
    public static final int UAV_COMP_ID_POWERSYS = 142;
    public static final int UAV_COMP_ID_ENGINE = 143;
    public static final int UAV_COMP_ID_SPRAY = 144;
    public static final int UAV_COMP_ID_CAMERA = 145;
    public static final int UAV_COMP_ID_SIM = 201;
    public static final int UAV_COMP_ID_SCENE = 202;
    public static final int UAV_COMP_ID_IAP = 203;
    public static final int UAV_COMP_ID_SELFTEST = 204;
    public static final int UAV_COMP_ID_MENUINFO = 205;
    public static final int UAV_COMP_ID_BASESTATION = 206;

    /**
     * 不同组件的共有消息 common MSG
     */
    public static final int UAV_MSG_REQU_ECHO = 0x01;
    public static final int UAV_MSG_REQU_READ = 0x02;
    public static final int UAV_MSG_REQU_SEND = 0x03;
    public static final int UAV_MSG_REQU_SAVE = 0x04;
    public static final int UAV_MSG_REQU_DEL = 0x05;

    /**
     * -01-UAV_COMP_ID_SYSENABLE 系统使能组件
     */
    public static final int UAV_MSG_REQU_STATUS = 0x10;
    public static final int UAV_MSG_SET_SYSMODE = 0x11;
    public static final int UAV_MSG_ASK_SYSMODE = 0x12;
    public static final int UAV_MSG_SET_PARAM = 0x13;
    public static final int UAV_MSG_ASK_PARAM = 0x14;
    public static final int UAV_MSG_SET_FILE = 0x15;
    public static final int UAV_MSG_ASK_FILE = 0x16;
    public static final int UAV_MSG_SET_FLYCMD = 0x17;
    public static final int UAV_MSG_ASK_FLYCMD = 0x18;
    public static final int UAV_MSG_SET_ACTIVED = 0x19;
    public static final int UAV_MSG_ASK_ACTIVED = 0x1A;
    public static final int UAV_MSG_SET_UPDATE = 0x1B;
    public static final int UAV_MSG_UPDATE_FCS = 0x1C;

    /**
     * -02- UAV_COMP_ID_SYSINFO 系统数据组件类
     */
    public static final int UAV_MSG_INFO_SYSNUM = 0x10;
    public static final int UAV_MSG_INFO_UAVNUM = 0x11;
    public static final int UAV_MSG_INFO_FCCNUM = 0x12;
    public static final int UAV_MSG_INFO_SOFTVER = 0x13;
    public static final int UAV_MSG_INFO_USERNUM = 0x14;
    public static final int UAV_MSG_INFO_STARTTIME = 0x15;
    public static final int UAV_MSG_INFO_ENDTIME = 0x16;

    /**
     * -03- UAV_COMP_ID_HELIINFO 飞机配置组件类
     */
    public static final int UAV_MSG_PLANE_TYPE = 0x10;
    public static final int UAV_MSG_PLANE_WEIGHT = 0x11;
    public static final int UAV_MSG_PLANE_CG = 0x12;
    public static final int UAV_MSG_PLANE_ENGINE = 0x13;
    public static final int UAV_MSG_PLANE_PAYLOAD = 0x14;
    public static final int UAV_MSG_PLANE_PLOADWT = 0x15;
    public static final int UAV_MSG_PLANE_FLAPTYPE = 0x16;
    public static final int UAV_MSG_PLANE_RUDTYPE = 0x17;

    /**
     * -04- UAV_COMP_ID_WAYPOINT 航线数据组件类
     */
    public static final int UAV_MSG_WP_SET = 0x10;
    public static final int UAV_MSG_WP_ASK = 0x11;
    public static final int UAV_MSG_WP_SAVE = 0x12;
    public static final int UAV_MSG_WP_DEL = 0x13;
    public static final int UAV_MSG_WP_AIRPORTSET = 0x14;
    public static final int UAV_MSG_WP_AIRPORTASK = 0x15;

    /**
     * -05- UAV_COMP_ID_CTRLCHANNAL 通道控制数据组件类
     */
    public static final int UMH_GNC_QPIDSET = 0x10;
    public static final int UMH_GNC_QPIDASK = 0x11;
    public static final int UMH_GNC_THETAPIDSET = 0x12;
    public static final int UMH_GNC_THETAPIDASK = 0x13;
    public static final int UMH_GNC_AXPIDSET = 0x14;
    public static final int UMH_GNC_AXPIDASK = 0x15;
    public static final int UMH_GNC_VXPIDSET = 0x16;
    public static final int UMH_GNC_VXPIDASK = 0x17;
    public static final int UMH_GNC_KXPIDSET = 0x18;
    public static final int UMH_GNC_KXPIDASK = 0x19;
    public static final int UMH_GNC_THETA2ELESET = 0x1A;
    public static final int UMH_GNC_THETA2ELEASK = 0x1B;
    public static final int UMH_GNC_VX2ELESET = 0x1C;
    public static final int UMH_GNC_VX2ELEASK = 0x1D;
    public static final int UMH_GNC_VXRANGESET = 0x1E;
    public static final int UMH_GNC_VXRANGEASK = 0x1F;

    public static final int UMH_GNC_PPIDSET = 0x20;
    public static final int UMH_GNC_PPIDASK = 0x21;
    public static final int UMH_GNC_PHIPIDSET = 0x22;
    public static final int UMH_GNC_PHIPIDASK = 0x23;
    public static final int UMH_GNC_AYPIDSET = 0x24;
    public static final int UMH_GNC_AYPIDASK = 0x25;
    public static final int UMH_GNC_VYPIDSET = 0x26;
    public static final int UMH_GNC_VYPIDASK = 0x27;
    public static final int UMH_GNC_KYPIDSET = 0x28;
    public static final int UMH_GNC_KYPIDASK = 0x29;
    public static final int UMH_GNC_PHI2AILSET = 0x2A;
    public static final int UMH_GNC_PHI2AILASK = 0x2B;
    public static final int UMH_GNC_VY2AILSET = 0x2C;
    public static final int UMH_GNC_VY2AILASK = 0x2D;
    public static final int UMH_GNC_VYRANGESET = 0x2E;
    public static final int UMH_GNC_VYRANGEASK = 0x2F;

    public static final int UMH_GNC_AZPIDSET = 0x30;
    public static final int UMH_GNC_AZPIDASK = 0x31;
    public static final int UMH_GNC_HDOTPIDSET = 0x32;
    public static final int UMH_GNC_HDOTPIDASK = 0x33;
    public static final int UMH_GNC_HPIDSET = 0x34;
    public static final int UMH_GNC_HPIDASK = 0x35;
    public static final int UMH_GNC_COLCOMPSET = 0x36;
    public static final int UMH_GNC_COLCOMPASK = 0x37;
    public static final int UMH_GNC_HDOTRANGESET = 0x38;
    public static final int UMH_GNC_HDOTRANGEASK = 0x39;

    public static final int UMH_GNC_RPIDSET = 0x3A;
    public static final int UMH_GNC_RPIDASK = 0x3B;
    public static final int UMH_GNC_PSIPIDSET = 0x3C;
    public static final int UMH_GNC_PSIPIDASK = 0x3D;
    public static final int UMH_GNC_RRANGESET = 0x3E;
    public static final int UMH_GNC_RRANGEASK = 0x3F;

    public static final int UMH_GNC_RPMPIDSET = 0x40;
    public static final int UMH_GNC_RPMPIDASK = 0x41;

    public static final int UMH_GNC_NONFB_QPIDSET = 0x42;
    public static final int UMH_GNC_NONFB_QPIDASK = 0x43;
    public static final int UMH_GNC_NONFB_PPIDSET = 0x44;
    public static final int UMH_GNC_NONFB_PPIDASK = 0x45;

    public static final int UAV_GNC_AUTO_ELESET = 0x46;
    public static final int UAV_GNC_AUTO_ELEASK = 0x47;
    public static final int UAV_GNC_AUTO_AILSET = 0x48;
    public static final int UAV_GNC_AUTO_AILASK = 0x49;
    public static final int UAV_GNC_AUTO_RUDSET = 0x4A;
    public static final int UAV_GNC_AUTO_RUDASK = 0x4B;
    public static final int UAV_GNC_AUTO_COLSET = 0x4C;
    public static final int UAV_GNC_AUTO_COLASK = 0X4D;
    public static final int UAV_GNC_AUTO_FBLSET = 0x4E;
    public static final int UAV_GNC_AUTO_FBLASK = 0X4F;

    /**
     * -06- UAV_COMP_ID_CMDFLY 飞行离散指令组件类
     */
    public static final int UAV_MSG_CMD_RESET = 0x11;
    public static final int UAV_MSG_CMD_ENGSTART = 0x12;
    public static final int UAV_MSG_CMD_ENGSTOP = 0x13;
    public static final int UAV_MSG_CMD_CTRLMANU = 0x14;
    public static final int UAV_MSG_CMD_CTRLACS = 0x15;
    public static final int UAV_MSG_CMD_CTRLMIX = 0x16;
    public static final int UAV_MSG_CMD_CTRLSOFT = 0x17;
    public static final int UAV_MSG_CMD_PREFLY = 0x18;
    public static final int UAV_MSG_CMD_TAKEOFF = 0x19;
    public static final int UAV_MSG_CMD_LANDING = 0x1A;
    public static final int UAV_MSG_CMD_HOVER = 0x1B;
    public static final int UAV_MSG_CMD_GOHOME = 0x1C;
    public static final int UAV_MSG_CMD_GUIDE = 0x1D;
    public static final int UAV_MSG_CMD_WAYPOINT = 0x1E;
    public static final int UAV_MSG_CMD_ACTIVED = 0x1F;

    /**
     * -07- UAV_COMP_ID_CMDMISSION 飞行任务（遥控）指令组件类
     */
    public static final int UAV_MSG_MIX_VX = 0x11;
    public static final int UAV_MSG_MIX_VY = 0x12;
    public static final int UAV_MSG_MIX_HDOT = 0x13;
    public static final int UAV_MSG_MIX_R = 0x20;
    public static final int UAV_MSG_MIX_PSI = 0x14;
    public static final int UAV_MSG_MIX_XE = 0x15;
    public static final int UAV_MSG_MIX_YE = 0x16;
    public static final int UAV_MSG_MIX_HE = 0x17;
    public static final int UAV_MSG_MIX_WP = 0x18;
    public static final int UAV_MSG_MIX_POINT = 0x19;

    /**
     * -08- UAV_COMP_ID_CMDTEST 测试指令组件类
     */
    public static final int UAV_MSG_TEST_DO = 0x10;
    public static final int UAV_MSG_TEST_DA = 0x11;
    public static final int UAV_MSG_TEST_CTRL = 0x12;
    public static final int UAV_MSG_TEST_ACT = 0x13;
    /**
     * -09- UAV_COMP_ID_FILE 文件传输组件类
     */
    public static final int UAV_MSG_FILE_SAVE = 0x10;
    public static final int UAV_MSG_FILE_DEL = 0x11;
    public static final int UAV_MSG_FILE_START = 0x12;
    public static final int UAV_MSG_FILE_DATA = 0x13;
    public static final int UAV_MSG_FILE_END = 0x14;
    public static final int UAV_MSG_FILE_STOP = 0x15;

    /**
     * -51- [UAV_COMP_ID_FCC] 飞控机类组件 消息ID
     */
    public static final int UAV_MSG_FCC_TYPESET = 0x10;
    public static final int UAV_MSG_FCC_TYPEASK = 0x11;
    public static final int UAV_MSG_FCC_ADSET = 0x12;
    public static final int UAV_MSG_FCC_ADASK = 0x13;
    public static final int UAV_MSG_FCC_DASET = 0x14;
    public static final int UAV_MSG_FCC_DAASK = 0x15;
    public static final int UAV_MSG_FCC_RTCSET = 0x16;
    public static final int UAV_MSG_FCC_RTCASK = 0x17;

    public static final int UAV_MSG_FCC_HEARTBEATD = 0x20;
    public static final int UAV_MSG_FCC_FLYSTATUS1 = 0x21;
    public static final int UAV_MSG_FCC_FLYSTATUS2 = 0x22;
    public static final int UAV_MSG_FCC_FLYSTATUS3 = 0x23;
    public static final int UAV_MSG_FCC_DEVSTATUS1 = 0x24;
    public static final int UAV_MSG_FCC_DEVSTATUS2 = 0x25;
    public static final int UAV_MSG_FCC_DEBUG = 0x26;

    /**
     * -56- UAV_COMP_ID_IMU 传感器IMU组件类
     */
    public static final int UAV_MSG_IMU_DIRSET = 0x10;
    public static final int UAV_MSG_IMU_DIRASK = 0x11;
    public static final int UAV_MSG_IMU_POSSET = 0x12;
    public static final int UAV_MSG_IMU_POSASK = 0x13;

    public static final int UAV_MSG_IMU_REQU = 0x14;
    public static final int UAV_MSG_IMU_RAWDATA = 0x15;
    public static final int UAV_MSG_IMU_INFODATA = 0x16;

    /**
     * -57- UAV_COMP_ID_GPS 传感器GPS组件类
     */
    public static final int UAV_MSG_GPS_POSSET = 0x10;
    public static final int UAV_MSG_GPS_POSASK = 0x11;

    public static final int UAV_MSG_GPS_REQU = 0x12;
    public static final int UAV_MSG_GPS_RAWDATA = 0x13;
    public static final int UAV_MSG_GPS_INFODATA = 0x14;


    /**
     * -58- UAV_COMP_ID_HMR 传感器HMR组件类
     */
    public static final int UAV_MSG_HMR_CALSTART = 0x10;
    public static final int UAV_MSG_HMR_CALSTOP = 0x11;
    public static final int UAV_MSG_HMR_CALEND = 0x12;
    public static final int UAV_MSG_HMR_CALSET = 0x13;
    public static final int UAV_MSG_HMR_CALASK = 0x14;
    public static final int UAV_MSG_HMR_PSIOFFSET = 0x15;
    public static final int UAV_MSG_HMR_PSIOFFASK = 0x16;

    public static final int UAV_MSG_HMR_INFOASK = 0x17;
    public static final int UAV_MSG_HMR_RAWDATA = 0x18;
    public static final int UAV_MSG_HMR_INFODATA = 0x19;
    public static final int UAV_MSG_HMR_DIRSET = 0x1A;
    public static final int UAV_MSG_HMR_DIRASK = 0x1B;

    /**
     * -81- UAV_COMP_ID_ACS 舵机系统组件类
     */
    public static final int UAV_MSG_ACS_TILTTYPESET = 0x10;
    public static final int UAV_MSG_ACS_TILTTYPEASK = 0x11;
    public static final int UAV_MSG_ACS_TILTMIXSET = 0x12;
    public static final int UAV_MSG_ACS_TILTMIXASK = 0x13;
    public static final int UAV_MSG_ACS_TILTPHASESET = 0x14;
    public static final int UAV_MSG_ACS_TILTPHASEASK = 0X15;
    public static final int UAV_MSG_ACS_TILTS1SET = 0X16;
    public static final int UAV_MSG_ACS_TILTS1ASK = 0x17;
    public static final int UAV_MSG_ACS_TILTS2SET = 0X18;
    public static final int UAV_MSG_ACS_TILTS2ASK = 0X19;
    public static final int UAV_MSG_ACS_TILTS3SET = 0x1A;
    public static final int UAV_MSG_ACS_TILTS3ASK = 0X1B;
    public static final int UAV_MSG_ACS_TILTS4SET = 0X1C;
    public static final int UAV_MSG_ACS_TILTS4ASK = 0X1D;

    public static final int UAV_MSG_ACS_FBLSET = 0X1E;
    public static final int UAV_MSG_ACS_FBLASK = 0X1F;
    public static final int UAV_MSG_ACS_RUDSERVOSET = 0X20;
    public static final int UAV_MSG_ACS_RUDSERVOASK = 0X21;
    public static final int UAV_MSG_ACS_GYROTYPESET = 0X22;
    public static final int UAV_MSG_ACS_GYROTYPEASK = 0X23;
    public static final int UAV_MSG_ACS_RUDRANGESET = 0X24;
    public static final int UAV_MSG_ACS_RUDRANGEASK = 0X25;
    public static final int UAV_MSG_ACS_ADJUSTSET = 0X26;
    public static final int UAV_MSG_ACS_ADJUSTASK = 0X27;
    public static final int UAV_MSG_ACS_RUDISET = 0X28;
    public static final int UAV_MSG_ACS_RUDIASK = 0X29;

    public static final int UAV_MSG_ACS_ACT1SET = 0X2A;
    public static final int UAV_MSG_ACS_ACT1ASK = 0X2B;
    public static final int UAV_MSG_ACS_ACT2SET = 0X2C;
    public static final int UAV_MSG_ACS_ACT2ASK = 0X2D;
    public static final int UAV_MSG_ACS_ACT3SET = 0X2E;
    public static final int UAV_MSG_ACS_ACT3ASK = 0X2F;
    public static final int UAV_MSG_ACS_ACT4SET = 0X30;
    public static final int UAV_MSG_ACS_ACT4ASK = 0X31;
    public static final int UAV_MSG_ACS_ACT5SET = 0X32;
    public static final int UAV_MSG_ACS_ACT5ASK = 0X33;
    public static final int UAV_MSG_ACS_ACT6SET = 0X34;
    public static final int UAV_MSG_ACS_ACT6ASK = 0X35;
    public static final int UAV_MSG_ACS_ACT7SET = 0X36;
    public static final int UAV_MSG_ACS_ACT7ASK = 0X37;
    public static final int UAV_MSG_ACS_ACT8SET = 0X38;
    public static final int UAV_MSG_ACS_ACT8ASK = 0X39;
    public static final int UAV_MSG_ACS_ACT9SET = 0X3A;
    public static final int UAV_MSG_ACS_ACT9ASK = 0X3B;
    public static final int UAV_MSG_ACS_ACT10SET = 0X3C;
    public static final int UAV_MSG_ACS_ACT10ASK = 0X3D;

    public static final int UAV_MSG_ACS_REQU = 0X3E;
    public static final int UAV_MSG_ACS_RAWDATA = 0X3F;
    public static final int UAV_MSG_ACS_INFODATA = 0X40;
    public static final int UAV_MSG_ACS_CTRLDATA = 0X41;

    /**
     * -121- UAV_COMP_ID_RC1 RC遥控器组件类
     */
    public static final int UAV_MSG_RC_RECEIVESET = 0x10;
    public static final int UAV_MSG_RC_RECEIVEASK = 0x11;
    public static final int UAV_MSG_RC_CALISTART = 0x12;
    public static final int UAV_MSG_RC_CALIMID = 0x13;
    public static final int UAV_MSG_RC_CALIEND = 0x14;
    public static final int UAV_MSG_RC_DIRSET = 0X15;
    public static final int UAV_MSG_RC_CAP1ASK = 0X16;
    public static final int UAV_MSG_RC_CAP2ASK = 0x17;
    public static final int UAV_MSG_RC_CAP3ASK = 0X18;
    public static final int UAV_MSG_RC_CAP4ASK = 0X19;
    public static final int UAV_MSG_RC_CAP5ASK = 0x1A;
    public static final int UAV_MSG_RC_CAP6ASK = 0X1B;
    public static final int UAV_MSG_RC_CAP7ASK = 0X1C;
    public static final int UAV_MSG_RC_CAP8ASK = 0X1D;
    public static final int UAV_MSG_RC_CAP9ASK = 0X1E;
    public static final int UAV_MSG_RC_CAP10ASK = 0X1F;

    public static final int UAV_MSG_RC_REQU = 0X20;
    public static final int UAV_MSG_RC_CHDATA = 0X21;
    public static final int UAV_MSG_RC_CAP = 0X22;
    public static final int UAV_MSG_RC_CALIASK = 0x23;
    public static final int UAV_MSG_RC_THRCALI = 0x24;

    /**
     * -143- UAV_COMP_ID_ENGINE 发动机ENG组件类
     */
    public static final int UAV_MSG_ENG_TYPESET = 0x10;
    public static final int UAV_MSG_ENG_TYPEASK = 0x11;
    public static final int UAV_MSG_ENG_SERVOSET = 0x12;
    public static final int UAV_MSG_ENG_SERVOASK = 0x13;
    public static final int UAV_MSG_ENG_STOPSET = 0x14;
    public static final int UAV_MSG_ENG_STOPTEST = 0x15;
    public static final int UAV_MSG_ENG_IDLINGSET = 0X16;
    public static final int UAV_MSG_ENG_IDLINGTEST = 0x17;
    public static final int UAV_MSG_ENG_NORMALSET = 0x18;
    public static final int UAV_MSG_ENG_NORMALTEST = 0x19;
    public static final int UAV_MSG_ENG_HIGHSET = 0x1A;
    public static final int UAV_MSG_ENG_HIGHTEST = 0x1B;
    public static final int UAV_MSG_ENG_RPMSET = 0x1C;
    public static final int UAV_MSG_ENG_RPMASK = 0x1D;
    public static final int UAV_MSG_ENG_SENCTRLSET = 0x1E;
    public static final int UAV_MSG_ENG_SENCTRLASK = 0x1F;
    public static final int UAV_MSG_ENG_CTRLSET = 0x20;
    public static final int UAV_MSG_ENG_CTRLASK = 0x21;
    public static final int UAV_MSG_ENG_LAWASK = 0x22;

    /**
     * -201- UAV_COMP_ID_SIM 仿真组件类
     */
    public static final int UAV_MSG_SIM_MODEL2FCC = 0x10;
    public static final int UAV_MSG_SIM_FCC2MODEL = 0x11;

    /**
     * -203- UAV_COMP_ID_IAP 固件升级组件类
     */
    public static final int UAV_MSG_IAP_START = 0x02;
    public static final int UAV_MSG_IAP_UPLOAD = 0x03;
    public static final int UAV_MSG_IAP_FINISHED = 0x04;
    public static final int UAV_MSG_IAP_VERIFY = 0x05;
    public static final int UAV_MSG_IAP_ENABLE = 0x06;

    public static final int UAV_MSG_IAP_READSYSID = 0x20;
    public static final int UAV_MSG_IAP_WRITESYSID = 0x21;
    public static final int UAV_MSG_IAP_READCPUID = 0x22;
    public static final int UAV_MSG_IAP_KEYSTART = 0x23;
    public static final int UAV_MSG_IAP_KEYUPLOAD = 0x24;
    public static final int UAV_MSG_IAP_KEYFINISHED = 0x25;
    public static final int UAV_MSG_IAP_SAVEPARAM = 0x30;

    /**
     * -205- UAV_COMP_ID_MENUINFO 授权管理组件类
     */
    public static final int UAV_MSG_MANUINFO_FCCNUMSET = 0x10;
    public static final int UAV_MSG_MANUINFO_FCCLISSET = 0x11;
    public static final int UAV_MSG_MANUINFO_MANUDATESET = 0x12;
    public static final int UAV_MSG_MANUINFO_USERIDSET = 0x13;
    public static final int UAV_MSG_MANUINFO_HWVERSION = 0x14;

    /**
     * -206- UAV_COMP_ID_BASESTATION 地面站与差分基准站
     */
    public static final int UAV_MSG_BASESTATION_BESTPOSA = 0x10;
    public static final int UAV_MSG_BASESTATION_SETFIXPOS = 0x11;


    /**
     * -144- UAV_COMP_ID_SPRAY 喷洒系统组件类
     */
    public static final int UAV_MSG_SPRAY_UPDATE = 0x14;
    public static final int UAV_MSG_SPRAY_MODE = 0x15;
    public static final int UAV_MSG_SPRAY_VDSET = 0x16;
    public static final int UAV_MSG_SPRAY_VDASK = 0x17;
    public static final int UAV_MSG_SPRAY_PARAMSET = 0X18;
    public static final int UAV_MSG_SPRAY_PARAMASK = 0X19;
    public static final int UAV_MSG_SPRAY_BPSWSET = 0x1A;
    public static final int UAV_MSG_SPRAY_BPSWASK = 0x1B;

}
