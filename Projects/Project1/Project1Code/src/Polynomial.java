import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Polynomial Class holds the linked list that is associated with that polynomial.
 * It implements the Polynomial
 * interface where all of the arithmetic is performed
 *
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
public class Polynomial implements PolynomialInterface
{
    /** Linked List to store terms */
    private SinglyLinkedList<Term> p;


    /**Constructs a polynomial given a properly formatted polynomial string
     * EG: 4X^2+3X, 4.0X^2+3X, 4+3
     *1. Create an empty linked list to store the terms of the polynomial
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
     *  5. Using the groups extract the coefficient and power, create a Term with those values, and
     *  call insert() to add the new node in the proper location in the Polynomial Singly Linked List
     * @param s String to be converted to a Polynomial
     */
    public Polynomial(String s)
    {
        /*Instantiate empty list */
        p = new SinglyLinkedList<>();
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
        /*Create new Term with the extracted coefficient and power and insert it into the proper location
        in the linked List */
            p.insert(new Node<>(new Term(coefficient,power)));
        }
    }

    /*Default polynomial constructor. Creates empty polynomial */

    public Polynomial()
    {
        p = new SinglyLinkedList<>();
    }



    /**returns the linked list of terms
     * @return p
     * */
    public SinglyLinkedList<Term> getP()
    {
        return p;
    }

    /**sets the linked list to a new one
     * @param p Singly linked list of terms
     * */
    public void setP(SinglyLinkedList<Term> p)
    {
        this.p = p;
    }


    /**
     * This method adds two polynomials(namely the one calling this instance method and the one passed as a parameter
     * and returns the resulting polynomial
     * @param p Polynomial to be added
     * @return sum polynomial
     * */
    public Polynomial add(Polynomial p)
    {
        /* If one of the polynomials is null the other is returned */
        if (this.getP().isEmpty()) return p;
        if (p.getP().isEmpty()) return this;

        double coefficient;
        int power;


        /* Pointers to iterate through (this) polynomial and Polynomial p */
        Node<Term> curr1 = p.getP().getHead();
        Node<Term> curr2 = this.getP().getHead();
        /*New singly linked list to store the resulting polynomial */
        Polynomial sumPolynomial = new Polynomial();
        SinglyLinkedList<Term> sumList = sumPolynomial.getP();
        /*Iterate through the two polynomials and compare the powers
         *   */
        while (curr1 != null && curr2 != null)
        {

            int curr1Power = curr1.getData().getPower();
            int curr2Power = curr2.getData().getPower();
            double curr1Coefficient = curr1.getData().getCoefficent();
            double curr2Coefficient = curr2.getData().getCoefficent();

            /*If the powers are equal, the new coefficient is set to the sum of the coefficients of the terms being
            iterated through */
            if (curr1Power == curr2Power)
            {
                coefficient = curr1Coefficient + curr2Coefficient;
                power = curr1Power;
                curr1 = curr1.getNext();
                curr2 = curr2.getNext();
            }
            /*If the power of the term in the first list is greater than that of the second, the coefficient is set to
             * that of the term in the first list. Only the iterator of the first list is incremented */
            else if (curr1Power > curr2Power)
            {
                coefficient = curr1Coefficient;
                power = curr1Power;
                curr1 = curr1.getNext();
            }
            /*Default Case: The power of the term in the second list is greater than that of the first.
            The coefficient is set to that of the term in the second list. Only the iterator of the second list
            is incremented */
            else
            {
                coefficient = curr2Coefficient;
                power = curr2Power;
                curr2 =curr2.getNext();
            }
            /*The resultant new node is inserted into the proper location in the resultList */
            sumList.insert(new Node<>(new Term(coefficient,power)));
        }

        /*The additionHelper method is called on the two iterators to insert the rest of the terms */
        additionHelper(curr1, sumList);
        additionHelper(curr2, sumList);

        return sumPolynomial;
    }

    /**
     * This is a additionHelper method used by the add method to add the unaccounted-for terms in both lists to the result List
     *
     * @param curr Iterator of respective list
     * @param sumList List that is to be stored in the resulting polynomial
     */
    private void additionHelper(Node<Term> curr, SinglyLinkedList<Term> sumList)
    {
        double coefficient;
        int power;
        while (curr != null)
        {
            coefficient = curr.getData().getCoefficent();
            power = curr.getData().getPower();
            curr = curr.getNext();
            sumList.insert(new Node<>(new Term(coefficient,power)));
        }
    }

    /**
     * This method subtracts two polynomials(namely the one calling this instance method and the one passed as a
     * parameter
     * and returns the resulting polynomial. Subtraction is performed by calling addition on the the calling polynomial
     * and the negated Polynomial passed as a parameter
     * @param p Polynomial to be subtracted
     * @return difference polynomial
     * */
    public Polynomial subtract(Polynomial p)
    {
        if (p.getP().isEmpty()) return this;
        String pString = p.toString();
        Polynomial pCopy;
        if (pString.length() == 0)
            pCopy = new Polynomial();
        else pCopy = new Polynomial(pString);
        Node<Term> curr = pCopy.getP().getHead();
        /*Negate every term in p and perform polynomial addition */
        while (curr != null)
        {
            Term currData = curr.getData();
            currData.setCoefficent(-currData.getCoefficent());
            curr = curr.getNext();
        }



        return this.add(pCopy);


    }






    /**
     * This method multiplies two polynomials(namely the one calling this instance method and the one passed as a
     * parameter
     * and returns the resulting polynomial. Multiplication is performed by iterating through the two lists,
     * calling the multiplyNodes() method, adding the resulting nodes in a temporary polynomial, and then calling the
     * add() method on the resultPolynomial
     * @param p Polynomial to be multiplied
     * @return product polynomial
     * */
    public Polynomial multiply(Polynomial p)
    {
        Polynomial productPolynomial = new Polynomial();
        /*Instantiate the iterators for the two polynomial lists */
        Node<Term> curr1 = this.getP().getHead();
        Node<Term> curr2 = p.getP().getHead();

        while (curr2 != null)
        {
            Polynomial tempPolynomial = new Polynomial();
            SinglyLinkedList<Term> tempList = tempPolynomial.getP();
            while (curr1 != null)
            {
                tempList.insert(multiplyNodes(curr1,curr2));
                curr1 = curr1.getNext();
            }
            curr1 = this.getP().getHead();
            curr2 = curr2.getNext();
            productPolynomial = productPolynomial.add(tempPolynomial);

        }



        return productPolynomial;
    }



    /**
     * This method extracts the Term data from two nodes and performs multiplication
     * based on the following rule: aX^j * bX^k = (a*b)X^(j+k) where a,j,b,k are constants A new node is created with
     * the updated power and coefficient
     * @param n1 First node
     * @param n2 Second node
     * @return product node
     * */
    private Node<Term> multiplyNodes(Node<Term> n1, Node<Term> n2)
    {
        double newCoefficient = n1.getData().getCoefficent() * n2.getData().getCoefficent();
        int newPower = n1.getData().getPower() + n2.getData().getPower();

        return (new Node<>(new Term(newCoefficient,newPower)));
    }


    /**
     * This method extracts the Term data from two nodes and performs division
     * based on the following rule: aX^j * bX^k = (a*b)X^(j+k) where a,j,b,k are constants A new node is created with
     * the updated power and coefficient
     * @param n1 First node
     * @param n2 Second node
     * @return quotient node
     * */
    public Polynomial divideNodes(Node<Term> n1, Node<Term> n2)
    {
        if (n2 == null) throw new RuntimeException("Illegal: Division by zero");
        double newCoefficient = n1.getData().getCoefficent() / n2.getData().getCoefficent();
        int newPower = n1.getData().getPower() - n2.getData().getPower();
        SinglyLinkedList<Term> resultList = new SinglyLinkedList<>();
        resultList.insert(new Node<>(new Term(newCoefficient,newPower)));
        Polynomial result = new Polynomial();
        result.setP(resultList);
        return result;

    }


    /**
     * This method divides two polynomials(namely the one calling this instance method and the one passed as a
     * parameter
     * and returns the resulting "whole" part polynomial. Division is performed by calling
     * euclideanPolynomialDivision(p)
     * and returning the first element of the polynomial array returned by that method
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
     * and returns the resulting "fractional" part of the polynomial. Remainder is performed by calling
     * euclideanPolynomialDivision(p)
     * and returning the second element of the polynomial array returned by that method
     * @param p Polynomial to be divided
     * @return quotient polynomial
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
        if (p.getP().isEmpty()) throw new RuntimeException("Illegal division by zero");
        if (this.getP().isEmpty()) return new Polynomial[2];
        Polynomial quotientPolynomial = new Polynomial();    //set q to be the polynomial 0
        Polynomial remainderPolynomial, divisorPolynomial = new Polynomial(p.toString());
        String thisString = this.toString();
        if (thisString.equals("0") || thisString.length() == 0) remainderPolynomial = new Polynomial();
        else remainderPolynomial = new Polynomial(thisString);

        SinglyLinkedList<Term> rList = remainderPolynomial.getP();
        Node<Term> dListHead = p.getP().getHead();

        int orderP = p.getP().getHead().getData().getPower();
        while(!rList.isEmpty() && rList.getHead().getData().getPower() >= orderP)
        {

            Polynomial sPolynomial = divideNodes(rList.getHead(),dListHead);
            quotientPolynomial = quotientPolynomial.add(sPolynomial);
            Polynomial temp = sPolynomial.multiply(divisorPolynomial);

            remainderPolynomial = remainderPolynomial.subtract(temp);


            (remainderPolynomial.getP()).clean();
            rList = remainderPolynomial.getP();
        }
        return new Polynomial[]{quotientPolynomial,remainderPolynomial};

    }






    /** This method converts the Singly Linked List of Terms into a string of the following format:
     * (Term1)(Operator)(Term2)....(TermN),
     * where TermI is comprised of (Coefficient)^(Power) with two special cases
     * The additionHelper method formatTermString() is called to format each individual term
     * @return String to be printed
     *
     * */
    public final String toString()
    {

        if (this.getP().isEmpty()) return "0.0";
        StringBuilder sb = new StringBuilder();
        Node<Term> headNode = p.getHead(); //save the headNode for later comparison
        Node<Term> curr = p.getHead();
        while (curr != null)
        {
            Term currTerm = curr.getData();

            //If the coefficient of the term is positive and is not the first term, we need to prepend a '+' sign
            if (currTerm.getCoefficent() > 0.0 &&  curr != headNode)
                sb.append('+');

            String formattedCurrTerm = formatTermString(currTerm);
            sb.append(formattedCurrTerm);

            curr = curr.getNext();
        }

        return sb.toString();
    }


    /**
     * This is a additionHelper method that inspects the term in question and creates a formatted term string for printing
     * based on three possible cases: order 0, order 1, coefficient of absolute value 1, and generic case
     * @param t Term to be printed
     * @return formatted string
     * */
    private String formatTermString(Term t)
    {
        StringBuilder sb = new StringBuilder();

        //check if the power is 0. Do not write 'X' if that is the case
        if (t.getPower() == 0)
        {
            if (t.getCoefficent() == 0.0)
                return "";
            else
            {
                return Double.toString(t.getCoefficent());
            }
        }

        //Check if the power is 1. Write the 'X' along with the coefficient but don't add the power
        else if (t.getPower() == 1)
            sb.append(t.getCoefficent()).append("X");

            //default
        else
            sb.append(t.getCoefficent()).append("X^").append(t.getPower());

        /*Cases of the form -X^n and X^n where n is an integer */
        if (t.getCoefficent() == 1.0) sb.replace(0,3,"");
        if (t.getCoefficent() == -1.0) sb.replace(1,4,"");


        return sb.toString();
    }
}





