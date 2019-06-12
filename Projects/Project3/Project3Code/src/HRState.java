/**
 * This class contains an enum for the diagnosis of a patient's heart state given the heart rate in beats per minute
 *
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
public enum HRState
{
    NORMAL("Normal"), BRADYCARDIA("Bradycardia"), TACHYCARDIA("Tachycardia"), HEART_ATTACK("Cardiac Arrest");

    private String stateName;
    HRState(String state)
    {
        this.stateName = state;
    }

    @Override
    public String toString(){
        return stateName;
    }

}

