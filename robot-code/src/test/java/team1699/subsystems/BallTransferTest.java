package team1699.subsystems;

import org.junit.Before;
import org.junit.Test;
import team1699.utils.controllers.BetterSpeedController;
import team1699.utils.controllers.TestSpeedController;
import team1699.utils.sensors.BeamBreak;
import team1699.utils.sensors.simsensors.SimBeamBreak;

import static org.junit.Assert.assertEquals;

public class BallTransferTest {

    BetterSpeedController testController;
    SimBeamBreak testBallBreak, testShooterBreak, testIntakeBreak;
    Hopper hopper;

    @Before
    public void setupTest(){
        testController = new TestSpeedController(0);
        testBallBreak = new SimBeamBreak(0);
        testShooterBreak = new SimBeamBreak(1);
        testIntakeBreak = new SimBeamBreak(3);
        hopper = new Hopper(testIntakeBreak, testBallBreak, testShooterBreak, testController);
    }

    @Test
    public void testCorrectHopperMovement(){
        hopper.setWantedState(Hopper.HopperState.INTAKING);
        hopper.update();
        assertEquals(0.0, testController.get(), 0.0);
        testIntakeBreak.setTriggered(BeamBreak.BeamState.BROKEN);
        hopper.update();
        assertEquals(Hopper.FORWARD_SPEED, testController.get(), 0.0);
        testBallBreak.setTriggered(BeamBreak.BeamState.BROKEN);
        hopper.update();
        assertEquals(Hopper.FORWARD_SPEED, testController.get(), 0.0);
        testIntakeBreak.setTriggered(BeamBreak.BeamState.CLOSED);
        testBallBreak.setTriggered(BeamBreak.BeamState.CLOSED);
        hopper.update();
        assertEquals(0.0, testController.get(), 0.0);
    }
}