package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import team1699.subsystems.BallTransferStateMachine;
import team1699.subsystems.DriveTrain;
import team1699.subsystems.Hopper;
import team1699.subsystems.Intake;
import team1699.subsystems.Shooter;
import team1699.utils.controllers.SpeedControllerGroup;
import team1699.utils.controllers.falcon.BetterFalcon;
import team1699.utils.controllers.talon.BetterTalon;
import team1699.utils.sensors.AdaFruitBeamBreak;
import team1699.utils.sensors.LimitSwitch;
import team1699.utils.sensors.TalonEncoder;

import java.io.IOException;
import java.nio.file.Path;

public class Robot extends TimedRobot {

    private Joystick driveJoystick, opJoystick;

    private DriveTrain driveTrain;
    private Hopper hopper;
    private Intake intake;
    private Shooter shooter;
    private BallTransferStateMachine ballTransferStateMachine;
    private BetterTalon intakeTalon, shooterTalonTop, shooterTalonBottom, hopperTalon, talon1, talon4;
    private VictorSP colorWheelMotor;
    private BetterFalcon portDriveMaster, portDriveSlave, starDriveMaster, starDriveSlave;
    private SpeedControllerGroup portDriveGroup, starDriveGroup;
    private Compressor compressor;
    private DoubleSolenoid intakeSolenoid, flipperBigSolenoid, shooterAngleSolenoid, climberSolenoid, colorWheelSolenoid;
    private AdaFruitBeamBreak intakeBreak, hopperBreak;
    private LimitSwitch shooterBreak;
    private TalonEncoder topShooterEncoder;

    @Override
    public void robotInit() {
        //Setup joystick
        driveJoystick = new Joystick(0);
        opJoystick = new Joystick(1);

        //TODO Fix ports
        //Setup port drive motors
        portDriveMaster = new BetterFalcon(30);
        portDriveSlave = new BetterFalcon(31);
        portDriveGroup = new SpeedControllerGroup(portDriveMaster, starDriveMaster);

        //Setup starboard drive motors
        starDriveMaster = new BetterFalcon(32);
        starDriveSlave = new BetterFalcon(33);
        starDriveGroup = new SpeedControllerGroup(starDriveMaster, starDriveSlave);
        mPortMaster = new TalonFX(32);
        mPortMaster.setInverted(true);
        mPortSlave1 = new TalonFX(33);
        mPortSlave1.follow(mPortMaster);
        mPortSlave1.setInverted(true);
        //mPortSlave2 = new TalonFX(12);
        //mPortSlave2.follow(mPortMaster);

        //Setup starboard drive motors
        mStarMaster = new TalonFX(30);
        mStarMaster.setInverted(false);
        mStarSlave1 = new TalonFX(31);
        mStarSlave1.follow(mStarMaster);
        mStarSlave1.setInverted(true);
        mStarSlave1.setInverted(false);
        //mStarSlave2 = new TalonFX(15);
        //mStarSlave2.follow(mStarMaster);
        //mStarSlave2.setInverted(true);

        //Setup drive train
        driveTrain = new DriveTrain(portDriveGroup, starDriveGroup, driveJoystick);

        //Setup intake motor
        intakeTalon = new BetterTalon(11);

        //Setup hopper motor
        hopperTalon = new BetterTalon(13);

        //Setup shooter motors
        shooterTalonTop = new BetterTalon(16);
        shooterTalonBottom = new BetterTalon(12);

        //Setup climber motors


        talon1 = new BetterTalon(10);
        talon4 = new BetterTalon(10);

        //Setup color wheel motor
        colorWheelMotor = new VictorSP(0);

        //Setup solenoids
        intakeSolenoid = new DoubleSolenoid(0, 0, 1);
        flipperBigSolenoid = new DoubleSolenoid(0, 4, 5);
        shooterAngleSolenoid = new DoubleSolenoid(0, 6, 7);
        climberSolenoid = new DoubleSolenoid(25, 0, 1);
        colorWheelSolenoid = new DoubleSolenoid(25, 2, 3);

        //Setup sensors
//        intakeBreak = new AdaFruitBeamBreak(0);
//        hopperBreak = new AdaFruitBeamBreak(2);
        intakeBreak = null;
        hopperBreak = null;
        shooterBreak = new LimitSwitch(1);

        //Setup ball transfer
        SpeedControllerGroup shooterGroup = new SpeedControllerGroup(shooterTalonTop, shooterTalonBottom);
        topShooterEncoder = new TalonEncoder(shooterTalonTop);
        intake = new Intake(intakeSolenoid, intakeTalon);
        hopper = new Hopper(intakeBreak, hopperBreak, shooterBreak, hopperTalon);
        shooter = new Shooter(shooterGroup, topShooterEncoder, shooterBreak);
        ballTransferStateMachine = new BallTransferStateMachine(shooter, intake, hopper);

        //Setup compressor
        compressor = new Compressor(0);
        compressor.start();
    }

