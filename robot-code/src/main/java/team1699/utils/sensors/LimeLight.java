package team1699.utils.sensors;

import edu.wpi.first.networktables.NetworkTable;
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

    public int getTX(){
        return table.getEntry("tx").getNumber(0).intValue();
    }

    public int getTY(){
        return table.getEntry("ty").getNumber(0).intValue();
    }
}
