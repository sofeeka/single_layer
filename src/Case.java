public class Case
{
    private final double[] vector;
    private final int value;

    public Case(double[] vector, int value)
    {
        this.vector = vector;
        this.value = value;
    }

    public double[] getVector()
    {
        return vector;
    }

    public int getValue()
    {
        return value;
    }
}
