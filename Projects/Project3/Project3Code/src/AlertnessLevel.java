/**
 * This class contains an enum for the diagnosis of a patient's alertness level
 *
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
public enum AlertnessLevel
{
    ALERT("A"), VERBAL("V"), PAIN("P"), UNRESPONSIVE("U");

    private String stateName;

    AlertnessLevel(String state)
    {
        this.stateName = state;
    }

    @Override
    public String toString(){
        return stateName;
    }

}
