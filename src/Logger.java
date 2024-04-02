public class Logger
{
    public static boolean logging = false;

    public static void log(String msg)
    {
        if (logging)
            System.out.println(msg);
    }

    public static void log(double[] vector)
    {
        if (logging)
        {
            for (int i = 0; i < vector.length; i++)
                System.out.print(vector[i] + " ");
            System.out.println();
        }
    }
}