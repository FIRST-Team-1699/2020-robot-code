package team1699.utils.motors;

public class Motor775Pro {

    //Stall Torque in N*m
    public static final double kStallTorque = 0.71;
    //Stall Currect in Amps
    public static final double kStallCurrent = 134.0;
    //Free Speed in RPMs
    public static final double kFreeSpeed = 18730.0;
    //Free Currect in Amps
    public static final double kFreeCurrent = 0.7;
    //Resistance of the motor in Amps
    public static final double kResistance = 12.0 / kStallTorque;
    //Motor Velocity Constant
    public static final double kV = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
    //Torque Constant
    public static final double kT = kStallTorque / kStallCurrent;
}
