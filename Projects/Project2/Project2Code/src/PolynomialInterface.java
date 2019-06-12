interface PolynomialInterface{
    public Polynomial add(Polynomial p);
    public Polynomial subtract(Polynomial p);
    public Polynomial multiply(Polynomial p);
    public Polynomial divide(Polynomial p) throws Exception;
    public Polynomial remainder(Polynomial p) throws Exception;
}