/**
 * This class describes a patient that has entered the emergency room.
 *
 *
 * @author Yaniv Bronshtein
 * @version 1.0
 * */

import java.util.ArrayList;
import java.util.HashSet;

public class Patient implements Comparable<Patient>
{
    /**Full name of patient */
    private String name;
    /**Integer age of the patient */
    private int age;
    /**Gender of the patient */
    private String gender;
    /**Complaint made by patient/on behalf of the patient  */
    private String complaint;
    /*Alertness Level of Patient as string: One of 4 possible letters(A,V,P,U) */
    private String alertnessLevelStr;
    /**Patient heart rate in beats per minute */
    private int HR;
    /**2 element array where BP[0]: systolic pressure and BP[1]: diastolic pressure */
    private int[] BP;
    /**Patient Respiratory Rate measured in breaths per minute */
    private int RR;
    /**Patient Body temperature measured in Degrees Fahrenheit*/
    private double bodyTemperature;
    /** Blood Oxygen Saturation or the concentration of oxygen the blood is carrying */
    private double SO2;
    /**Pain Level as provided by patient on a scale of 1-10, where 1 is the lowest and 10 is the highest */
    private int painLevel;
    /**ArrayList containing the medications the patient is taking  */
    private ArrayList<String> patientMedicationOriginalList;
    /**3 element boolean array corresponding to whether a certain specialist has been assigned to a patient
     * Index 0: Cardiologist
     * Index 1: Oncologist
     * Index 2: Neurologist */
    private boolean[] assignedSpecialistArray;
    /**Heart state enum variable */
    private HRState hrState;
    /**Blood Pressure state enum variable */
    private BPState bpState;
    /**Respiration Rate State enum variable */
    private RRState rrState;
    /**Temperature State enum variable */
    private BodyTemperatureState temperatureState;
    /**SO2 State enum variable */
    private SO2State so2State;
    /**AlertnessLevel enum variable */
    private AlertnessLevel alertnessLevel;
    /**Triage Level of patient. Determines patient priority. Integer value in the range [1,3]. 1 is highest priority
     * and 3 is lowest priority */
    private int triageLevel;
    /**Time stamp of when the patient has been admitted in milliseconds */
    long timeAdmitted;

    /**Constructs a patient given the parameters parsed from file labeled "patient.txt"
     * Calls setTriageLevel() to calculate the priority of a patient to be inserted into the priority queue
     * @param name Name of patient
     * @param age Age of patient
     * @param gender Gender of patient
     * @param complaint Complaint provided by patient/on behalf of patient
     * @param alertnessLevelStr String character of patient alertness level
     * @param HR patient Heart Rate in bpm
     * @param BP array containing patient heart rate
     * @param RR patient respiratory rate
     * @param SO2 patient Blood Oxygen Saturation Rate
     * @param painLevel patient self-reported pain level
     * @param patientMedicationOriginalList List of patient medications preserving original order for printing
     * @param assignedSpecialistArray Array of booleans listing assigned specialists of the patient
*
*           */

    //todo: Store patient medication in proper order for printing
    public Patient(String name, int age, String gender, String complaint, String alertnessLevelStr, int HR,
                   int[] BP, int RR,
                   double bodyTemperature, double SO2, int painLevel,
                   ArrayList<String> patientMedicationOriginalList, boolean[] assignedSpecialistArray)
    {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.complaint = complaint;
        this.alertnessLevelStr = alertnessLevelStr;
        this.HR = HR;
        this.BP = BP;
        this.RR = RR;
        this.bodyTemperature = bodyTemperature;
        this.SO2 = SO2;
        this.painLevel = painLevel;
        this.patientMedicationOriginalList = patientMedicationOriginalList;
        this.assignedSpecialistArray = assignedSpecialistArray;
        timeAdmitted = System.currentTimeMillis(); //create timestamp
        setTriageLevel();
    }

    /**This method returns the name of the patient
     * @return name */
    public String getName()
    {
        return name;
    }

    /**This method returns the age of the patient
     * @return age */
    public int getAge()
    {
        return age;
    }

    /**This method returns the gender of the patient
     * @return gender */
    public String getGender()
    {
        return gender;
    }

    /**This method returns the complaint of the patient
     * @return complaint */
    public String getComplaint()
    {
        return complaint;
    }
    /**This method initializes the enum for the Heart State given the heart rate in bpm
     * */
    private void setHeartState()
    {
        if (HR >= 30 && HR < 60) hrState = HRState.BRADYCARDIA;
        else if (HR > 100 && HR < 150) hrState = HRState.TACHYCARDIA;
        else if (HR > 150 || HR < 30) hrState = HRState.HEART_ATTACK;
        else hrState = HRState.NORMAL;
    }

    /**This method returns the enum variable for the heart state
     * @return HRState */
    public HRState getHrState()
    {
        if (HR > 100) return HRState.TACHYCARDIA;
        else if (HR < 60) return HRState.BRADYCARDIA;
        else return HRState.NORMAL;
    }

    /**This method returns the integer value of the heart rate in bpm
     * @return HR */
    public int getHR()
    {
        return HR;
    }

    /**This method initializes the enum for the Blood Pressure given the patient systolic and diastolic blood
     * pressure
     * */
    private void setBpState()
    {
        int systolic = BP[0], diastolic = BP[1];

        if (systolic > 140 || diastolic > 90)
            bpState = BPState.HYPERTENSION;
        else if (systolic <= 90 || diastolic <= 60)
            bpState = BPState.HYPOTENSION;
        else
            bpState = BPState.NORMAL;
    }

    /**This method returns the enum variable for the blood pressure state
     * @return bpState */
    public BPState getBpState()
    {
        return bpState;
    }

