import java.util.ArrayList;

public class Trainer
{
    private final CaseLoader caseLoader;
    private final ArrayList<Case> cases;

    public Trainer(CaseLoader caseLoader)
    {
        this.caseLoader = caseLoader;
        cases = this.caseLoader.loadCases();
    }

    public double trainPerceptron(Perceptron perceptron, int epochs, double desiredPrecision)
    {
        Logger.log("training from " + caseLoader.getSource() + " for " + epochs + " epochs");
        perceptron.setLen(cases.get(0).getVector().length);
        perceptron.setLearningRate(epochs);

        double precision = 0;

        for (int i = 0; i < epochs; i++)
        {
            perceptron.runEpoch(cases);
            precision = perceptron.test(cases);

            if (precision > desiredPrecision)
            {
                Logger.log("returned on epoch " + i + "/" + epochs + " with precision " + precision);
                break;
            }
        }
        return precision;
    }
}
