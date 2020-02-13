package team1699.subsystems;

import team1699.utils.sensors.LimitSwitch;

public class ColorMatcherState {
    // private String previousColor;
    // private String currentColor;
    public enum wheelState {
        unknown, initilized, unk_yellow, norm_yellow, blue, red, green
    };

    private wheelState currentState;
    private ColorMatcher currentColor;
    private LimitSwitch hasContactSwitch;

    public ColorMatcherState(ColorMatcher currentColor, LimitSwitch hasContactSwitch) {
        this.currentColor = currentColor;
        this.hasContactSwitch = hasContactSwitch;
    }

    public void run() {
        if (!hasContactSwitch.isPressed()) {
            currentState = wheelState.unknown;
        } else {
            switch (currentState) {

            case unknown:
            default:
                currentState = wheelState.initilized;
                break;

            case initilized:
                if (currentColor.update() == ColorMatcher.color.blue) {
                    currentState = wheelState.blue;
                } else if (currentColor.update() ==  ColorMatcher.color.green) {
                    currentState = wheelState.green;
                } else if (currentColor.update() ==  ColorMatcher.color.red) {
                    currentState = wheelState.red;
                } else if (currentColor.update() ==  ColorMatcher.color.yellow) {
                    currentState = wheelState.unk_yellow;
                }
                break;

            case unk_yellow:
                if (currentColor.update() ==  ColorMatcher.color.blue) {
                    currentState = wheelState.blue;
                } else if (currentColor.update() ==  ColorMatcher.color.red) {
                    currentState = wheelState.red;
                }
                break;

            case norm_yellow:
                if (currentColor.update() ==  ColorMatcher.color.blue) {
                    currentState = wheelState.blue;
                }
                break;

            case blue:
                if (currentColor.update() ==  ColorMatcher.color.green) {
                    currentState = wheelState.green;
                }
                break;
            case red:
                if (currentColor.update() ==  ColorMatcher.color.yellow) {
                    currentState = wheelState.norm_yellow;
                }
                break;
            case green:
                if (currentColor.update() ==  ColorMatcher.color.green) {
                    currentState = wheelState.green;
                }
                break;
            }
        }
    }
}