/**
 * This class contains the main method where the polynomials to best tested with the various arithmetic operations
 * are created
 * @author Alex Chen
 * @version 1.0
 * */
public class X23562229
{
    /** Main method */
    public static void main(String args[]) throws Exception
    {
        String a = "X^5+2X^2+3X^3+4X^4";

        String b = "2X^2+4X";


		Polynomial p = new Polynomial(a), q = new Polynomial(b);
		Utility.run(p, q);
    }
}