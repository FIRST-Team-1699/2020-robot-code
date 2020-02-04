package team1699.subsystems;

import team1699.utils.controllers.SpeedControllerGroup;

public class Shooter {

    enum ShooterState{
        UNINITIALIZED,
        RUNNING,
        ESTOPPED
    }

    private final SpeedControllerGroup controllerGroup;
    private double goal = 0.0; //TODO Figure out units
    private ShooterState state = ShooterState.UNINITIALIZED;
    private double lastError = 0.0;
    private double filteredGoal = 0.0; //TODO Figure out if we need this

    static final double kDt = 0.05;

    //Max Velocity
    static final double kMaxVelocity = 2.0;

    //Min Velocity
    static final double kMinVelocity = 0.0;

    //Max voltage to be applied
    static final double kMaxVoltage = 12.0;

    //Max voltage when zeroing
    static final double kMaxZeroingVoltage = 4.0;

    //Control loop constants
    static final double Kp = 60.0;
    static final double Kv = 100.0;

    //TODO Add a constructor so we don't have to use a group?
    public Shooter(SpeedControllerGroup controllerGroup){
        this.controllerGroup = controllerGroup;
    }

    //TODO Do we need enabled
    public void update(double encoderRate, final boolean enabled){
        switch(state){
            case UNINITIALIZED:
                break;
            case RUNNING:
                break;
            case ESTOPPED:
                break;
            default:
                break;
        }
    }

    public void setGoal(final double goal){
        this.goal = goal;
    }

    public double getGoal(){
        return goal;
    }
}
