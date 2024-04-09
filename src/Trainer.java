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
        for(Case c : cases)
            set.add(c.getValue());
        return set;
    }

    public double trainPerceptron(Perceptron perceptron, int epochs, double desiredPrecision)
    {
        Logger.log("training from " + caseLoader.getSource() + " for " + epochs + " epochs");

        perceptron.setLen(cases.get(0).getVector().length);
        perceptron.setLearningRate(epochs);
        perceptron.setValues(getValues());

        double precision = 0;

        for (int e = 0; e < epochs; e++)
        {
            Logger.log("epoch: " + e);
            perceptron.runEpoch(cases);
            precision = perceptron.test(cases);

            if (precision > desiredPrecision)
            {
                Logger.log("returned on epoch " + e + "/" + epochs + " with precision " + precision);
                break;
            }
        }
        return precision;
    }

    public int getVectorLen()
    {
        return this.cases.get(0).getVector().length;
    }
}
