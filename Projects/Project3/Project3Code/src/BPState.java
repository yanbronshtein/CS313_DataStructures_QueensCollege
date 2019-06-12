/**
 * This class contains an enum for the diagnosis of a patient's Blood Pressure vitals
 *
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
public enum BPState
{
    NORMAL("Normal"), HYPERTENSION("Hypertension"), HYPOTENSION("Hypotension");

    private String stateName;
    BPState(String state)
    {
        this.stateName = state;
    }

    @Override
    public String toString(){
        return stateName;
    }


}
