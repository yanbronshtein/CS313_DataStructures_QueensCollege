/**
 * The Utility class tests the polynomial arithmetic operations on two polynomials
 * @author Alex Chen
 * @version 1.0
 * */
class Utility
{
    /** Executes 5 arithmetic operations of polynomial
     * @param p First polynomial
     * @param q Second polynomial
     *
     * */
    public static void run(Polynomial p, Polynomial q) throws Exception {

        System.out.println("Polynomials\np = " + p + "\nq = " + q);
        System.out.println("Sum " + p.add(q));
        System.out.println("Difference " + p.subtract(q));
        System.out.println("Product " + p.multiply(q));
        System.out.println("Quotient " + p.divide(q));
        System.out.println("Remainder " + p.remainder(q));

  }


}