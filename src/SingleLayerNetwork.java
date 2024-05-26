import java.security.KeyPair;
import java.util.ArrayList;

public class SingleLayerNetwork
{
    private ArrayList<Perceptron> perceptrons;

    SingleLayerNetwork()
    {
        perceptrons = new ArrayList<>();
    }

    public double test(ArrayList<Case> cases)
    {
        double correct = 0;
        double all = cases.size();

        for (Case c : cases)
            if (predictValue(c.getVector()).equals(c.getValue()))
                correct++;

        return correct / all;
    }

    private String predictValue(double[] vector)
    {
        double net = 0;
        String value = "";

        for (Perceptron perceptron : perceptrons)
        {
            double newNet = perceptron.calculateNet(vector);
            if ( newNet > net)
            {
                net = newNet;
                value = perceptron.getValue();
            }
        }
        return value;
    }

    public ArrayList<Perceptron> getPerceptrons()
    {
        return perceptrons;
    }

    public void setPerceptrons(ArrayList<Perceptron> perceptrons)
    {
        this.perceptrons = perceptrons;
    }

    public void addPerceptron(Perceptron perceptron)
    {
        this.perceptrons.add(perceptron);
    }

}
