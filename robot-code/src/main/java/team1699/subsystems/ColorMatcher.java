package team1699.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorMatcher {
    /**
     * Change the I2C port below to match the connection of your color sensor
     */
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    /**
     * A Rev Color Sensor V3 object is constructed with an I2C port as a parameter.
     * The device will be automatically initialized with default parameters.
     */
    private final ColorSensorV3 m_ColorSensor = new ColorSensorV3(i2cPort);
    /**
     * A Rev Color Match object is used to register and detect known colors. This
     * can be calibrated ahead of time or during operation.
     * 
     * This object uses a simple euclidian distance to estimate the closest match
     * with given confidence range.
     */
    private final ColorMatch m_colorMatcher = new ColorMatch();
    /**
     * Note: Any example colors should be calibrated as the user needs, these are
     * here as a basic example.
     */
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    /** original setting */
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); 

    public enum color{
        unknown,
        yellow,
        green,
        blue,
        red

    }

    private color mcurrentColor;

    //private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.600, 0.113); // no red/green boundary sees yellow
    //private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.400, 0.113);  // no red/green boundary sees yellow 


    // Rev Color threshold
    // blue 0.143, 0.427, 0.429
    // green 0.197, 0.561, 0.240
    // red 0.561, 0.232, 0.114
    // yellow 0.361, 0.524, 0.113

    public void init() {
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);

        m_colorMatcher.setConfidenceThreshold(0.80);
        mcurrentColor = color.unknown;
    }

    public color update() {
        /**
         * The method GetColor() returns a normalized color value from the sensor and
         * can be Useful if outputting the color to use an RGB LED or similar. To read
         * the raw color, or use GetRawColor().
         * 
         * The color sensor works best when within a few inches from an object in well
         * lit conditions (the built in LED is a big help here!). The farther an object
         * is the more light from the surroundings will bleed into the measurements and
         * make it difficult to accurately determine its color.
         */
        Color detectedColor = m_ColorSensor.getColor();
        /**
         * Run the color match algorithm on our detected color
         */
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

         if (match.color == kBlueTarget) {
             colorString = "Blue";
             mcurrentColor = color.blue;
         } else if (match.color == kRedTarget) {
             colorString = "Red";
             mcurrentColor = color.red;
         } else if (match.color == kGreenTarget) {
             colorString = "Green";
             mcurrentColor = color.green;
         } else if (match.color == kYellowTarget) {
             colorString = "Yellow";
             mcurrentColor = color.yellow;
         } else {
             colorString = "Unknown";
             mcurrentColor = color.unknown;
         }
        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the Sensor.
         */         
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
        return mcurrentColor;
    }
}