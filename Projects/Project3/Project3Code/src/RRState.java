/**
 * This class contains an enum for the diagnosis of a patient's respiratory state given the respiratory rate in
 * breaths per minute
 * minute
 *
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
public enum RRState
{
    NORMAL("Normal"), BRADYPNEA("Bradypnea"), TACHYPNEA("Tachypnea");

    private String stateName;
    RRState(String state)
    {
        this.stateName = state;
    }

    @Override
    public String toString(){
        return stateName;
    }
}
