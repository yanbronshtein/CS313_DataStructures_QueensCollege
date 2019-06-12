/**
 * The Term class is used to store each term of the Polynomial. It should contain both the coefficient and power.
 * @author Yaniv Bronshtein
 * @version 1.0
 * */
class Term
{
    /**Contains the coefficient value of the polynomial term */
    private double coefficent;
    /**Contains the power value of the polynomial term */
    private int power;

    /**
     * Constructs a term object
     * @param coefficent coefficient of term
     * @param power power of term */
    public Term(double coefficent, int power)
    {
        this.coefficent = coefficent;
        this.power = power;
    }

    /**Setter method for coefficient
     * @param coefficent New coefficient value */
    public void setCoefficent(double coefficent)
    {
        this.coefficent = coefficent;
    }

    /**Setter method for power
     * @param power New power value */
    public void setPower(int power)
    {
        this.power = power;
    }

    /**Getter method for coefficient
     * @return existing coefficient */
    public double getCoefficent()
    {
        return coefficent;
    }

    /**Getter method for power
     * @return existing power */
    public int getPower()
    {
        return power;
    }

    /**
     * This utility function compares the order of two terms.
     * If the return value < 0, then the original term is of a lower order.
     * Else if the return value > 0, then the original term is of a higher order
     * Otherwise, the terms are of the same order
     * @param other Term to compare To
     * @return result of comparison
     *
     *             */
    public double compareTo(Term other)
    {
        return (this.power - other.power);
    }
}