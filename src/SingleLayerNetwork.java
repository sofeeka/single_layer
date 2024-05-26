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
        return 0;
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
