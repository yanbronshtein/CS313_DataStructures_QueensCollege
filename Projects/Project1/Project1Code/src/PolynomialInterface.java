/**
 * This interface defines the methods to be implemented by the Polynomial Class
 * @author Alex Chen
 * @version 1.0
 * */
interface PolynomialInterface
{
    Polynomial add(Polynomial p);
    Polynomial subtract(Polynomial p);
    Polynomial multiply(Polynomial p);
    Polynomial divide(Polynomial p) throws Exception;
    Polynomial remainder(Polynomial p) throws Exception;
}