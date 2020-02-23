package team1699.utils.controllers.falcon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import team1699.utils.controllers.BetterSpeedController;

public class BetterFalcon extends BetterSpeedController {

    private final int port;
    private ControlMode controlMode;
    private final TalonFX talonFX;

    public BetterFalcon(final int port){
        this.port = port;
        this.controlMode = ControlMode.PercentOutput;
        talonFX = new TalonFX(port);
    }

    public BetterFalcon(final int port, final ControlMode controlMode){
        this.port = port;
        this.controlMode = controlMode;
        talonFX = new TalonFX(port);
    }

    public BetterFalcon(final int port, final boolean inverted){
        this.port = port;
        this.controlMode = ControlMode.PercentOutput;
        talonFX = new TalonFX(port);
        talonFX.setInverted(inverted);
    }

    public BetterFalcon(final int port, final ControlMode controlMode, final boolean inverted){
        this.port = port;
        this.controlMode = controlMode;
        talonFX = new TalonFX(port);
        talonFX.setInverted(inverted);
    }


    @Override
    public void set(double percent) {
        talonFX.set(controlMode, percent);
    }

    @Override
    public double get() {
        return talonFX.getMotorOutputPercent();
    }

    //TODO Add way to get sensors
}
