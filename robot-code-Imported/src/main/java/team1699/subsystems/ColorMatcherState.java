package team1699.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

public class ColorMatcherState {
    private String previousColor;
    private String currentColor;

    public void ColorMatcherState()
    {
        this.currentColor = currentColor;
        this.previousColor = "Unknown";
    } 

    public void Run(){
        switch(previousColor){

            case "Unknown":
                
                break;

            case "Blue":

                break;

            case "Green":

                break;

            case "Red":

                break;

            case "Yellow":

                break;

        }
    }
} 