    DigitalInput testBreak1 = new DigitalInput(0);
    DigitalInput testBreak2 = new DigitalInput(2);
    @Override
    public void robotPeriodic() {
        System.out.printf("Port 0: %b ---- Port 2: %b\n", testBreak1.get(), testBreak2.get());
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
        if(driveJoystick.getTriggerPressed()){
            driveTrain.setWantedState(DriveTrain.DriveState.GOAL_TRACKING);
        }else if(driveJoystick.getTriggerReleased()){
            driveTrain.setWantedState(DriveTrain.DriveState.MANUAL);
        }

        if(opJoystick.getRawButtonPressed(7)){
            ballTransferStateMachine.setWantedState(BallTransferStateMachine.BallTransferState.INTAKING);
        }else if(opJoystick.getRawButtonPressed(8)){
            ballTransferStateMachine.setWantedState(BallTransferStateMachine.BallTransferState.SHOOTING);
        }else if(opJoystick.getRawButtonPressed(9)){
            ballTransferStateMachine.setWantedState(BallTransferStateMachine.BallTransferState.WAITING);
        }else if(opJoystick.getRawButtonPressed(10)){
            ballTransferStateMachine.setWantedState(BallTransferStateMachine.BallTransferState.EMPTYING);
        }

        if(opJoystick.getRawButtonPressed(2)){
            toggleSolenoid(shooterAngleSolenoid);
        }

        driveTrain.update();
        intake.update();
        hopper.update();
        shooter.update(topShooterEncoder.get());
        ballTransferStateMachine.update();
    }

    @Override
    public void testPeriodic() {
        runTest();
    }

    private void runTest(){
        if(driveJoystick.getTrigger()){
            intakeTalon.set(-0.5);
        }else{
            intakeTalon.set(0.0);
        }

        if(driveJoystick.getRawButton(2)){
            shooterTalonTop.set(0.65);
        }else{
            shooterTalonTop.set(0.0);
        }

        if(driveJoystick.getRawButton(3)){
            talon1.set(0.5);
        }else{
            talon1.set(0.0);
        }

        if(driveJoystick.getRawButton(4)){
            shooterTalonBottom.set(0.55);
        }else{
            shooterTalonBottom.set(0.0);
        }

        if(driveJoystick.getRawButton(5)){
            hopperTalon.set(-0.5);
        }else{
            hopperTalon.set(0.0);
        }

        if(driveJoystick.getRawButton(6)){
            talon4.set(0.5);
        }else{
            talon4.set(0.0);
        }

        if(driveJoystick.getRawButtonPressed(7)){
            toggleSolenoid(intakeSolenoid);
        }

        if(driveJoystick.getRawButtonPressed(9)){
            toggleSolenoid(flipperBigSolenoid);
        }

        if(driveJoystick.getRawButtonPressed(10)){
            toggleSolenoid(shooterAngleSolenoid);
        }

        if(driveJoystick.getRawButtonPressed(11)){
            toggleSolenoid(climberSolenoid);
        }

        if(driveJoystick.getRawButtonPressed(12)){
            toggleSolenoid(colorWheelSolenoid);
        }
    }

    private void toggleSolenoid(final DoubleSolenoid solenoid){
        if(solenoid.get() == DoubleSolenoid.Value.kForward){
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }else{
            solenoid.set(DoubleSolenoid.Value.kForward);
        }
    }
}
