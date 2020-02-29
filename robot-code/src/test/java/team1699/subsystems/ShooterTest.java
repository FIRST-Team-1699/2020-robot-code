package team1699.subsystems;

import org.junit.Before;
import org.junit.Test;
import team1699.utils.MotorConstants;
import team1699.utils.controllers.SpeedControllerGroup;
import team1699.utils.controllers.TestSpeedController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShooterTest {

    private ShooterSim simShooter;
    final static double goal = 2 * Math.PI; //TODO Figure out units (prob rad/sec)

    @Before
    public void setupTest(){
        simShooter = new ShooterSim();
    }

    @Test
    public void testShooterModel(){
        SpeedControllerGroup testGroup = new SpeedControllerGroup(new TestSpeedController(1));
        Shooter shooter = new Shooter(testGroup, null, null); //TODO Add speed controller group
        shooter.setGoal(goal);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("dump.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return; //Return if something breaks
        }
        pw.write("# time, velocity, voltage, acceleration, goal, lastError\n");

        double currentTime = 0.0;
        while(currentTime < 30.0) {
            //TODO Change to get voltage for simController
            shooter.update(simShooter.getVelocity());
            final double voltage = testGroup.get();
            pw.write(String.format("%f, %f, %f, %f, %f, %f\n", currentTime, simShooter.velocity, voltage, simShooter.getAcceleration(voltage), shooter.filteredGoal, shooter.lastError));
            simulateTime(voltage, ShooterSim.kDt);
            currentTime += ShooterSim.kDt;
            pw.flush();
        }

        pw.close();

        assertEquals(goal, simShooter.velocity, 0.01);
    }

    void simulateTime(final double voltage, final double time){
        assertTrue(String.format("System asked for: %f volts which is greater than 12.0", voltage), voltage <= 12.0 && voltage >= -12.0);
        final double kSimTime = 0.0001;

        double currentTime = 0.0;
        while(currentTime < time){
            final double acceleration = simShooter.getAcceleration(voltage);
            //simShooter.velocity += simShooter.velocity * kSimTime;
            simShooter.velocity += acceleration * kSimTime;
            currentTime += kSimTime;
            //Jakob: I don't this we need this because there is no positional limit on a shooter
//            if(simShooter.limitTriggered()){
//                assertTrue(simShooter.velocity > -0.05, String.format("System running at %f m/s which is less than -0.051", simShooter.velocity));
//            }
            //TODO Check units
            //assertTrue(String.format("System is at %f rad/sec which is less than minimum velocity of %f", simShooter.velocity, Shooter.kMinVelocity), simShooter.velocity >= Shooter.kMinVelocity - 0.01);
            //assertTrue(String.format("System is at %f rad/sec which is greater than the maximum velocity of %f", simShooter.velocity, Shooter.kMaxVelocity), simShooter.velocity <= Shooter.kMaxVelocity + 0.01);
        }
    }

    private static class ShooterSim{

        //TODO Change constants
        //Gear Ratio
        static final double kG = 1;
        //Rotational Inertial of Flywheel
        static final double kI = 0.05;

        //Sample time
        public static final double kDt = 0.010;

        // V = I * R + ω / Kv
        // torque = Kt * I

        ShooterSim(){}

        private double velocity = 0.0;

        //TODO Think gearing forgotten
        private double getAcceleration(final double voltage){
//            System.out.println(String.format("Velocity: %f, Voltage: %f", velocity, voltage));
//            System.out.println("Accel: " + (voltage - ((velocity * kG)/(MotorConstants.MotorCIM.Kv))) * (MotorConstants.MotorCIM.Kt/(kI * MotorConstants.MotorCIM.kResistance)));
            return (voltage - ((velocity * kG)/(MotorConstants.MotorCIM.Kv))) * (MotorConstants.MotorCIM.Kt/(kI * MotorConstants.MotorCIM.kResistance));
        }

        private double getVelocity(){
            return velocity;
        }
    }

}
