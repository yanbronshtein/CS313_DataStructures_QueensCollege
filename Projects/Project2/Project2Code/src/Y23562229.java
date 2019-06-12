public class Y23562229
{
    public static void main(String args[]) throws Exception
    {
        String a = "X^5+2X^2+3X^3+4X^4";
        String b = "2X^2+4X";
        Polynomial p = new Polynomial(a), q = new Polynomial(b);
        Utility.run(p, q);
    }
}