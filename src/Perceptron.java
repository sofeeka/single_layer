import java.io.BufferedReader;
import java.io.FileReader;

//Iris-versicolor - 1
//Iris-virginica - 0

public class Perceptron
{
    private double[] weights;
    private double bias;
    private double learningRate;
    double precision;

    private String binaryOneInInput;

    Perceptron()
    {
        bias = Math.random();
        learningRate = 0.01;
        precision = 0;
    }

    private boolean isActivated(double net)
    {
        return net >= 0;
    }

    private void fillRandomWeights()
    {
        for (int i = 0; i < weights.length; i++)
            weights[i] = Math.random();
    }

    public void adjustWeightsFromFile(String fileName, int epochs)
    {
        Logger.log("training on file " + fileName + " for " + epochs + " epochs");
        for (int epoch = 0; epoch < epochs; epoch++)
        {
            int v = 0;
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    String[] splitInput = line.split(",");
                    if (weights == null)
                    {
                        weights = new double[splitInput.length - 1];
                        fillRandomWeights();
                        binaryOneInInput = splitInput[splitInput.length - 1];
                    }

                    double[] attributes = new double[splitInput.length - 1];
                    int value = getBinaryValue(splitInput[splitInput.length - 1]);

                    for (int i = 0; i < splitInput.length - 1; i++)
                        attributes[i] = Double.parseDouble(splitInput[i].trim());

                    Logger.log("");
                    adjustWeightsForVector(attributes, value, v);
                    v++;
                }

            } catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }

    private int getBinaryValue(String value)
    {
        return value.equals(binaryOneInInput) ? 1 : 0;
    }

    private void adjustWeightsForVector(double[] x, int value, int i)
    {
        Logger.log("\n" + i + ". adjustWeightsForVector:");

        double helpingFactor = this.learningRate * (value - predictOutput(x));
        Logger.log("helpingFactor: " + helpingFactor);

        this.weights = addTwoVectors(this.weights, multiplyVectorByValue(x, helpingFactor));
        Logger.log("weights");
        Logger.log(weights);

        this.bias -= helpingFactor;
        Logger.log("bias");
        Logger.log(bias + "");
    }

    private int predictOutput(double[] x)
    {
        double net = findDotProduct(x) - bias;
        return isActivated(net) ? 1 : 0;
    }

    private double[] multiplyVectorByValue(double[] v, double value)
    {
        for (int i = 0; i < v.length; i++)
            v[i] *= value;
        return v;
    }

    private double[] addTwoVectors(double v1[], double[] v2)
    {
        if (v1.length != v2.length)
        {
            Logger.log("findDotProduct: Wrong number of arguments in vectors.");
            return new double[0];
        }

        for (int i = 0; i < v1.length; i++)
            v1[i] += v2[i];
        return v1;
    }

    private double findDotProduct(double[] v1)
    {
        return findDotProduct(v1, this.weights);
    }

    private static double findDotProduct(double[] v1, double[] v2)
    {
        if (v1.length != v2.length)
        {
            Logger.log("findDotProduct: Wrong number of arguments in vectors.");
            return 0;
        }

        double sum = 0.0;
        for (int i = 0; i < v1.length; i++)
            sum += v1[i] * v2[i];

        return sum;
    }

    public double testFromFile(String fileName)
    {
        int correct = 0;
        int all = 0;
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] split = line.split(",");
                double[] attributes = new double[split.length - 1];
                int value = getBinaryValue(split[split.length - 1]);

                for (int i = 0; i < split.length - 1; i++)
                    attributes[i] = Double.parseDouble(split[i].trim());

                if (predictOutput(attributes) == value)
                    correct++;
                all++;
            }

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        Logger.log("tested: " + correct + "/" + all);
        return ((double) (correct)) / all;
    }
}
