package team1699.utils.sensors.simsensors;

import team1699.utils.sensors.BeamBreak;

public class SimBeamBreak extends BeamBreak {

    private BeamState triggered;

    public SimBeamBreak(int port) {
        super(port);
        triggered = BeamBreak.BeamState.CLOSED;
    }

    @Override
    public BeamState triggered() {
        return triggered;
    }

    public void setTriggered(final BeamState triggered) {
        this.triggered = triggered;
    }
}
