import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial implements PolynomialInterface{

    /*Hashmap to store Polynomial */
    private HashMap<Integer, Double> polynomialMap;

    /**Constructs a Polynomial given a properly formatted Polynomial string
     * EG: 4X^2+3X, 4.0X^2+3X, 4+3
     *1. Create an empty linked list to store the terms of the Polynomial
     * 2. Remove any spaces in between the terms
     * 3. Tokenize the string using String.split() by the operators in between the terms. Attach those operators
     * to the term
     * 4.For each token
     *  a). Create a Matcher that categorizes the token(potential term) into 5 groups:
     *  Group 0: Entire match
     *  Group 1: Sign of term
     *  Group 2: Magnitude of coefficient
     *  Group 3: Variable (X in our case)
     *  Group 4: (^power)
     *
     *  5. Using the groups extract the coefficient and power, and adding to a hashmap
     */
    public Polynomial(String s)
    {
        polynomialMap = new HashMap<>();

        /*remove white space */
        String initialParse = s.replaceAll("\\s+","");

        /*Split by the operators leaving the signs attached to the terms */
        String[] tokenized = initialParse.split("(?=[+-])");
        //Regex pattern
        String patternStr = "([-+]?)([0-9]*\\.?[0-9]+)?(X)?([\\^][-+]?[0-9]+)?";
        Pattern pattern = Pattern.compile(patternStr);
        String[] groupArray = new String[5]; //Array to hold the groups created by the pattern matcher
        for (String token : tokenized)
        {
            Matcher m = pattern.matcher(token);
            if (m.find())
            {
                groupArray[0] = m.group(0); //Entire match
                groupArray[1] = m.group(1); //Sign
                groupArray[2] = m.group(2); //Magnitude of coefficient
                groupArray[3] = m.group(3); //X
                groupArray[4] = m.group(4); //^Power
            }
            double coefficient = 0.0;
            int power = 0;

            /*The groups are empty if the array cells contain an empty string or null */
            boolean group1Empty = (groupArray[1] == null) || (groupArray[1].isEmpty());
            boolean group2Empty = (groupArray[2] == null) || (groupArray[2].isEmpty());
            boolean group3Empty = (groupArray[3] == null) || (groupArray[3].isEmpty());
            boolean group4Empty = (groupArray[4] == null) || (groupArray[4].isEmpty());

            /*Extract coefficient */
            //Both sign and magnitude present
            if (!group1Empty && !group2Empty)
                coefficient = Double.parseDouble(groupArray[1] + groupArray[2]);
                //Sign not present but magnitude is present
            else if (group1Empty && !group2Empty)
                coefficient = Double.parseDouble(groupArray[2]);
                //Sign is present but magnitude is not present
            else if (!group1Empty)
            {
                if (groupArray[1].equals("-"))
                    coefficient = -1.0;
                if (groupArray[1].equals("+"))
                    coefficient = +1.0;
            }
            //Implicit 1
            else
                coefficient = +1.0;

            /*Extract power */
            //Power is not present
            if (!group3Empty && group4Empty)
                power = 1;
            //Power is present
            if (!group3Empty && !group4Empty)
            {
                String powerStr = groupArray[4].substring(1);
                power = Integer.parseInt(powerStr);
            }
            //Check for uniqueness. If key is not unique insert a new key value pair with the same key but a combined sum
            //of the two coefficients
            if (polynomialMap.containsKey(power))
                polynomialMap.put(power, polynomialMap.get(power) + coefficient);
            else
                polynomialMap.put(power, coefficient);
        }

    }



    /**Constructs an empty polynomial */
    public Polynomial()
    {
        polynomialMap = new HashMap<>();

    }

    /**returns the HashMap of terms
     * @return p
     * */
    public HashMap<Integer, Double> getPolynomialMap()
    {
        return polynomialMap;
    }

    /**sets the HashMap to a new one
     * @param Polynomial HashMap of terms
     * */
    public void setPolynomialMap(HashMap<Integer,Double> Polynomial)
    {
        this.polynomialMap = Polynomial;
    }




    /**
     * This method adds two polynomials(namely the one calling this instance method and the one passed as a parameter
     * and returns the resulting Polynomial
     * @param p Polynomial to be added
     * @return sum Polynomial
     * */
    public Polynomial add(Polynomial p)
    {
        /* If one of the polynomials is null the other is returned */
        if (this.getPolynomialMap().isEmpty()) return p;
        if (p.getPolynomialMap().isEmpty()) return this;
        Polynomial sumPolynomial = new Polynomial();

        HashMap<Integer, Double> sumMap = new HashMap<>(this.getPolynomialMap());

        for (Map.Entry<Integer, Double> entry : p.getPolynomialMap().entrySet())
        {
            if (sumMap.containsKey(entry.getKey()))
            {
                int sumKey = entry.getKey();
                double sumValue = sumMap.get(sumKey);
                sumMap.put(sumKey, sumValue + entry.getValue());
            }
            else
            {
                sumMap.put(entry.getKey(), entry.getValue());
            }
        }

        sumPolynomial.setPolynomialMap(sumMap);
        return sumPolynomial;
    }




    /**
     * This method subtracts two polynomials(namely the one calling this instance method and the one passed as a
     * parameter
     * and returns the resulting Polynomial. Subtraction is performed by calling addition on the the calling polynomialMap
     * and the negated Polynomial passed as a parameter
     * @param p Polynomial to be subtracted
     * @return difference Polynomial
     * */
    public Polynomial subtract(Polynomial p)
    {
        if (p.getPolynomialMap().isEmpty()) return this;
        String pString = p.toString();
        Polynomial pCopy;
        if (pString.length() == 0)
            pCopy = new Polynomial();
        else pCopy = new Polynomial(pString);
        /*Negate every term in p and perform Polynomial addition */

        HashMap<Integer,Double> pCopyMap = pCopy.getPolynomialMap();
        for (Map.Entry<Integer, Double> entry : pCopyMap.entrySet())
            entry.setValue(-entry.getValue());

        return this.add(pCopy);

    }







    /**
     * This method multiplies two polynomials(namely the one calling this instance method and the one passed as a
     * parameter
     * and returns the resulting Polynomial. Multiplication is performed by iterating through the two lists,
     * calling the multiplyNodes() method, adding the resulting nodes in a temporary Polynomial, and then calling the
     * add() method on the resultPolynomial
     * @param p Polynomial to be multiplied
     * @return product Polynomial
     * */

    public Polynomial multiply(Polynomial p)
    {
        HashMap<Integer,Double> thisMap = this.getPolynomialMap();
        HashMap<Integer,Double> pMap = p.getPolynomialMap();
        Polynomial productPolynomial = new Polynomial();
        for (Map.Entry<Integer,Double> thisEntry: thisMap.entrySet())
        {
            Polynomial tempPolynomial = new Polynomial();
            HashMap<Integer,Double> tempMap = tempPolynomial.getPolynomialMap();

            for (Map.Entry<Integer,Double> pEntry: pMap.entrySet() )
            {

                int newPower = thisEntry.getKey() + pEntry.getKey();
                double newCoefficient = thisEntry.getValue() * pEntry.getValue();
                tempMap.put(newPower,newCoefficient);
            }

            productPolynomial = productPolynomial.add(tempPolynomial);
        }

        return productPolynomial;

    }


    /**
     * This method divides two polynomials(namely the one calling this instance method and the one passed as a
     * parameter
     * and returns the resulting "whole" part Polynomial. Division is performed by calling
     * euclideanPolynomialDivision(p)
     * and returning the first element of the Polynomial array returned by that method
     * @param p Polynomial to be divided
     * @return quotient polynomial
     * */
    public Polynomial divide( Polynomial p) {

        Polynomial[] divisionResult = euclideanPolynomialDivision(p);
        if (divisionResult[0] == null)
            return new Polynomial();
        return divisionResult[0];
    }

    /**
     * This method divides two polynomials(namely the one calling this instance method and the one passed as a
     * parameter
     * and returns the resulting "fractional" part of the Polynomial. Remainder is performed by calling
     * euclideanPolynomialDivision(p)
     * and returning the second element of the Polynomial array returned by that method
     * @param p Polynomial to be divided
     * @return quotient Polynomial
     * */
    public Polynomial remainder(Polynomial p)
    {
        Polynomial[] divisionResult = euclideanPolynomialDivision(p);
        if (divisionResult[1] == null)
            return new Polynomial();



        return divisionResult[1];
    }


    /**This method implements Euclidean Polynomial division
     * @see <a href="https://en.wikipedia.org/wiki/Polynomial_greatest_common_divisor#Euclidean_division</a>
     * @param p Polynomial to be divided
     * @return Polynomial array containing quotient and remainder polynomials*/
    private Polynomial[] euclideanPolynomialDivision(Polynomial p)
    {

        if (p.getPolynomialMap().isEmpty()) throw new RuntimeException("Illegal division by zero");
        if (this.getPolynomialMap().isEmpty()) return new Polynomial[2];


        Polynomial remainderPolynomial = new Polynomial(this.toString());
        Polynomial quotientPolynomial = new Polynomial();

        HashMap<Integer, Double> rMap = remainderPolynomial.getPolynomialMap();
        HashMap<Integer, Double> pMap = p.getPolynomialMap();

        int orderP = Collections.max(pMap.keySet());
        int orderR = Collections.max(rMap.keySet());


        while(!rMap.isEmpty() && Collections.max(rMap.keySet()) >= orderP)
        {
            Polynomial sPolynomial = new Polynomial();
            HashMap<Integer,Double> sMap = sPolynomial.getPolynomialMap();

            int sPower = orderR - orderP;
            double sCoefficient = rMap.get(orderR) / pMap.get(orderP);
            sMap.put(sPower,sCoefficient);
            quotientPolynomial = quotientPolynomial.add(sPolynomial);
            Polynomial temp = sPolynomial.multiply(p);

            remainderPolynomial = remainderPolynomial.subtract(temp);


            remainderPolynomial.clean();
            rMap = remainderPolynomial.getPolynomialMap();
            orderR = Collections.max(rMap.keySet());
        }
        return new Polynomial[]{quotientPolynomial,remainderPolynomial};

    }






    /** This method converts the hashmap of terms into a string of the following format:
     * (Term1)(Operator)(Term2)....(TermN),
     * where TermI is comprised of (Coefficient)^(Power) with two special cases
     * The helper method formatTermEntry() is called to format each individual term
     * @return String to be printed
     *
     * */
    public final String toString()
    {
        //Store in a treeMap to print in descending order of powers
        TreeMap<Integer,Double> sortedPolynomial = new TreeMap<>(
                Comparator.reverseOrder());
        sortedPolynomial.putAll(polynomialMap);
        if (sortedPolynomial.isEmpty()) return "0.0";

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer,Double> entry : sortedPolynomial.entrySet())
        {
            //If the coefficient of the term is positive and is not the first term, we need to prepend a '+' sign
            if (entry.getValue() > 0.0 && !entry.equals(sortedPolynomial.firstEntry()))
                sb.append('+');
            String formattedEntry = formatTermEntry(entry);
            sb.append(formattedEntry);

        }

        return sb.toString();
    }


    /**This method clears the entries from the list that have a coefficient of 0 */
    public void clean()
    {
        HashMap<Integer,Double> map = this.getPolynomialMap();
        if (map.isEmpty()) return;
        for (Map.Entry<Integer,Double> entry: map.entrySet())
        {
            if (entry.getValue() == 0)
                map.remove(entry.getKey());
        }
    }


    /**
     * This is a additionHelper method that inspects the term in question and creates a formatted term string for printing
     * based on three possible cases: order 0, order 1, coefficient of absolute value 1, and generic case
     * @param entry Key Value pair to be formatted
     * @return formatted string
     * */
    private String formatTermEntry(Map.Entry<Integer,Double> entry)
    {
        StringBuilder sb = new StringBuilder();

        //check if the power is 0. Do not write 'X' if that is the case
        if (entry.getKey() == 0)
        {
            if (entry.getValue() == 0.0)
                return "";
            else
            {
                return Double.toString(entry.getValue());
            }
        }

        //Check if the power is 1. Write the 'X' along with the coefficient but don't add the power
        else if (entry.getKey() == 1)
            sb.append(entry.getValue()).append("X");

            //default
        else
            sb.append(entry.getValue()).append("X^").append(entry.getKey());

        /*Cases of the form -X^n and X^n where n is an integer */
        if (entry.getValue() == 1.0) sb.replace(0,3,"");
        if (entry.getValue() == -1.0) sb.replace(1,4,"");


        return sb.toString();
    }


}