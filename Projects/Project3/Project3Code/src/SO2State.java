/**
 * This class contains an enum for the diagnosis of a patient's Blood Oxygen Saturation given the value as a percentage
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
public enum SO2State
{
    NORMAL("Normal"), LOW("Low"), DANGER_ZONE("Danger Zone");

    private String stateName;
    SO2State(String state)
    {
        this.stateName = state;
    }

    @Override
    public String toString(){
        return stateName;
    }
}