    /**This method returns the blood pressure as an array of two integers
     * @return BP */
    public int[] getBP()
    {
        return BP;
    }

    /**This method sets the enum variable for the respiratory state*/
    private void setRrState()
    {
        if (RR >= 16 && RR <= 20)
            rrState = RRState.NORMAL;
        else if (RR < 16)
            rrState = RRState.BRADYPNEA;
        else
            rrState = RRState.TACHYPNEA;
    }

    /**This method returns the enum variable for the respiratory state
     * @return rrState */
    public RRState getRrState()
    {
        return rrState;
    }

    /**This method returns the integer value of the respiratory rate
     * @return RR */
    public int getRR()
    {
        return RR;
    }

    /**This method sets the enum variable for the temperature state */
    private void setBodyTemperatureState()
    {
        if (bodyTemperature >= 99) temperatureState = BodyTemperatureState.YES;
        else temperatureState = BodyTemperatureState.NO;
    }

    /**This method returns the enum variable for the body temperature state
     * @return temperatureState */
    public BodyTemperatureState getBodyTemperatureState()
    {
        return temperatureState;
    }

    /** This method returns the body temperature in degrees fahrenheit
     * @return bodyTemperature */
    public double getBodyTemperature()
    {
        return bodyTemperature;
    }

    /**This method sets the SO2 enum variable */
    private void setSo2State()
    {
        if (SO2 >= 95 && SO2 <= 100)
            so2State = SO2State.NORMAL;
        else if (SO2 < 95 && SO2 >= 90)
            so2State = SO2State.LOW;
        else
            so2State = SO2State.DANGER_ZONE;
    }

    /**This method returns the SO2 state but not the actual value of the instance variable
     * @return SO2State */
    public SO2State getSo2State()
    {
        if (so2State == SO2State.DANGER_ZONE || so2State == SO2State.LOW) return SO2State.LOW;
        else return SO2State.NORMAL;
    }

    /**This method returns the SO2 level as a percentage
     * @return SO2 */
    public double getSO2()
    {
        return SO2;
    }

    /**This method sets the AlertnessLevel enum variable */
    private void setAlertnessLevel()
    {
        switch (alertnessLevelStr)
        {
            case "A":
                alertnessLevel = AlertnessLevel.ALERT;
                break;
            case "V":
                alertnessLevel = AlertnessLevel.VERBAL;
                break;
            case "P":
                alertnessLevel = AlertnessLevel.PAIN;
                break;
            case "U":
                alertnessLevel = AlertnessLevel.UNRESPONSIVE;
                break;
        }

    }

    /**This method returns the alterness level enum variable */
    public AlertnessLevel getAlertnessLevel()
    {
        return alertnessLevel;
    }

    /**This method sets the triage level for a patient by first calling
     * all the methods to set the enums for all the fields necessary to determine the triage level
     * Then the integer value is set based on those enums*/
    private void setTriageLevel()
    {
        setAlertnessLevel();
        setHeartState();
        setBpState();
        setRrState();
        setBodyTemperatureState();
        setSo2State();

        if (hrState == HRState.HEART_ATTACK || bodyTemperature > 105 ||
                so2State == SO2State.DANGER_ZONE ||
                RR < 6 || bpState == BPState.HYPOTENSION ||
                alertnessLevel == AlertnessLevel.UNRESPONSIVE)
        {
            triageLevel = 1;
        }

        else if (alertnessLevel == AlertnessLevel.PAIN || alertnessLevel == AlertnessLevel.VERBAL ||
                so2State == SO2State.LOW || hrState == HRState.BRADYCARDIA || hrState == HRState.TACHYCARDIA ||
                rrState == RRState.BRADYPNEA || rrState == RRState.TACHYPNEA || bpState == BPState.HYPERTENSION)
        {
            triageLevel = 2;
        }

        else
            triageLevel = 3;
    }

    /**Returns the integer value of the triage
     * @return triageLevel */
    int getTriageLevel()
    {
        return triageLevel;
    }

    /**This method checks the boolean values in the assignedSpecialistArray and appends any and all specialists. If
     * no specialists have been assigned, the string "N/A" is returned
     * @return assignedSpecialistStr
     * */
    String getAssignedSpecialistsAsString()
    {
        String assignedSpecialistStr = "";

        if (!assignedSpecialistArray[0] && !assignedSpecialistArray[1] && !assignedSpecialistArray[2]) return "N/A";
        if (assignedSpecialistArray[0]) assignedSpecialistStr += "Cardiologist ";
        if (assignedSpecialistArray[1]) assignedSpecialistStr += "Oncologist ";
        if (assignedSpecialistArray[2]) assignedSpecialistStr += "Neurologist ";

        return assignedSpecialistStr;

    }

    /**This method extracts the medications in the HashSet of the patient medications and appends them to a string
     * After each medication, a comma followed by a space is insert. In the final string, the trailing comma is
     * removed */
    String getPatientMedicationAsString()
    {
        String medicationsStr = "";
        if (patientMedicationOriginalList.isEmpty()) return medicationsStr;
        for (String elem : patientMedicationOriginalList)
            medicationsStr += (elem + ", ");

        medicationsStr = medicationsStr.substring(0,medicationsStr.length()-2);

        return medicationsStr;
    }

    @Override
    public String toString()
    {
        return "Name: " + name + " Triage: " + triageLevel;
    }


    @Override
    public int compareTo(Patient o)
    {
        if (triageLevel < o.getTriageLevel())
            return -1;

        else if (triageLevel > o.getTriageLevel())
            return 1;

        else {
            if (timeAdmitted < o.timeAdmitted)
                return -1;
            else if (timeAdmitted > o.timeAdmitted)
                return 1;
            else
                return 0;
        }

    }
}
