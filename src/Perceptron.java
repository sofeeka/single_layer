import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Perceptron
{
    private double[] weights;
    private double bias;
    private double learningRate;
    private String value;

    private static final boolean dynamicLearningRate = true;
    private static final boolean skipWorseEpochs = false;
    public static final Random random = new Random(1);

    Perceptron()
    {
        bias = random.nextDouble(1);
        learningRate = 0.01;
    }

    Perceptron(Perceptron perceptron)
    {
        this.weights = Arrays.copyOf(perceptron.weights, perceptron.weights.length);
        this.bias = perceptron.bias;
        this.learningRate = perceptron.learningRate;
        this.value = perceptron.value;
    }

    private boolean isActivated(double net)
    {
        return net >= 0;
    }

    public void runEpochs(ArrayList<Case> cases, int epochs)
    {
        double bestAccuracy = this.test(cases);
        double[] bestWeights = Arrays.copyOf(this.weights, this.weights.length);

        for (int i = 0; i < epochs; i++)
        {
            runEpoch(cases);

            if (skipWorseEpochs)
            {
                double newAccuracy = this.test(cases);
                if (newAccuracy < bestAccuracy)
                {
                    this.weights = bestWeights;
                }
            }
        }
    }

    public void runEpoch(ArrayList<Case> cases)
    {
        for (Case c : cases)
        {
            Logger.logForEachVector(c.getVector());
            adjustWeightsForCase(c);
        }
        decreaseLearningRate();
        Logger.logForEachVector("weights: ");
        Logger.logForEachVector(this.weights);
    }

    private void adjustWeightsForCase(Case c)
    {
        double[] x = c.getVector();
        String value = c.getValue();
        String predictedValue = predictOutput(x);

        Logger.logForEachVector("expected: " + value + "\t" + getBinaryValue(value));
        Logger.logForEachVector("got     : " + predictedValue + "\t" + getBinaryValue(predictedValue));

        double helpingFactor = this.learningRate * (getBinaryValue(value) - getBinaryValue(predictedValue));
        this.weights = addTwoVectors(this.weights, multiplyVectorByValue(x, helpingFactor));
        this.bias -= helpingFactor;

        Logger.logForEachVector("helpingFactor: " + helpingFactor);
        Logger.logForEachVector("weights");
        Logger.logForEachVector(weights);
        Logger.logForEachVector("bias");
        Logger.logForEachVector(bias + "\n");
    }

    public void setLearningRate(int epochs)
    {
        if (dynamicLearningRate)
        {
//            learningRate = Math.min(.1 / epochs, 0.01);
            Logger.log("learning rate set to " + learningRate);
        } else
            Logger.log("learning rate was not changed");
    }

    public void decreaseLearningRate()
    {
        if (dynamicLearningRate)
            learningRate *= 0.999;
    }

    private int getBinaryValue(String s)
    {
        //todo
        return s.equals(value) ? 1 : 0;
    }

    private String predictOutput(double[] x)
    {
        //todo
        double net = findDotProduct(x) - bias;
        Logger.logForEachVector("net: " + net);
        return isActivated(net) ? value : "404";
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
            String expected = c.getValue();
            String predicted = predictOutput(c.getVector());
            String trainedFor = this.value;

            if (expected.equals(trainedFor) && expected.equals(predicted))
                correct++;

            if (!expected.equals(trainedFor) && !predicted.equals(trainedFor))
                correct++;
        }
        return ((double) correct) / all;
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

    public void setLen(int len)
    {
        if (weights == null || weights.length == 0)
        {
            weights = new double[len];
            for (int i = 0; i < weights.length; i++)
                weights[i] = random.nextDouble(2) - 1;
        }
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
