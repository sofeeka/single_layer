import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CaseLoader_File implements CaseLoader
{
    private static final int ALPHABET_SIZE = 26;
    private String fileName;

    CaseLoader_File(String fileName)
    {
        this.fileName = fileName;
    }

    public ArrayList<Case> loadCases(String fileName)
    {
        this.fileName = fileName;
        return loadCases();
    }

    @Override
    public ArrayList<Case> loadCases()
    {
        Logger.log("loading cases from file " + fileName);
        ArrayList<Case> cases = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] splitInput = line.split(",", 2);

                String value = splitInput[0];

                String text = splitInput[1];
                double[] attributes = getLetterCountFromText(text);

                cases.add(new Case(attributes, value));
            }

        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        return cases;
    }

    private double[] getLetterCountFromText(String text)
    {
        double[] vector = new double[ALPHABET_SIZE];
        text = text.toLowerCase();
        for (char c : text.toCharArray())
        {
            if (c >= 'a' && c <= 'z')
            {
                vector[c - 'a']++;
            }
        }
        return normalizeVector(vector);
    }

    private double[] normalizeVector(double[] vector)
    {
        double sum = 0.0;
        for (double value : vector)
            sum += value * value;

        double len = Math.sqrt(sum);
        for (int i = 0; i < vector.length; i++)
            vector[i] = vector[i] / len;

        return vector;
    }

    @Override
    public String getSource()
    {
        return "file " + fileName;
    }
}
