import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class PolynomialTest
{

    public static void main(String[] args)
    {
        Polynomial a = new Polynomial("X^5+2X^2+3X^3+4X^4");
        Polynomial b = new Polynomial("2X^2+4X");

//        Polynomial a = new Polynomial("2X^6+2");
////
//        Polynomial b = new Polynomial("X^3");
        System.out.println(a.divide(b));
//        System.out.println(a.remainder(b));








    }


}
