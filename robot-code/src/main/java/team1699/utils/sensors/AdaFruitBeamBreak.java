package team1699.utils.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class AdaFruitBeamBreak extends BeamBreak {

    private final DigitalInput beamBreak;
    private BeamState beamState;

    public AdaFruitBeamBreak(int port) {
        super(port);
        beamBreak = new DigitalInput(port);
        if(!beamBreak.get()){
            beamState = BeamState.ERROR;
        }else{
            beamState = BeamState.CLOSED;
        }
    }

    @Override
    public BeamState triggered() {
        if(beamBreak.get()){
            beamState = BeamState.BROKEN;
        }else{
            beamState = BeamState.CLOSED;
        }
        return beamState;
    }
}
