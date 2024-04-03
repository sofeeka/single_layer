import java.util.ArrayList;
import java.util.Arrays;

public class Perceptron
{
    private double[] weights;
    private double bias;
    private double learningRate;
    double precision;

    private static final boolean dynamicLearningRate = true;

    Perceptron()
    {
        bias = Math.random();
        learningRate = 0.01;
        precision = 0;
    }

    Perceptron(Perceptron perceptron)
    {
        this.weights = Arrays.copyOf(perceptron.weights, perceptron.weights.length);
        this.bias = perceptron.bias;
        this.learningRate = perceptron.learningRate;
        this.precision = perceptron.precision;
    }

    private boolean isActivated(double net)
    {
        return net >= 0;
    }

    public void runEpoch(ArrayList<Case> cases)
    {
        for (Case c : cases)
        {
            Logger.logForEachVector(c.getVector());
            adjustWeightsForCase(c);
        }
    }

    private void adjustWeightsForCase(Case c)
    {
        double[] x = c.getVector();
        int value = c.getValue();

        double helpingFactor = this.learningRate * (value - predictOutput(x));
        this.weights = addTwoVectors(this.weights, multiplyVectorByValue(x, helpingFactor));
        this.bias -= helpingFactor;

        Logger.logForEachVector("helpingFactor: " + helpingFactor);
        Logger.logForEachVector("weights");
        Logger.logForEachVector(weights);
        Logger.logForEachVector("bias");
        Logger.logForEachVector(bias + "");
    }

    public void setLearningRate(int epochs)
    {
        if (dynamicLearningRate)
        {
            learningRate = Math.min(2. / epochs, 0.01);
            Logger.log("learning rate set to " + learningRate);
        } else
            Logger.log("learning rate was not changed");
    }

    private int predictOutput(double[] x)
    {
        double net = findDotProduct(x) - bias;
        return isActivated(net) ? 1 : 0;
    }

    private double[] multiplyVectorByValue(double[] v, double value)
    {
        double[] res = new double[v.length];
        for (int i = 0; i < v.length; i++)
            res[i] = v[i] * value;
        return res;
    }

    private double[] addTwoVectors(double[] v1, double[] v2)
    {
        if (v1.length != v2.length)
        {
            Logger.log("addTwoVectors: Wrong number of arguments in vectors.");
            return new double[0];
        }

        double[] res = new double[v1.length];
        for (int i = 0; i < v1.length; i++)
            res[i] = v1[i] + v2[i];

        return res;
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

    public double test(ArrayList<Case> cases)
    {
        int correct = 0;
        int all = cases.size();

        for (Case c : cases)
        {
            if (predictOutput(c.getVector()) == c.getValue())
                correct++;
        }
        return ((double) (correct)) / all;
    }

    public double[] getWeights()
    {
        return weights;
    }

    public void setWeights(double[] weights)
    {
        this.weights = weights;
    }

    public double getBias()
    {
        return bias;
    }

    public double getLearningRate()
    {
        return learningRate;
    }

    public double getPrecision()
    {
        return precision;
    }

    public void setLen(int len)
    {
        if (weights == null)
        {
            weights = new double[len];
            for (int i = 0; i < weights.length; i++)
                weights[i] = Math.random();
        }
    }

}
