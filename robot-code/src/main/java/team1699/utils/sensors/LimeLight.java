package team1699.utils.sensors;

import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {

    private static LimeLight instance;

    public static LimeLight getInstance(){
        if(instance == null){
            instance = new LimeLight();
        }
        return instance;
    }

    private final NetworkTableInstance table;

    private LimeLight(){
        table = NetworkTableInstance.getDefault();
    }

    public double getTX(){
        return table.getEntry("tx").getDouble(0);
    }

    public double getTY(){
        return table.getEntry("ty").getDouble(0);
    }
}
