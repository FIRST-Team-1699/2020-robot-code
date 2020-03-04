package team1699.utils;

public class MotorConstants {

    public static class MotorCIM {
        //Stall Torque in N*m
        public static final double kStallTorque = 2.41;
        //Stall Current in Amps
        public static final double kStallCurrent = 131.0;
        //Free Speed in RPMs
        public static final double kFreeSpeed = 5330.0;
        //Free Current in Amps
        public static final double kFreeCurrent = 2.7;
        //Resistance of the motor
        public static final double kResistance = 12.0 / kStallTorque;
        //Motor Velocity Constant
        public static final double Kv = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
        //Torque constant
        public static final double Kt = kStallTorque / kStallCurrent;
    }

    public static class MotorDualCIM {
        //Stall Torque in N*m
        public static final double kStallTorque = 2.41 * 2.00;
        //Stall Current in Amps
        public static final double kStallCurrent = 131.0;
        //Free Speed in RPMs
        public static final double kFreeSpeed = 18730.0;
        //Free Current in Amps
        public static final double kFreeCurrent = 2.7;
        //Resistance of the motor
        public static final double kResistance = 12.0 / kStallTorque;
        //Motor Velocity Constant
        public static final double Kv = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
        //Torque constant
        public static final double Kt = kStallTorque / kStallCurrent;
    }

    public static class MotorFalcon {
        //Stall Torque in N*m
        public static final double kStallTorque = 4.69;
        //Stall Current in Amps
        public static final double kStallCurrent = 257.0;
        //Free Speed in RPMs
        public static final double kFreeSpeed = 6380.0;
        //Free Current in Amps
        public static final double kFreeCurrent = 1.5;
        //Resistance of the motor
        public static final double kResistance = 12.0 / kStallTorque;
        //Motor Velocity Constant
        public static final double Kv = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
        //Torque constant
        public static final double Kt = kStallTorque / kStallCurrent;
    }

    public static class MotorDualFalcon {
        //Stall Torque in N*m
        public static final double kStallTorque = 4.69 * 2.00;
        //Stall Current in Amps
        public static final double kStallCurrent = 257.0;
        //Free Speed in RPMs
        public static final double kFreeSpeed = 6380.0;
        //Free Current in Amps
        public static final double kFreeCurrent = 1.5;
        //Resistance of the motor
        public static final double kResistance = 12.0 / kStallTorque;
        //Motor Velocity Constant
        public static final double Kv = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
        //Torque constant
        public static final double Kt = kStallTorque / kStallCurrent;
    }

    //Motor constants
    public class Motor775Pro {
        //Stall Torque in N*m
        public static final double kStallTorque = 0.71;
        //Stall Current in Amps
        public static final double kStallCurrent = 134.0;
        //Free Speed in RPMs
        public static final double kFreeSpeed = 18730.0;
        //Free Current in Amps
        public static final double kFreeCurrent = 0.7;
        //Resistance of the motor
        public static final double kResistance = 12.0 / kStallTorque;
        //Motor Velocity Constant
        public static final double Kv = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
        //Torque constant
        public static final double Kt = kStallTorque / kStallCurrent;
    }

    public class MotorDual775Pro {
        //Stall Torque in N*m
        public static final double kStallTorque = 0.71 * 2.00;
        //Stall Current in Amps
        public static final double kStallCurrent = 134.0;
        //Free Speed in RPMs
        public static final double kFreeSpeed = 18730.0;
        //Free Current in Amps
        public static final double kFreeCurrent = 0.7;
        //Resistance of the motor
        public static final double kResistance = 12.0 / kStallTorque;
        //Motor Velocity Constant
        public static final double Kv = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
        //Torque constant
        public static final double Kt = kStallTorque / kStallCurrent;
    }
}
