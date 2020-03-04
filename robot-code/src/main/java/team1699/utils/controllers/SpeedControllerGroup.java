package team1699.utils.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeedControllerGroup {

    private BetterSpeedController master;
    private List<BetterSpeedController> controllers;

    public SpeedControllerGroup(final BetterSpeedController master) {
        this.master = master;
        controllers = new ArrayList<>();
    }

    public SpeedControllerGroup(final BetterSpeedController master, BetterSpeedController... controllers) {
        this.master = master;
        this.controllers = new ArrayList<>();
        this.controllers.addAll(Arrays.asList(controllers));
    }

    public void set(final double percent) {
        this.master.set(percent);
        for (BetterSpeedController controller : controllers) {
            if (controller != null) {

                controller.set(percent);
            }
        }
    }

    //Should only be used when it is known the controller is a talon
    public void set(final ControlMode mode, final double out) {
        //TODO Try catch cast
        master.getTalon().set(mode, out);
    }

    public double get() {
        return this.master.get();
    }

    public BetterSpeedController getMaster() {
        return master;
    }

}
