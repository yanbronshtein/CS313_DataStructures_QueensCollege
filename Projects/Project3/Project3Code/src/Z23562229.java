import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * This project aims at implementing an emergency room.
 * This class contains the main method that drives the program.
 * In addition the class is responsible for validating the 4 command line arguments which are text file names that
 * must be located in the working directory,
 * parsing each of the files, inserting the parsed patients into a priority patientQueue, and then creating patient reports
 * for each patient in the working directory
 * @author Yaniv Bronshtein
 * @version 1.0
 *
 * */
public class Z23562229
{
    /**Java Priority Queue used to store the patients as if the patients were in an emergency room */
    private static PriorityQueue<Patient> patientQueue = new PriorityQueue<>();

    /**Array of hashsets containing the medications found in each of the files cardio.txt, cancer.txt, and neuro.txt */
    private static HashSet[] specialistMedications = new HashSet[3];

    /**Main method:
     * 1. Calls validateInput() to parse and validate command line argument
     * 2. Calls readMedicationFile() on all the medication files and saves the contents into specialistMedications
     * HashSet Array
     * 3. Calls readPatientFile() to read and parse the patient file and add the patients to the patientQueue
     * 4. Removes all patients from the queue and generates a unique text file for each patient by calling
     * createPatientReport()
     *
     * @param args 4 command line arguments patient.txt, cardio.txt, cancer.txt, neuro.txt
     *
     * * */
    public static void main(String[] args)
    {
        try
        {
            validateInput(args);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            readPatientFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        int patientCount = 1;
        while (!patientQueue.isEmpty())
        {
            Patient patient = patientQueue.poll(); //Remove patient from the queue
            long timeSeen = System.currentTimeMillis(); //Record the current time
            long waitingTime = timeSeen - patient.timeAdmitted; //Calculate patient wait time
            try
            {
                createPatientReport(patient,waitingTime,patientCount);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            patientCount++;
        }

    }

    private static void validateInput(String[] args) throws IOException
    {
        if (args.length != 4) throw new IllegalArgumentException("There must be four files supplied");

        int matchingFileCount = 0;
        for (String fileName : args)
        {

            switch (fileName)
            {
                case "cardiac.txt":
                    readMedicationFile(fileName,0);
                    matchingFileCount++;
                    break;
                case "cancer.txt":
                    readMedicationFile(fileName,1);
                    matchingFileCount++;
                    break;
                case "neuro.txt":
                    readMedicationFile(fileName,2);
                    matchingFileCount++;
                    break;
                case "patient.txt":
                    matchingFileCount++;
                    break;
                default:
                    throw new IOException("The file" + fileName + "is either an invalid file name or the wrong file");
            }
        }

        if (matchingFileCount != 4) throw new IOException("Not all 4 necessary files are present");
    }

    /**This method reads a medication file. Each medication is added to a hashSet which is then stored in the
     * specialistMedications HashSet array as determined by the
     * specialistID:(0 = cardiologist, 1 = oncologist, 2 = neurologist)
     * @param fileName Name of text file
     * @param specialistID array index where to store medications in
     * @throws IOException see BufferedReader class
     **/
    private static void readMedicationFile(String fileName, int specialistID) throws IOException
    {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        HashSet<String> medications = new HashSet<>();


        while ((st = br.readLine()) != null)
            medications.add(st.trim());
        br.close();
        specialistMedications[specialistID] = medications;
    }

    /**This method reads the patient.txt file, calls parsePatient() to parse the patient record,
     * and then inserts the returned patient into the patient queue
     * @throws IOException see BufferedReader class
     * */
    private static void readPatientFile() throws IOException
    {
        File file = new File("patient.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
        {
            Patient newPatient = parsePatient(st);
            try
            {
                Thread.sleep(10); //Add delay in between patients to ensure that compareTo() in Patient.java works
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            patientQueue.add(newPatient);

        }
        br.close();

    }

    /**This method parses a patient record and returns a patient object created from the input
     * @param line patient record
     * @return newPatient
     *
     * */
    private static Patient parsePatient(String line)
    {
        String name, complaint, genderStr, alertnessLevelStr;
        HashSet<String> patientMedicationSet = new HashSet<>();
        ArrayList<String> patientMedicationOriginalList = new ArrayList<>();
        int age, heartRate, respirationRate, painLevel;
        int[] bloodPressure = new int[2];
        double bodyTemperature, bloodOxygenSaturation;
        boolean[] assignedSpecialistArray;

        //Remove leading and trailing hidden characters from line in file and then split by comma
        String[] tokenized = line.trim().split(",");

        if (tokenized.length < 12) throw new RuntimeException("Improperly formatted patient history");
        /*For each token it is necessary to trim the token before saving it */
        //Get name
        name = tokenized[0].contains("-") ? "" : tokenized[0].trim();

        //Get age
        if (tokenized[1].contains("-"))
            age = -1;
        else
            try
            {
                age = Integer.parseInt(tokenized[1].trim());
            } catch (NumberFormatException e)
            {
                throw new NumberFormatException(tokenized[1] + " is an Invalid Age\n");
            }

        //Get gender
        genderStr = tokenized[2].contains("-") ? "" : tokenized[2].trim();

        //Get complaint
        complaint = tokenized[3].contains("-") ? "" : tokenized[3].trim();

        //Get alertnessLevel
        alertnessLevelStr = tokenized[4].contains("-") ? "" : tokenized[4].trim();

        //Get heart rate
        if (tokenized[5].contains("-"))
            heartRate = -1;
        else
            try
            {
                heartRate = Integer.parseInt(tokenized[5].trim());
            } catch (NumberFormatException e)
            {
                throw new NumberFormatException(tokenized[5] + " is an Invalid heart rate\n");
            }


        //Get blood pressure
        if (tokenized[6].contains("-"))
        {
            bloodPressure[0] = -1;
            bloodPressure[1] = -1;
        } else
        {
            String[] bloodPressureTokens = tokenized[6].trim().split("/");
            try
            {
                bloodPressure[0] = Integer.parseInt(bloodPressureTokens[0].trim());
                bloodPressure[1] = Integer.parseInt(bloodPressureTokens[1].trim());

            } catch (NumberFormatException e)
            {
                throw new NumberFormatException(tokenized[6] + " is an invalid Blood Pressure");
            }
        }

        //Get respiration rate
        if (tokenized[7].contains("-"))
            respirationRate = -1;
        else
            try
            {
                respirationRate = Integer.parseInt(tokenized[7].trim());
            } catch (NumberFormatException e)
            {
                throw new NumberFormatException(tokenized[7] + "is an invalid Respiration Rate\n");
            }

        //Get body temperature
        if (tokenized[8].contains("-"))
            bodyTemperature = -1.0;
        else
            try
            {
                bodyTemperature = Double.parseDouble(tokenized[8].trim());
            } catch (NumberFormatException e)
            {
                throw new NumberFormatException(tokenized[8] + "is an invalid Body Temperature\n");
            }

        //Get blood oxygen saturation
        if (tokenized[9].contains("-"))
            bloodOxygenSaturation = -1.0;
        else
        {
            String percentStr = tokenized[9].replaceAll("%", "").trim();
            try
            {
                bloodOxygenSaturation = Double.parseDouble(percentStr);
            } catch (NumberFormatException e)
            {
                throw new NumberFormatException(tokenized[9] + " is an invalid Blood Oxygen Saturation");
            }
        }

        //Get PAIN level
        if (tokenized[10].contains("-"))
            painLevel = -1;
        else
            try
            {
                painLevel = Integer.parseInt(tokenized[10].trim());

            } catch (NumberFormatException e)
            {
                throw new NumberFormatException(tokenized[10] + "is an invalid pain level\n");
            }


        //Add Patient Medications
        if (!tokenized[11].contains("-"))
            for (int i = 11; i < tokenized.length; i++)
            {
                patientMedicationSet.add(tokenized[i].trim());
                patientMedicationOriginalList.add(tokenized[i].trim());

            }


        assignedSpecialistArray = assignSpecialist(patientMedicationSet);

        return new Patient(name,age,genderStr,complaint,alertnessLevelStr,heartRate,bloodPressure,
                respirationRate,bodyTemperature,bloodOxygenSaturation,painLevel,patientMedicationOriginalList,
                assignedSpecialistArray);
    }

    /**This method computes the intersection between the specific medications that the patient is taking and those
     * that would warrant assigning the patient to one of the three specialists
     * If the intersection contains a HashSet containing at least one medication, then the specialist is assigned to
     * the patient
     * @param patientMedication HashSet containing all the medications found in the patient record
     * @return assignedSpecialistArray */
    private static boolean[] assignSpecialist(HashSet<String> patientMedication)
    {
        boolean[] assignedSpecialistArray = {false, false, false};
        for (int i = 0; i < 3; i++)
        {
            HashSet<String> intersection = new HashSet<>(patientMedication); // use the copy constructor
            intersection.retainAll(specialistMedications[i]);
            if (!intersection.isEmpty()) assignedSpecialistArray[i] = true;
        }

        return assignedSpecialistArray;

    }

    /**
     * This method creates a unique patient file. The name of the file created corresponds to its rank:
     * (1.txt means that that particular patient was seen by a doctor first)
     * @param patient patient in questions
     * @param waitingTime the calculated time the patient spent waiting to be seen by the doctor
     * @param patientCount rank of patient
     * */
    private static void createPatientReport(Patient patient, long waitingTime, int patientCount) throws IOException
    {
        FileWriter fileWriter = new FileWriter(Integer.toString(patientCount) + ".txt");
        String fileContent =
                patient.getName() + "\n" +
                        patient.getAge() + ", " + patient.getGender() + ", " +  patient.getComplaint() + "\n" +
                        Integer.toString(patient.getTriageLevel()) + "\n" +
                        patient.getAssignedSpecialistsAsString() + "\n" +
                        Long.toString(waitingTime) + "ms\n" +
                        Integer.toString(patient.getHR()) + " " + patient.getHrState().toString() + "\n" +
                        Integer.toString(patient.getBP()[0]) + "/" + Integer.toString(patient.getBP()[1]) + " " +
                        patient.getBpState().toString() + "\n" +
                        Integer.toString(patient.getRR()) + " " + patient.getRrState() + "\n" +
                        Double.toString(patient.getBodyTemperature()) + " " + patient.getBodyTemperatureState().toString() +
                        "\n" +
                        Double.toString(patient.getSO2()) + "% " + patient.getSo2State().toString() + "\n" +
                        patient.getPatientMedicationAsString() + "\n";
        fileWriter.write(fileContent);
        fileWriter.close();

    }



}


