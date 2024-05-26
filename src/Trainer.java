import java.util.ArrayList;
import java.util.HashSet;

public class Trainer
{
    private final CaseLoader caseLoader;
    private final ArrayList<Case> cases;

    public Trainer(CaseLoader caseLoader)
    {
        this.caseLoader = caseLoader;
        cases = this.caseLoader.loadCases();
    }

    public HashSet<String> getValues()
    {
        HashSet<String> set = new HashSet<>();
        for (Case c : cases)
            set.add(c.getValue());
        return set;
    }

    public double trainPerceptron(Perceptron perceptron, int epochs, double desiredPrecision)
    {
        Logger.log("training from " + caseLoader.getSource() + " for " + epochs + " epochs");

        perceptron.setLen(cases.get(0).getVector().length);
        perceptron.setLearningRate(epochs);
//        perceptron.setValues(getValues());

        double precision = 0;

        for (int e = 0; e < epochs; e++)
        {
            Logger.logForEachVector("epoch: " + e);
            perceptron.runEpoch(cases);
            precision = perceptron.test(cases);

            if (precision > desiredPrecision)
            {
                Logger.log("returned on epoch " + e + "/" + epochs + " with precision " + precision);
                break;
            }
        }
        Logger.logEmpty();
        return precision;
    }

    public int getVectorLen()
    {
        return this.cases.get(0).getVector().length;
    }

    public void trainNetwork(SingleLayerNetwork singleLayerNetwork, int epochs, double desiredPrecision)
    {
        initialisePerceptrons(singleLayerNetwork);

        for (Perceptron perceptron : singleLayerNetwork.getPerceptrons())
            trainPerceptron(perceptron, epochs, desiredPrecision);
        Logger.logEmpty();
    }

    public void initialisePerceptrons(SingleLayerNetwork singleLayerNetwork)
    {
        for (String value : getValues())
        {
            Logger.log("new perceptron value: " + value);
            Perceptron perceptron = new Perceptron();
            perceptron.setValue(value);
            singleLayerNetwork.addPerceptron(perceptron);
        }

        Logger.logEmpty();
    }
}
