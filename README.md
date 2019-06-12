
CS 313: Data Structures at Queens College
===

## Table of Contents
1. [Project 1: Polynomial Arithmetic](#Project-1:-Polynomial-Arithmetic)
2. [Project 2: Polynomial Arithmetic with Hash Maps](#Project-2:-Polynomial-Arithmetic-with-Hash-Maps)
3. [Project 3: Emergency Room](#Project-3:-Emergency-Room)

## Project 1: Polynomial Arithmetic
#### Project Specs:

You have been hired by a math teacher to help write a program that will read in any two polynomials and will be able
to do arithmetic with them. They want addition, subtraction, and multiplication for sure. If you can and have time,
they would pay more for division to also be implemented.

The input will be two polynomials in string form:
“X^5+2X^2+3X^3+4X^4”
“2X^2+4X”
The output will should be:
Sum: X^5 + 4.0 X^4 + 3.0 X^3 + 4.0 X^2 + 4.0 X
Difference: X^5 + 4.0 X^4 + 3.0 X^3 -4.0 X
Product: 2.0 X^7 + 12.0 X^6 + 22.0 X^5 + 16.0 X^4 + 8.0 X^3
Quotient: 0.5 X^3 + X^2 -0.5 X + 2.0
Remainder: -8.0 X

## Project 2: Polynomial Arithmetic with Hash Maps
This project implements Project 1, but with Hash Maps instead of custom Linked Lists

## Project 3: Emergency Room
#### Project Specs:
You have been hired by a Director of Medicine at New York Presbyterian. She want you to implement
an software that allows the ER nurses and staff to submit information about a patient and the
algorithms in the software will return a triage level.

You are given 4 text files:
1.Patient.txt
a) A list of patients that enter the ER, there are 15 patients
b) Each line is a different patient, each attribute is comma separated
c) The format is in the following order:
i) Name
ii) Age
iii) Gender
iv) Complaint
v) Alertness Level
vi) Heart Rate
vii) Blood Pressure
viii) Respiration Rate
ix) Temperature
x) Oxygen Saturation
xi) Pain Level
xii) Remaining items is a list of medications taking

2.Cardiac.txt
a) A list of different common cardiac medication
b) If the patient is taking any of these medications, assign them a cardiologist
3.Cancer.txt
a) A list of different common cancer treatment or pain management
b) If the patient is taking any of these medications, assign them a oncologist
4.Neuro.txt
a) A list of different common neurological medication
b) If the patient is taking any of these medications, assign them a neurologist

Your program should write a file for each patient. The name for these files should be the number at
which the are seen by the doctor in the ER. So there should be 15 files and the names of the files should
be from 1- 15. For example, 1.txt is the first patient to been seen by the doctor in the ER, that person is
the first person that came in with the highest triage level. 15.txt is the last patient to be seen by the
doctor, this person that the lowest triage level.
Each file should have the following information:
- Patient name
- Age, Gender, Complaint
- Triage Level
- Doctor assigned {cardiologist, oncologist, neurologist}, if no doctor assigned used ‘N/A’
- Time spent waiting (ms)
- Vital Sign Assessment
- HR: Bradycardia/Tachycardia/Normal
- BP: Hypotension/Hypertension/Normal
- RR: Bradypnea/ Tachypnea/ Normal
- Temperature : Yes/no (Fever)
- SO2: Low/Normal
- List of medication


Example 1.txt
Morris Love
46, Male, Chest Pain
1
Cardiologist
1202ms
153 Tachycardia
150/45 Hypertension
26 tachypnea
96.1 No
85% Low
Warfarin, Aspirin, Amlodipine, Diltiazem

#### Reading Vital Signs










