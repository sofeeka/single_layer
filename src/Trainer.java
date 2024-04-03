import java.util.ArrayList;

public class Trainer
{
    private final CaseLoader caseLoader;
    private final ArrayList<Case> cases;

    public Trainer(CaseLoader_File caseLoaderFile)
    {
        this.caseLoader = caseLoaderFile;
        cases = caseLoader.loadCases();
    }

    public double trainPerceptron(Perceptron perceptron, int epochs, double desiredPrecision)
    {
        Logger.log("training from " + caseLoader.getSource() + " for " + epochs + " epochs");
        double precision = 0;
        double[] bestPrecisionWeights = perceptron.initialise(cases.get(0).getVector().length, epochs);

        for (int i = 0; i < epochs; i++)
        {
            perceptron.runEpoch(cases);
            double newPrecision = perceptron.test(cases);

            if(newPrecision > desiredPrecision)
            {
                Logger.log("returned on epoch " + i + "/" + epochs + " with precision " + newPrecision);
                return newPrecision;
            }
            if (newPrecision > precision)
            {
                bestPrecisionWeights = perceptron.getWeights();
                precision = newPrecision;
            }
        }

        perceptron.setWeights(bestPrecisionWeights);
        return precision;
    }

    public CaseLoader_File getLoader()
    {
        return (CaseLoader_File) caseLoader;
    }
}
