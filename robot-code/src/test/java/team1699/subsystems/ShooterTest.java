package team1699.subsystems;

import org.junit.Before;
import org.junit.Test;

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
        Shooter shooter = new Shooter(null); //TODO Add speed controller group
        shooter.setGoal(goal);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("dump.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write("# time, position, voltage, velocity, acceleration, goal, limitSensor, lastError\n");

        double currentTime = 0.0;
        while(currentTime < 30.0) {
            //TODO Change to get voltage for simController
            final double voltage = 0.0;
            //final double voltage = shooter.update(simShooter.getVelocity(), true);
            pw.write(String.format("%f, %f, %f, %f, %f, %f, %f\n", currentTime, simShooter.velocity, voltage, simShooter.velocity, simShooter.getAcceleration(voltage), shooter.filteredGoal, shooter.lastError));
            simulateTime(voltage, ShooterSim.kDt);
            currentTime += ShooterSim.kDt;
            pw.flush();
        }

        pw.close();

        assertEquals(simShooter.velocity, goal, 0.01);
    }

    void simulateTime(final double voltage, final double time){
        assertTrue(String.format("System asked for: %f volts which is greater than 12.0", voltage), voltage <= 12.0 && voltage >= -12.0);
        final double kSimTime = 0.0001;

        double currentTime = 0.0;
        while(currentTime < time){
            final double acceleration = simShooter.getAcceleration(voltage);
            simShooter.velocity += simShooter.velocity * kSimTime;
            simShooter.velocity += acceleration * kSimTime;
            currentTime += kSimTime;
            //Jakob: I don't this we need this because there is no positional limit on a shooter
//            if(simShooter.limitTriggered()){
//                assertTrue(simShooter.velocity > -0.05, String.format("System running at %f m/s which is less than -0.051", simShooter.velocity));
//            }
            //TODO Check units
            assertTrue(String.format("System is at %f rad/sec which is less than minimum velocity of %f", simShooter.velocity, Shooter.kMinVelocity), simShooter.velocity >= Shooter.kMinVelocity - 0.01);
            assertTrue(String.format("System is at %f rad/sec which is greater than the maximum velocity of %f", simShooter.velocity, Shooter.kMaxVelocity), simShooter.velocity <= Shooter.kMaxVelocity + 0.01);
        }
    }

    private static class ShooterSim{

        //TODO Change constants
        //Distance from center of rotation in meters
        static final double cg = 0.0;
        //Mass of Barrel Assembly in Kilograms
        static final double kMass = 20.0;
        //Gear Ratio
        static final double kG = 100.0 * 60/12;
        //Radius of pulley
        static final double kr = 0.25 * 0.0254 * 22.0 / Math.PI / 2.0;

        //Sample time
        public static final double kDt = 0.010;

        // V = I * R + ω / Kv
        // torque = Kt * I

        ShooterSim(){}

        private double velocity = 0.0;

        private double getAcceleration(final double voltage){
            return 0.0;
        }

        private double getVelocity(){
            return 0.0;
        }
    }

}
