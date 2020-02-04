package team1699.subsystems;

public class ColorMatcherState {
    private String previousColor;
    private String currentColor;

    //TODO Make sure this is what Seth meant
    public ColorMatcherState(String currentColor){
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