/**
 * This class contains an enum for the diagnosis of a patient's Body Temperature vitals
 *
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
public enum BodyTemperatureState
{
    NO("No"), YES("Yes");

    private String stateName;
    BodyTemperatureState(String state)
    {
        this.stateName = state;
    }

    @Override
    public String toString(){
        return stateName;
    }
}
