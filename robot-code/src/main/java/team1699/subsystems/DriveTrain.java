package team1699.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team1699.utils.controllers.SpeedControllerGroup;
import team1699.utils.controllers.falcon.BetterFalcon;

public class DriveTrain extends SubsystemBase implements Subsystem{

	//Constants TODO Change https://docs.wpilib.org/en/latest/docs/software/examples-tutorials/trajectory-tutorial/entering-constants.html
	public static final double ksVolts = 0.22;
	public static final double kvVoltSecondsPerMeter = 1.98;
	public static final double kaVoltSecondsSquaredPerMeter = 0.2;
	public static final double kPDriveVel = 8.5;
	public static final double kTrackwidthMeters = 0.69;
	public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
	public static final double kMaxSpeedMetersPerSecond = 3;
	public static final double kMaxAccelerationMetersPerSecondSquared = 3;
	public static final double kRamseteB = 2;
	public static final double kRamseteZeta = 0.7;

	private final SpeedControllerGroup portDrive, starDrive;

	private final AHRS gyro;

	private final DifferentialDriveOdometry odometry;



	private final Joystick joystick;

	//Should only be used for tests
	public DriveTrain(final SpeedControllerGroup portDrive, final SpeedControllerGroup starDrive, final Joystick joystick){
		this.portDrive = portDrive;
		this.starDrive = starDrive;
		this.joystick = joystick;
		this.gyro = null;
		odometry = null;
	}


	public DriveTrain(final SpeedControllerGroup portDrive, final SpeedControllerGroup starDrive, final Joystick joystick, final AHRS gyro){
		this.portDrive = portDrive;
		this.starDrive = starDrive;
		this.joystick = joystick;
		this.gyro = gyro;

		((BetterFalcon) portDrive.getMaster()).resetEncoders();
		((BetterFalcon) starDrive.getMaster()).resetEncoders();
		odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading())); //TODO Check get angle
	}

	@Override
	public void periodic() {
		odometry.update(Rotation2d.fromDegrees(getHeading()), ((BetterFalcon) portDrive.getMaster()).getEncoder(), ((BetterFalcon) starDrive.getMaster()).getEncoder());
	}

	public Pose2d getPose(){
		return odometry.getPoseMeters();
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds(){
		return new DifferentialDriveWheelSpeeds(((BetterFalcon) portDrive.getMaster()).getEncoderRate(), ((BetterFalcon) starDrive.getMaster()).getEncoderRate());
	}

	public void resetOdometry(Pose2d pose) {
		((BetterFalcon) portDrive.getMaster()).resetEncoders();
		((BetterFalcon) starDrive.getMaster()).resetEncoders();
		odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
	}

	public void tankDriveVolts(final double leftVolts, final double rightVolts){
		portDrive.set(leftVolts);
		starDrive.set(rightVolts);
	}

	public double getAverageEncoderDistance(){
		return (((BetterFalcon) portDrive.getMaster()).getEncoder() + ((BetterFalcon) starDrive.getMaster()).getEncoder()) / 2.0;
	}

	public void zeroHeading(){
		gyro.zeroYaw(); //TODO Check
	}

	public double getHeading(){
		return Math.IEEEremainder(gyro.getAngle(), 360); //TODO Add a way to reverse gyro direction
	}

	public double getTurnRate(){
		return gyro.getRate(); //TODO Add a way to reverse gyro
	}

	public void update(){
		//TODO Check correct joystick axis
		runArcadeDrive(joystick.getX(), joystick.getY());
	}

	//WPILib Differential Drive
	protected void runArcadeDrive(double throttle, double rotate){
		double portOutput = 0.0;
		double starOutput = 0.0;

		//TODO add deadband
		throttle = Math.copySign(throttle * throttle, throttle);
		rotate = Math.copySign(rotate * rotate, rotate);
		
		double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotate)), throttle);

		if(throttle >= 0.0){
			////First quadrant, else second quadrant
			if(rotate >= 0.0){
				portOutput = maxInput;
				starOutput = throttle - rotate;
			}else{
				portOutput = throttle + rotate;
				starOutput = maxInput;
			}
		}else{
			if(rotate >= 0.0){
				portOutput = maxInput;
				starOutput = throttle - rotate;
			}else{
				portOutput = throttle + rotate;
				starOutput = maxInput;
			}
		}

		portDrive.set(portOutput);
		starDrive.set(starOutput);
	}

	public Command getAutonomousCommand(final Trajectory trajectory){
		var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(ksVolts, kaVoltSecondsSquaredPerMeter, kMaxAccelerationMetersPerSecondSquared), kDriveKinematics, 10);
		TrajectoryConfig config = new TrajectoryConfig(kMaxSpeedMetersPerSecond, kMaxAccelerationMetersPerSecondSquared).setKinematics(kDriveKinematics).addConstraint(autoVoltageConstraint);
		RamseteCommand ramseteCommand = new RamseteCommand(
				trajectory,
				this::getPose,
				new RamseteController(kRamseteB, kRamseteZeta),
				new SimpleMotorFeedforward(ksVolts, kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter),
				kDriveKinematics,
				this::getWheelSpeeds,
				new PIDController(kPDriveVel, 0, 0),
				new PIDController(kPDriveVel, 0, 0),
				this::tankDriveVolts,
				this //TODO Check
		);
		return ramseteCommand.andThen(() -> tankDriveVolts(0, 0));
	}
}
