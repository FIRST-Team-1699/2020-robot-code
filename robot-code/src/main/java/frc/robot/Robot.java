package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import team1699.subsystems.DriveTrain;
import team1699.subsystems.Hopper;
import team1699.utils.controllers.SpeedControllerGroup;
import team1699.utils.controllers.falcon.BetterFalcon;

import java.io.IOException;
import java.nio.file.Path;

public class Robot extends TimedRobot {

    private Joystick driveJoystick;

    private DriveTrain driveTrain;
    private Hopper hopper;
    private BetterFalcon portDriveMaster, portDriveSlave, starDriveMaster, starDriveSlave;
    private SpeedControllerGroup portDriveGroup, starDriveGroup;

    @Override
    public void robotInit() {
        //Setup joystick
        driveJoystick = new Joystick(0);

        //TODO Fix ports
        //Setup port drive motors
        portDriveMaster = new BetterFalcon(30);
        portDriveSlave = new BetterFalcon(31);
        portDriveGroup = new SpeedControllerGroup(portDriveMaster, starDriveMaster);

        //Setup starboard drive motors
        starDriveMaster = new BetterFalcon(32);
        starDriveSlave = new BetterFalcon(33);
        starDriveGroup = new SpeedControllerGroup(starDriveMaster, starDriveSlave);

        //Setup drive train
        driveTrain = new DriveTrain(portDriveGroup, starDriveGroup, driveJoystick);
    }

    @Override
    public void robotPeriodic() {

    }


    @Override
    public void autonomousInit() {
        String trajectoryJSONPath = "home/lvuser/deploy/paths/driveToShoot.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSONPath);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException e) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSONPath, e.getStackTrace());
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopPeriodic() {
        driveTrain.update();
    }

    @Override
    public void testPeriodic() {
    }
}
