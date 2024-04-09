public class Case
{
    private final double[] vector;
    private final String value;

    public Case(double[] vector, String value)
    {
        this.vector = vector;
        this.value = value;
    }

    public double[] getVector()
    {
        return vector;
    }

    public String getValue()
    {
        return value;
    }
}
